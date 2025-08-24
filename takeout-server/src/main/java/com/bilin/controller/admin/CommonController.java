package com.bilin.controller.admin;

import com.bilin.result.Result;
import com.bilin.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "Common API (Admin)")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    // Use Aliyun OSS to upload file
    @PostMapping("/upload")
    @ApiOperation("Upload File")
    public Result upload(MultipartFile file) {
        try{
            // Get original file name
            String originalFilename = file.getOriginalFilename();
            log.info("Upload file with filename: {}", originalFilename);
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + suffix;
            // Use AliyunUtils to upload the file
            String url = aliOssUtil.upload(file.getBytes(), objectName);
            // Return image path Result object
            return Result.success(url);
        }  catch (Exception e){
            log.info("File upload failed: {}", e.getMessage());
            return Result.error("File upload failed");
        }
    }
}
