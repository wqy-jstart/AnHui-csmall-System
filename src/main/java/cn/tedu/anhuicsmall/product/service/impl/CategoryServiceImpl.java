package cn.tedu.anhuicsmall.product.service.impl;

import cn.tedu.anhuicsmall.product.ex.ServiceException;
import cn.tedu.anhuicsmall.product.mapper.BrandAndCategoryMapper;
import cn.tedu.anhuicsmall.product.mapper.CategoryAndAttributeTemplateMapper;
import cn.tedu.anhuicsmall.product.mapper.CategoryMapper;
import cn.tedu.anhuicsmall.product.mapper.SpuMapper;
import cn.tedu.anhuicsmall.product.pojo.dto.CategoryAddNewDTO;
import cn.tedu.anhuicsmall.product.pojo.dto.CategoryUpdateDTO;
import cn.tedu.anhuicsmall.product.pojo.entity.BrandAndCategory;
import cn.tedu.anhuicsmall.product.pojo.entity.Category;
import cn.tedu.anhuicsmall.product.pojo.entity.CategoryAndAttributeTemplate;
import cn.tedu.anhuicsmall.product.pojo.entity.Spu;
import cn.tedu.anhuicsmall.product.service.ICategoryService;
import cn.tedu.anhuicsmall.product.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    public CategoryServiceImpl() {
        log.debug("创建业务层接口实现类:CategoryServiceImpl");
    }

    // 注入分类的持久层接口
    @Autowired
    private CategoryMapper categoryMapper;

    // 注入品牌分类关联的持久层接口
    @Autowired
    private BrandAndCategoryMapper brandAndCategoryMapper;

    // 注入品牌与属性模板的持久层接口
    @Autowired
    private CategoryAndAttributeTemplateMapper categoryAndAttributeTemplateMapper;

    // 注入商品spu的持久层接口
    @Autowired
    private SpuMapper spuMapper;

    /**
     * 处理添加类别的业务
     *
     * @param categoryAddNewDTO 封装了用户添加的类别信息
     */
    @Override
    public void insert(CategoryAddNewDTO categoryAddNewDTO) {
        log.debug("开始处理添加分类的业务,参数:{}", categoryAddNewDTO);

        // 查询父级类别
        int depth = 1;// 默认深度为1
        Long parentId = categoryAddNewDTO.getParentId();// 获取客户端传入的父级id
        Category parentCategory = null;
        if (parentId != 0) { // 说明归属于某个父级
            parentCategory = categoryMapper.selectById(parentId);
            if (parentCategory == null) {
                String message = "添加类别失败,所选择的父级类别不存在!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
            }
            depth = parentCategory.getDepth() + 1;
        }
        log.debug("当前尝试添加的类型的depth值为:{}", depth);

        // 判断添加的类别名称是否存在
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("name", categoryAddNewDTO.getName());
        Integer count = categoryMapper.selectCount(categoryQueryWrapper);
        if (count > 0) {
            String message = "添加分类失败,分类名称已经被占用";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        log.debug("分类名称没有被占用,将向分类表中插入数据");
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewDTO, category);
        // 补全Category对象的值：depth >>> 使用最终的depth变量
        category.setDepth(depth);
        // 补全Category对象的值: isParent >>> 0，新增的类别的isParent固定为0
        category.setIsParent(0);//固定先为0
        log.debug("即将插入分类数据:{}", category);
        int rows = categoryMapper.insert(category);
        if (rows != 1) {// 如果插入所影响的行数不为1
            String message = "添加类别失败,服务器忙,请稍后再尝试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
        // 检查当前新增类型的父级类别，如果父类别的isParent为0，则将父级类别的isParent更新为1
        if (parentId != 0) { // 如果当前父级id不为0,说明存在父类
            if (parentCategory.getIsParent() == 0) { // 如果父类的isParent为0(暂时没有子级的情况)
                // 创建一个分类对象,用来修改父级的isParent(需设置id过滤条件)
                Category updateParentCategory = new Category();
                updateParentCategory.setId(parentId);
                updateParentCategory.setIsParent(1);
                log.debug("将父级类别的isParent更新为1,更新的参数对象:{}", updateParentCategory);
                categoryMapper.updateById(updateParentCategory);
            }
        }
    }

    /**
     * 根据id删除分类信息
     *
     * @param categoryId 分类id
     */
    @Override
    public void deleteById(Long categoryId) {
        log.debug("开始处理[根据id删除类别]的业务,参数,{}", categoryId);
        Category queryCategory = categoryMapper.selectById(categoryId);
        if (queryCategory == null) {
            String message = "删除失败,该id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
        // 检查当前尝试删除的类别是否存在子级类别：判断以上查询结果的isParent是否为1
        if (queryCategory.getIsParent() == 1) {
            // 是：当前尝试删除的类别“是父级类别”（包含子级）
            String message = "删除类别失败，尝试删除的类别仍包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        // 如果此类别关联了品牌，则不允许删除
        {
            QueryWrapper<BrandAndCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", categoryId);
            int count = brandAndCategoryMapper.selectCount(wrapper);
            if (count > 0) {
                String message = "删除类别失败,该类别包含关联品牌的数据!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        // 如果此类别关联了属性模板，则不允许删除
        {
            QueryWrapper<CategoryAndAttributeTemplate> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", categoryId);
            int count = categoryAndAttributeTemplateMapper.selectCount(wrapper);
            if (count > 0) {
                String message = "删除类别失败,该类别包含关联属性模板的数据!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        // 如果此类别关联了SPU，则不允许删除
        {
            QueryWrapper<Spu> wrapper = new QueryWrapper<>();
            wrapper.eq("category_id", categoryId);
            int count = spuMapper.selectCount(wrapper);
            if (count > 0) {
                String message = "删除类别失败,该类别包含关联SPU的数据!";
                log.debug(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        // 删除时，如果删到某个类别没有子级了，需要将它的isParent更新为0
        Long parentId = queryCategory.getParentId(); // 先拿到父级id
        if (parentId != 0) { // 如果父级id不为0,说明归属于某个父级
            QueryWrapper<Category> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", parentId);
            Integer count = categoryMapper.selectCount(wrapper); // 在归属某个父级的情况下,根据parentId去查询数量
            if (count == 1) { // 如果为1,说明在当前父级下只有目前将要删除的一个子类
                // 创建新的Category对象，更新父级的isParent为0
                Category category = new Category();
                category.setId(parentId);
                category.setIsParent(0);
                log.debug("将id为{}类别的isParent改为0", parentId);
                int rows = categoryMapper.updateById(category);
                // 判断返回值是否不为1
                if (rows != 1) {
                    // 是：抛出异常（ERR_UPDATE）
                    String message = "删除类别失败，服务器忙，请稍后再尝试！";
                    log.warn(message);
                    throw new ServiceException(ServiceCode.ERR_UPDATE, message);
                }
            }
        }

        log.debug("即将执行删除id为{}的类别数据", categoryId);
        int rows = categoryMapper.deleteById(categoryId);
        if (rows > 1) {
            String message = "删除失败,服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /**
     * 根据id修改分类信息
     *
     * @param categoryUpdateDTO 分类信息
     */
    @Override
    public void update(CategoryUpdateDTO categoryUpdateDTO) {
        log.debug("开始处理[根据id修改类别信息]的业务");
        Category queryCategory = categoryMapper.selectById(categoryUpdateDTO.getId());
        if (queryCategory == null) {
            String message = "修改失败,该类别id不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.ne("id", categoryUpdateDTO.getId());
        wrapper.eq("name", categoryUpdateDTO.getName());
        Integer count = categoryMapper.selectCount(wrapper);
        if (count > 0) {
            String message = "修改失败,修改的类别名称已存在";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        log.debug("即将修改id为{}的分类信息...", categoryUpdateDTO.getId());
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateDTO, category);
        log.debug("开始修改id为{}的分类信息", categoryUpdateDTO.getId());
        int rows = categoryMapper.updateById(category);
        if (rows != 1) {
            String message = "服务器忙,请稍后再试...";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
    }

    /**
     * 根据id查询分类信息
     *
     * @param categoryId 类别id
     * @return 返回分类实体类
     */
    @Override
    public Category selectById(Long categoryId) {
        log.debug("开始根据id:{}来查询菜单详情", categoryId);
        Category queryCategory = categoryMapper.selectById(categoryId);
        if (queryCategory == null) {
            String message = "查询失败,请求的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        return queryCategory;
    }

    /**
     * 查询父级类别列表
     * @return 返回列表集合
     */
    @Override
    public List<Object> selectByParent() {
        log.debug("开始处理查询父级列表的功能,无参!");
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        return categoryMapper.selectObjs(wrapper);
    }

    /**
     * 处理查询分类列表信息
     *
     * @return 返回查询的分类列表
     */
    @Override
    public List<Object> selectList() {
        log.debug("开始处理查询类别列表的业务,无参!");
        return categoryMapper.selectObjs(null);
    }

    /**
     * 处理根据父级类别查询自己类别的业务
     *
     * @param parentId 父级类别id
     * @return 返回查询后的列表数据
     */
    @Override
    public List<Object> listByParentId(Long parentId) {
        log.debug("开始处理查询父级id为{}的子级类别信息", parentId);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        return categoryMapper.selectObjs(wrapper);
    }

    /**
     * 处理启用分类
     *
     * @param id 要启用的分类id
     */
    @Override
    public void setEnable(Long id) {
        updateEnableById(id, 1);
    }

    /**
     * 处理禁用分类
     *
     * @param id 要禁用的分类id
     */
    @Override
    public void setDisable(Long id) {
        updateEnableById(id, 0);
    }

    /**
     * 处理显示分类
     *
     * @param id 显示分类的id
     */
    @Override
    public void setDisplay(Long id) {
        updateDisplayById(id, 1);
    }

    /**
     * 处理隐藏分类
     *
     * @param id 隐藏的分类id
     */
    @Override
    public void setHidden(Long id) {
        updateDisplayById(id, 0);
    }

    /**
     * 处理是否启用的业务逻辑
     *
     * @param categoryId 分类id
     * @param enable     1=启用;0=禁用
     */
    private void updateEnableById(Long categoryId, Integer enable) {
        String[] tips = {"禁用", "启用"};
        log.debug("开始处理[{}分类]的业务,id参数为{}", tips[enable], categoryId);
        // 根据id查询分类详情
        Category queryCategory = categoryMapper.selectById(categoryId);
        if (queryCategory == null) {
            String message = tips[enable] + "分类失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的enable与方法参数enable是否相同
        if (enable.equals(queryCategory.getEnable())) {
            String message = tips[enable] + "分类失败，管理员账号已经处于" + tips[enable] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        // 创建category对象,并封装id和enable这2个属性的值,并进行修改
        Category category = new Category();
        category.setId(categoryId);
        category.setEnable(enable);
        int rows = categoryMapper.updateById(category);
        if (rows != 1) {
            String message = tips[enable] + "分类失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }

    /**
     * 处理是否显示的逻辑
     *
     * @param categoryId 分类id
     * @param display    1=显示;0=隐藏
     */
    private void updateDisplayById(Long categoryId, Integer display) {
        String[] tips = {"隐藏", "显示"};
        log.debug("开始处理[{}分类]的业务,id参数为{}", tips[display], categoryId);
        // 根据id查询分类详情
        Category queryCategory = categoryMapper.selectById(categoryId);
        if (queryCategory == null) {
            String message = tips[display] + "分类失败,尝试访问的数据不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        // 判断查询结果中的display与方法参数要修改的display是否相同
        if (display.equals(queryCategory.getIsDisplay())) {
            String message = tips[display] + "分类失败，管理员账号已经处于" + tips[display] + "状态！";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        // 创建category对象,并封装id和display这2个属性的值,并进行修改
        Category category = new Category();
        category.setId(categoryId);
        category.setIsDisplay(display);
        int rows = categoryMapper.updateById(category);
        if (rows != 1) {
            String message = tips[display] + "分类失败，服务器忙，请稍后再次尝试！";
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改成功!");
    }
}
