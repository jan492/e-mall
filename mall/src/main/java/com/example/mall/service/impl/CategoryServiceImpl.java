package com.example.mall.service.impl;

import com.example.mall.dao.CategoryMapper;
import com.example.mall.pojo.Category;
import com.example.mall.service.ICategoryService;
import com.example.mall.vo.CategoryVo;
import com.example.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.mall.consts.MallConst.ROOT_PARENT_ID;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();

        // lambda + stream
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCategory(categoryVoList, categories);
        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> result) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id, result, categories);
    }


    private void findSubCategoryId(Integer id, Set<Integer> result, List<Category> categories) {
        for (Category category: categories){
            if (category.getParentId().equals(id)){
                result.add(category.getId());
                findSubCategoryId(category.getId(), result, categories);
            }
        }
    }

    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        // 查询子目录
        for (CategoryVo categoryVo: categoryVoList){
            List<CategoryVo> subCategoryVoList = categories.stream()
                    .filter(e -> e.getParentId().equals(categoryVo.getId()))
                    .map(this::category2CategoryVo).sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed()).collect(Collectors.toList());
            categoryVo.setSubCategories(subCategoryVoList);
            // 递归查询子目录
            findSubCategory(subCategoryVoList, categories);
        }
    }

    private CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
