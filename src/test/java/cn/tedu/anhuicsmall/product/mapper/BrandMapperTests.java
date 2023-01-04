package cn.tedu.anhuicsmall.product.mapper;

import cn.tedu.anhuicsmall.product.pojo.entity.Brand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 品牌测试类
 */
@Slf4j
@SpringBootTest
public class BrandMapperTests {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 测试分页查询
     */
    @Test
    public void page(){
        // 1:当前页  2:页面大小
        Page<Brand> page = new Page<>(2,4);// 创建分页对象
        IPage<Brand> page1 = brandMapper.selectPage(page, null);
        page1.getRecords().forEach(System.out::println);
        System.out.println("总页数为："+page.getTotal());
    }
}
