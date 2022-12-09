package cn.tedu.anhuicsmall.product.web;

import cn.tedu.anhuicsmall.product.mapper.CategoryMapper;
import cn.tedu.anhuicsmall.product.pojo.entity.Category;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Test {

    @Autowired
    private CategoryMapper categoryMapper;

    public void test(){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        List<List<?>> recursion = new ArrayList<>();
        List<Object> objects = categoryMapper.selectObjs(wrapper);
        recursion.add(objects);
        System.out.println(recursion);
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }
}
