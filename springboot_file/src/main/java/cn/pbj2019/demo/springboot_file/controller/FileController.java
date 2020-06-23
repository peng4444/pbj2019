package cn.pbj2019.demo.springboot_file.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName: FileController
 * @Author: pbj
 * @Date: 2019/11/12 22:15
 * @Description: TODO  [springBoot 简单优雅是实现文件上传和下载](https://www.cnblogs.com/quellanan/p/11754070.html)
 */
@RestController
@RequestMapping("/pbj")
@Slf4j
public class FileController {
    @Value("${file.upload.url}")
    private String uploadFilePath;
    @Value("${file.download.url}")
    private String downloadFilePath;
    @Value("${file.delete.url}")
    private String deleteFilePath;

    @RequestMapping("/upload")
    public String httpUpload(@RequestParam("files") MultipartFile files[]){
        JSONObject object=new JSONObject();
        for(int i=0;i<files.length;i++){
            String fileName = files[i].getOriginalFilename();  // 文件名
            File dest = new File(uploadFilePath +'/'+ fileName);//上传重复的文件不会报错，后上传的文件会直接覆盖已经上传的文件。
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
//                log.error("{}",e);
                object.put("success",2);
                object.put("result","程序错误，请重新上传");
                return object.toString();
            }
        }
        object.put("success",1);
        object.put("result","文件上传成功");
        return object.toString();
    }

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName){
        File file = new File(downloadFilePath +'/'+ fileName);
        if(!file.exists()){
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("{}",e);
            return "下载失败";
        }
        return "下载成功";
    }

    @Scheduled(cron="0 0 3 * * ?")//定时删除
    private void deleteFiles(){
        deleteFile(new File(deleteFilePath));
    }

    public void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            log.info("暂无文件");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            String name = f.getName();
            log.info(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete();
    }
}
