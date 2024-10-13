package com.info_board.controller;

import com.info_board.pojo.Result;
import com.info_board.utils.AwsUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String filename = UUID.randomUUID()
                + originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = AwsUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}
