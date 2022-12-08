package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.AlbumMapper;
import cn.tedu.anhuicsmall.product.mapper.PictureMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.PictureAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.PictureUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.Album;
import cn.tedu.anhuicsmall.product.pojo.entity.Picture;
import cn.tedu.anhuicsmall.product.service.IPictureService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {

    public PictureServiceImpl() {
        log.debug("创建业务层接口实现类:PictureServiceImpl");
    }

    // 注入商品图片持久层接口
    @Autowired
    private PictureMapper pictureMapper;

    // 注入商品相册持久层接口
    @Autowired
    private AlbumMapper albumMapper;

    /**
     * 处理添加图片的业务
     *
     * @param pictureAddNewDTO 注册需要的图片信息
     */
    @Override
    public void insert(PictureAddNewDTO pictureAddNewDTO) {
        log.debug("处理添加图片的功能,参数:{}", pictureAddNewDTO);
        // 判断图片所属相册是否存在
        Album queryAlbum = albumMapper.selectById(pictureAddNewDTO.getAlbumId());
        if (queryAlbum == null) {
            String message = "添加失败,该图片所属相册不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        if (pictureAddNewDTO.getIsCover() == 1) { // 如果将当前图片设置为封面
            Long albumId = pictureAddNewDTO.getAlbumId();
            // 在当前相册下的图片中查询isCover为1的图片数量
            QueryWrapper<Picture> wrapper = new QueryWrapper<>();
            wrapper.eq("album_id", albumId);
            wrapper.eq("is_cover", 1);
            Integer count = pictureMapper.selectCount(wrapper);
            if (count != 0) {
                String message = "添加失败,封面已经存在!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_INSERT, message);
            }
        }

        log.debug("即将向表中添加图片数据...");
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureAddNewDTO, picture);
        log.debug("开始向表中添加图片数据,参数:{}", picture);
        int rows = pictureMapper.insert(picture);
        if (rows > 1) {
            String message = "添加失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 根据id删除图片数据
     *
     * @param pictureId 要删除的图片id
     */
    @Override
    public void deleteById(Long pictureId) {
        log.debug("开始处理删除id为{}的图片数据", pictureId);
        // 判断该图片是否存在
        Picture queryPicture = pictureMapper.selectById(pictureId);
        if (queryPicture == null) {
            String message = "删除失败,该图片数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断该图片是否为封面
        if (queryPicture.getIsCover() == 1) { // 如果该图片的isCover为1,说明是封面,则不能删除
            String message = "删除失败,该图片是一个封面,不允许删除";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 判断该图片是否有所属相册
        Album queryAlbum = albumMapper.selectById(queryPicture.getAlbumId());
        if (queryAlbum == null) {
            String message = "删除失败,该图片所属相册不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        log.debug("即将删除id为{}的图片数据", pictureId);
        int rows = pictureMapper.deleteById(pictureId);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改图片
     *
     * @param pictureUpdateDTO 图片修改的信息
     */
    @Override
    public void update(PictureUpdateDTO pictureUpdateDTO) {
        log.debug("开始修改id为{}的图片数据", pictureUpdateDTO.getId());
        // 判断该图片是否存在
        Picture queryPicture = pictureMapper.selectById(pictureUpdateDTO.getId());// 拿当前修改的id去查图片
        if (queryPicture == null) {
            String message = "修改失败,该图片数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Long albumId = pictureUpdateDTO.getAlbumId(); // 获取当前修改的相册id

        if (pictureUpdateDTO.getIsCover() == 1) { // 如果将当前图片修改为封面
            // 在当前相册下的图片中查询除此次修改的图片外,是否还有isCover为1的
            QueryWrapper<Picture> wrapper = new QueryWrapper<>();
            wrapper.eq("album_id", albumId);
            wrapper.ne("id", pictureUpdateDTO.getId());
            wrapper.eq("is_cover", 1);
            Integer count = pictureMapper.selectCount(wrapper);
            if (count > 0) {
                String message = "修改失败,该图片所属相册的封面已经存在!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_UPDATE, message);
            }
        }

        if (pictureUpdateDTO.getIsCover() == 0) { // 如果将当前图片修改为非封面
            // 前者是当前id下的相册id,后者是修改后的相册id
            if (queryPicture.getAlbumId().equals(pictureUpdateDTO.getAlbumId())){ // 如果相等,说明还处于当前相册
                // 在当前相册下的图片中查询除此次修改的图片外,isCover为1的数量
                QueryWrapper<Picture> wrapper = new QueryWrapper<>();
                wrapper.eq("album_id", albumId);
                wrapper.ne("id", pictureUpdateDTO.getId());
                wrapper.eq("is_cover", 1);
                Integer count = pictureMapper.selectCount(wrapper);
                if (count == 0) {
                    String message = "修改失败,该图片是当前相册唯一的封面!";
                    log.debug(message);
                    throw new ServiceException(ServiceCode.ERR_UPDATE, message);
                }
            }else { //如果不相等,说明换了相册,查询该图片是否为上一个图片的封面
                // 查询当前id之外的上一个相册isCover为1的数量
                QueryWrapper<Picture> wrapper = new QueryWrapper<>();
                wrapper.eq("album_id", queryPicture.getAlbumId());
                wrapper.ne("id", pictureUpdateDTO.getId());
                wrapper.eq("is_cover", 1);
                Integer count = pictureMapper.selectCount(wrapper);
                if (count == 0){
                    String message = "修改失败,该图片是当前相册唯一的封面!";
                    log.debug(message);
                    throw new ServiceException(ServiceCode.ERR_UPDATE, message);
                }
            }
        }

        log.debug("即将修改id为{}的图片数据", pictureUpdateDTO.getId());
        Picture picture = new Picture();
        BeanUtils.copyProperties(pictureUpdateDTO, picture);
        log.debug("开始修改id为{}的图片数据,参数:{}", pictureUpdateDTO.getId(), picture);
        int rows = pictureMapper.updateById(picture);
        if (rows > 1) {
            String message = "修改失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 根据id查询单个图片信息
     *
     * @param pictureId 图片id
     * @return 返回图片实体类
     */
    @Override
    public Picture selectById(Long pictureId) {
        log.debug("开始处理查询id为{}的图片信息", pictureId);
        // 判断该图片是否存在
        Picture queryPicture = pictureMapper.selectById(pictureId);
        if (queryPicture == null) {
            String message = "查询失败,该图片数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return queryPicture;
    }

    /**
     * 查询全部图片列表的功能
     *
     * @return 返回列表数据
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询全部图片列表的功能,无参!");
        return pictureMapper.selectObjs(null);
    }

    /**
     * 根据相册id查询图片列表信息
     *
     * @param albumId 相册id
     * @return 返回图片列表数据
     */
    @Override
    public List<Object> selectListByAlbumId(Long albumId) {
        log.debug("开始处理查询相册id为{}的图片列表", albumId);
        Album queryAlbum = albumMapper.selectById(albumId);
        if (queryAlbum == null) {
            String message = "查询失败,该相册id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        QueryWrapper<Picture> wrapper = new QueryWrapper<>();
        wrapper.eq("album_id", albumId);
        return pictureMapper.selectObjs(wrapper);
    }

    /**
     * 处理将图片修改为封面
     *
     * @param id 要修改的图片id
     */
    @Override
    public void setCover(Long id) {
        updateIsCoverById(id, 1);
    }

    /**
     * 将图片设置为非封面
     *
     * @param id 取消封面的图片id
     */
    @Override
    public void setNoCover(Long id) {
        updateIsCoverById(id, 0);
    }

    /**
     * 处理设置图片封面的逻辑方法
     *
     * @param pictureId 图片id
     * @param coverId   1=封面;0=非封面
     */
    private void updateIsCoverById(Long pictureId, Integer coverId) {
        String[] tips = {"封面", "非封面"};
        log.debug("开始处理将id为{}的图片设置为{}的业务", pictureId, tips[coverId]);
        // 判断该图片是否存在
        Picture queryPicture = pictureMapper.selectById(pictureId);
        if (queryPicture == null) {
            String message = "设置" + tips[coverId] + "失败,该图片数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 判断查询结果中的isCover与方法参数isCover是否相同
        if (coverId.equals(queryPicture.getIsCover())) {
            String message = "设置" + tips[coverId] + "失败,该图片已经处于" + tips[coverId] + "状态!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        if (coverId == 1) { // 设置为封面
            Long albumId = queryPicture.getAlbumId();
            // 在当前相册下的图片中查询除此次修改的图片外,是否还有isCover为1的
            QueryWrapper<Picture> wrapper = new QueryWrapper<>();
            wrapper.eq("album_id", albumId);
            wrapper.eq("is_cover", 1);
            Integer count = pictureMapper.selectCount(wrapper);
            if (count != 0) {
                String message = "设置" + tips[coverId] + "失败,该图片所属相册的封面已经存在!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_UPDATE, message);
            }
        }
        if (coverId == 0) { // 此步判断避免isCover为1的数据再进来查一遍sql
            Long albumId = queryPicture.getAlbumId();
            // 在当前相册下的图片中查询除此次修改的图片外,是否还有isCover为1的
            QueryWrapper<Picture> wrapper = new QueryWrapper<>();
            wrapper.eq("album_id", albumId);
            wrapper.ne("id",pictureId);
            wrapper.eq("is_cover", 1);
            Integer count = pictureMapper.selectCount(wrapper);
            if (count == 0) {
                String message = "设置" + tips[coverId] + "失败,此图片为相册唯一的封面!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_UPDATE, message);
            }
        }

        // 创建图片对象,封装id和isCover这2个属性值,并进行设置
        Picture picture = new Picture();
        picture.setId(pictureId);
        picture.setIsCover(coverId);
        int rows = pictureMapper.updateById(picture);
        if (rows > 1) {
            String message = "设置" + tips[coverId] + "失败,服务器忙,请稍后再试...";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("设置成功!");
    }
}
