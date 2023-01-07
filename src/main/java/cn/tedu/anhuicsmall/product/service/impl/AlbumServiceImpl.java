package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.AlbumMapper;
import cn.tedu.anhuicsmall.product.mapper.PictureMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.AlbumAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.AlbumUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import cn.tedu.anhuicsmall.product.pojo.entity.Picture;
import cn.tedu.anhuicsmall.product.repo.IAlbumRedisRepository;
import cn.tedu.anhuicsmall.product.repo.impl.AlbumRedisRepositoryImpl;
import cn.tedu.anhuicsmall.product.service.IAlbumService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相册的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements IAlbumService {

    public AlbumServiceImpl() {
        log.debug("创建业务层接口实现类:AlbumServiceImpl");
    }

    // 注入相册的持久层接口
    @Autowired
    private AlbumMapper albumMapper;

    // 注入图片的持久层接口
    @Autowired
    private PictureMapper pictureMapper;
    
    // 注入相册的Redis缓存实现类
    @Autowired
    private IAlbumRedisRepository albumRedisRepository;

    /**
     * 处理添加相册的功能
     *
     * @param albumAddNewDTO 注册需要的相册信息
     */
    @Override
    public void insert(AlbumAddNewDTO albumAddNewDTO) {
        log.debug("开始处理添加相册的功能,参数:{}", albumAddNewDTO);
        // 判断添加的相册昵称是否存在
        QueryWrapper<Album> wrapper = new QueryWrapper<>();
        wrapper.eq("name", albumAddNewDTO.getName());
        Album queryAlbum = albumMapper.selectOne(wrapper);
        if (queryAlbum != null) {
            String message = "添加失败,该相册名称已经被占用!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        log.debug("即将向相册表中插入信息...");
        Album album = new Album();
        BeanUtils.copyProperties(albumAddNewDTO, album);
        log.debug("开始向相册表中插入信息...");
        int rows = albumMapper.insert(album);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 处理根据id删除相册信息的功能
     *
     * @param albumId 要删除的相册id
     */
    @Override
    public void deleteById(Long albumId) {
        log.debug("开始处理删除id为:{}的相册信息", albumId);
        Album queryAlbum = albumMapper.selectById(albumId);
        if (queryAlbum == null) {
            String message = "删除失败,该相册id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 删除与该相册相关的所有图片信息
        Map<String, Object> map = new HashMap<>();
        map.put("album_id", albumId);
        int rows = pictureMapper.deleteByMap(map);
        if (rows != 0) {
            String message = "删除关联图片失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }

        log.debug("即将删除id为{}的相册信息...", albumId);
        int row = albumMapper.deleteById(albumId);
        if (row > 1) {
            String message = "删除相册失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改相册信息的功能
     *
     * @param albumUpdateDTO 相册修改的信息
     */
    @Override
    public void update(AlbumUpdateDTO albumUpdateDTO) {
        log.debug("开始处理修改id为:{}的相册信息", albumUpdateDTO.getId());
        Album queryAlbum = albumMapper.selectById(albumUpdateDTO.getId());
        if (queryAlbum == null) {
            String message = "修改失败,该相册id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 修改的相册名称不能和其他相册的名称相同
        log.debug("开始检查相册名称是否存在...");
        QueryWrapper<Album> wrapper = new QueryWrapper<>();
        wrapper.ne("id",albumUpdateDTO.getId()); // 除该id外
        wrapper.eq("name",albumUpdateDTO.getName()); // 与该名字相同
        Album queryAlbumByNameAndNotId = albumMapper.selectOne(wrapper);
        if (queryAlbumByNameAndNotId !=null){
            String message = "修改失败,该相册名称已存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }

        Album album = new Album();
        BeanUtils.copyProperties(albumUpdateDTO, album);
        int rows = albumMapper.updateById(album);
        if (rows > 1) {
            String message = "修改相册失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 处理根据id查询相册信息的功能
     *
     * @param albumId 相册id
     * @return 返回结果集
     */
    @Override
    public Album selectById(Long albumId) {
        log.debug("开始处理查询id为{}的相册详情",albumId);
        log.debug("将从Redis中获取相关数据...");
        Album album = albumRedisRepository.get(albumId);

        if (album != null){
            log.debug("命中缓存,即将返回:{}",album);
            return album;
        }

        log.debug("未命中缓存,即将向数据库中查询数据...");
        album = albumMapper.selectById(albumId);
        if (album == null) {
            String message = "查询失败,该相册数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        log.debug("从数据库中查询到有效结果,将查询结果存入缓存中...");
        albumRedisRepository.save(album);
        log.debug("开始返回结果!");
        return album;
    }

    /**
     * 开始处理查询相册列表的功能
     *
     * @return 返回结果集
     */
    @Override
    public List<Album> selectList() {
        log.debug("开始处理查询相册列表的功能,无参!");
        return albumRedisRepository.list();
    }

    /**
     * 手动重建Redis缓存的方法
     */
    @Override
    public void rebuildCache() {
        log.debug("准备删除Redis缓存中的相册数据...");
        albumRedisRepository.deleteAll();// 清除缓存中的数据,防止缓存堆积过多,显示的列表数据冗余
        log.debug("删除Redis缓存中的相册数据,完成!");

        log.debug("准备从数据库中读取相册列表...");
        List<Album> list = albumMapper.selectList(null); // 利用Mapper查询列表放到List中
        log.debug("从数据库中读取相册列表，完成！");

        log.debug("准备将相册列表写入到Redis缓存...");
        albumRedisRepository.save(list);// 利用相册的Redis接口调用save转入要保存的列表,加载到缓存中
        log.debug("将相册列表写入到Redis缓存，完成！");

        log.debug("准备将各相册详情写入Redis缓存...");
        for (Album album : list) {// 遍历拿到的相册列表
            Long id = album.getId();// 获取遍历的每个相册列表的id
            Album albumStandardVO = albumMapper.selectById(id);// 利用拿到的id来查询对应的相册详情
            albumRedisRepository.save(albumStandardVO);// 将每一个相册详情放到Redis缓存中
        }
        log.debug("将各相册详情写入到Redis缓存中,完成!");
    }
}
