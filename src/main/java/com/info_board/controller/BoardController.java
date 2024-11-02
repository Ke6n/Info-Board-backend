package com.info_board.controller;

import com.info_board.pojo.Article;
import com.info_board.pojo.PageBean;
import com.info_board.pojo.Result;
import com.info_board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/displayBoard")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,
                                          Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId){
        PageBean<Article> articleList = boardService.list(pageNum, pageSize, categoryId);
        return Result.success(articleList);
    }
}
