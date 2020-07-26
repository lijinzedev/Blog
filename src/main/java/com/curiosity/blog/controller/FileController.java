package com.curiosity.blog.controller;

import com.curiosity.blog.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/26
 */
@Controller
@ResponseBody
public class FileController {
    @PostMapping("/file/upload")
    public FileDto upload() {
        FileDto fileDto = new FileDto();
        return fileDto;
    }
}
