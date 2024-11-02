package com.info_board.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.info_board.mapper.ArticleMapper;
import com.info_board.pojo.Article;
import com.info_board.pojo.PageBean;
import com.info_board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId) {
        PageBean<Article> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);

        Page<Article> page = (Page<Article>) articleMapper.listAllPublished(categoryId);
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}
