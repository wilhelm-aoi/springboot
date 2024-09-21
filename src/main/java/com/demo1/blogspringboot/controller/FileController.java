package com.demo1.blogspringboot.controller;

import cn.hutool.core.io.FileUtil;
import com.demo1.blogspringboot.common.AuthAccess;
import com.demo1.blogspringboot.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2024年9月17日 13:41
 */
@RestController
@RequestMapping("/file")
public class FileController {

   @Value("${ip:localhost}")
    private String ip;

    @Value("${server.port}")
    private String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        try {
            String originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new IllegalArgumentException("文件名为空"));
            String mainName = FileUtil.mainName(originalFilename);  // 获取文件名（不包括扩展名）
            String extName = FileUtil.extName(originalFilename);    // 获取文件的扩展名
            if (!FileUtil.exist(ROOT_PATH)) {
                FileUtil.mkdir(ROOT_PATH);  // 确保目录存在
            }
            String newFilename = originalFilename;
            if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果文件已存在，生成新的文件名
                newFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
            }
            File saveFile = new File(ROOT_PATH + File.separator + newFilename);
            file.transferTo(saveFile);  // 保存文件
            String url = "http://" + ip + ":" + port + "/file/download/" + newFilename;  // 构建文件下载链接
            return Result.success(url);  // 返回成功结果
        } catch (IOException e) {
            return Result.error("文件上传失败: " + e.getMessage());  // 捕获文件保存异常
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());  // 捕获文件名为空异常
        }
    }

    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        String filePath = ROOT_PATH  + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }
}
