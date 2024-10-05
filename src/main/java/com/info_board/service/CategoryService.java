package com.info_board.service;

import com.info_board.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();
}
