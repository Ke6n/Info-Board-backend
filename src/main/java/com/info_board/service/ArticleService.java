package com.info_board.service;

import com.info_board.pojo.Article;
import com.info_board.pojo.PageBean;

public interface ArticleService {

    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    Article findById(Integer id);

    void delete(Integer id);
}
