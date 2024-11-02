package com.info_board.service;

import com.info_board.pojo.Article;
import com.info_board.pojo.PageBean;

public interface BoardService {
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId);
}
