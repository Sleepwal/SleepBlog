package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.utils.GithubUploader;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/17 13:22
 */


@RestController
public class FileUploadController {

    @Autowired
    private GithubUploader githubUploader;

    @ApiOperation("上传图片")
    @PostMapping("/github/upload")
    public Object upload(@RequestParam("img") MultipartFile multipartFile) throws IOException {
        return Result.success(githubUploader.upload(multipartFile));
    }

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
//    public final static String UPLOAD_PATH_PREFIX = "static/upload/";

    /*@PostMapping("/upload")
    public Result<?> upload(@RequestParam("img") MultipartFile uploadFile, HttpServletRequest request){
        if(uploadFile.isEmpty()){
            //返回选择文件提示
            return Result.errorResult(ResultEnum.FILE_IS_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
        logger.info("-----------上传文件保存的路径【"+ realPath +"】-----------");
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + format);
        logger.info("-----------存放上传文件的文件夹【"+ file +"】-----------");
        logger.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【"+ file.getAbsolutePath() +"】-----------");
        if(!file.isDirectory()){
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = uploadFile.getOriginalFilename();
        logger.info("-----------文件原始的名字【"+ oldName +"】-----------");
        assert oldName != null;
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        logger.info("-----------文件要保存后的新名字【"+ newName +"】-----------");
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
            logger.info("-----------【"+ filePath +"】-----------");
            return Result.okResult(filePath);
        } catch (Exception e) {
            throw new RuntimeException(ResultEnum.FILE_UPLOAD_ERROR.getMsg());
        }
    }*/


}