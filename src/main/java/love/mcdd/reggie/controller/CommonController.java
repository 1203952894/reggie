package love.mcdd.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import love.mcdd.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


/**
 * 文件上传 和 下载
 * @Author:Cola
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file 与前端文件名保持 一致
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());

        // 判断 文件是否存在
        File dir = new File(basePath);
        if(!dir.exists()){
            log.info("目录不存在需要创建");
            dir.mkdirs();
            log.info("成功创建目录");
        }

        // 原始文件名
       /* String originalFilename = file.getOriginalFilename();*/

        // 使用 UUID 重新生成文件名，防止文件名造成文件覆盖

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+suffix;
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("临时文件生成成功 ");
        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name 前端文件名
     * @param response 文件流
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        // 输入流 通过输入流读取文件内容
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            // 输出流 通过输出流将文件写回浏览器 用于展示
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            // 关闭资源
            fileInputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("文件下载成功 ");

    }
}
