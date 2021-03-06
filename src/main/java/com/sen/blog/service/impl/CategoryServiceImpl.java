package com.sen.blog.service.impl;

import com.sen.blog.common.BaseServiceImpl;
import com.sen.blog.dao.CategoryDao;
import com.sen.blog.entity.Article;
import com.sen.blog.entity.Category;
import com.sen.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Auther: Sen
 * @Date: 2019/9/24 01:38
 * @Description:
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, CategoryDao> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> selectByArticleId(Article article) {
        return categoryDao.selectByArticleId(article);
    }
}
