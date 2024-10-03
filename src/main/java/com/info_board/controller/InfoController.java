package com.info_board.controller;

import com.info_board.pojo.Result;
import com.info_board.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/infos")
public class InfoController {
    @GetMapping("/list")
    public Result<String> list(){
        return Result.success("Infos loaded");
    }
}
