package com.whatsmars.fs.web.service.impl;

import org.hongxi.whatsmars.common.ImageSizeEnum;
import org.hongxi.whatsmars.common.mongo.GridFSClient;
import org.hongxi.whatsmars.common.pojo.Result;
import org.hongxi.whatsmars.common.pojo.ResultCode;
import com.whatsmars.fs.web.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Author: qing
 * Date: 14-10-12
 */
@Service
public class FileServiceImpl implements FileService {

    protected static final Logger logger = Logger.getLogger(FileService.class);

    @Autowired
    private GridFSClient gridFSClient;

    @Override
    public Result uploadFile(InputStream inputStream, String format,String uid) {
        Result result = new Result();
        try {
            String filename = gridFSClient.saveFile(inputStream,format,uid);
            result.addModel("filename",filename);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("upload file error,",e);
            result.setResultCode(ResultCode.SYSTEM_ERROR);
            result.setMessage("文件上传失败");
        }
        return result;
    }

    @Override
    public Result uploadImage(ByteArrayInputStream inputStream,String uid,Integer returnSize) {
        Result result = new Result();
        try {
            String filename = gridFSClient.saveImage(inputStream,uid);
            result.addModel("filename",filename);

            if(returnSize != null && returnSize > 0) {
                //找出符合returnSize的path
                for (ImageSizeEnum e : ImageSizeEnum.values()) {
                    if (e.size >= returnSize) {
                        result.addModel("uri",e.path + "/" + filename);
                    }
                }
                //返回最大尺寸
                result.addModel("uri",ImageSizeEnum.PIXELS_MAX.path + "/" + filename);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("upload image error,",e);
            result.setResultCode(ResultCode.SYSTEM_ERROR);
            result.setMessage("图片上传失败");
        }
        return result;
    }

    @Override
    public Result delete(String filename) {
        Result result = new Result();
        try {
            gridFSClient.delete(filename);
            result.addModel("filename",filename);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("delete file error,",e);
            result.setResultCode(ResultCode.SYSTEM_ERROR);
            result.setMessage("文件删除失败");
        }
        return result;
    }

    @Override
    public Result displayImage(String filename,String path) {
        Result result = new Result();
        try {
            InputStream inputStream = gridFSClient.getImage(filename,path);
            if(inputStream != null) {
                result.addModel("inputStream", inputStream);
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }


    @Override
    public Result displayFile(String filename) {
        Result result = new Result();
        try {
            InputStream inputStream = gridFSClient.getFile(filename);
            if(inputStream != null) {
                result.addModel("inputStream", inputStream);
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setResultCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }
}
