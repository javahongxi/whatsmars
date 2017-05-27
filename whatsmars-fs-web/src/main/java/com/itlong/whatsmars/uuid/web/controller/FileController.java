package com.itlong.whatsmars.uuid.web.controller;

import com.itlong.whatsmars.common.CommonConstants;
import com.itlong.whatsmars.common.pojo.Result;
import com.itlong.whatsmars.common.pojo.ResultCode;
import com.itlong.whatsmars.common.util.DESUtils;
import com.itlong.whatsmars.common.util.ResultHelper;
import com.itlong.whatsmars.uuid.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by liuguanqing on 15/4/15.
 */
@Controller
public class FileController {

    private static final int SIZE = 1028576;//1M

    @Autowired
    private FileService fileService;

    @RequestMapping("/file_upload")
    @ResponseBody
    public String uploadFile(@RequestParam(value = "format",required = false)String format,
                             @RequestParam(value = "uid")String uid,
                             @RequestParam("token")String token,
                             HttpServletRequest request) {

        String source = DESUtils.decrypt(token, CommonConstants.FS_SECURITY_KEY);
        if(!source.equals(uid)) {
            return ResultHelper.renderAsJson(ResultCode.VALIDATE_FAILURE);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            InputStream inputStream = request.getInputStream();//body inputstream
            if (inputStream == null) {
                return ResultHelper.renderAsJson(ResultCode.PARAMETER_ERROR);
            }
            int i = 0;
            int maxSize = SIZE * 32;//最大32M
            while (true) {
                int _c = inputStream.read();
                if (_c == -1) {
                    break;
                }
                bos.write(_c);
                i++;
                if (i > maxSize) {
                    inputStream.close();
                    bos.close();
                    return ResultHelper.renderAsJson(ResultCode.VALIDATE_FAILURE, "文件尺寸超过最大限制");
                }
            }
            inputStream.close();
        } catch (Exception e) {
            return ResultHelper.renderAsJson(ResultCode.SYSTEM_ERROR);
        }

        //
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Result result = this.fileService.uploadFile(bis, format,uid);
        return ResultHelper.renderAsJson(result);
    }


    @RequestMapping("/image_upload")
    @ResponseBody
    public String uploadImage(@RequestParam("token")String token,
                              @RequestParam("uid")String uid,
                              @RequestParam(value = "return_size",required = false)Integer returnSize,
                              HttpServletRequest request) {

        String source = DESUtils.decrypt(token, CommonConstants.FS_SECURITY_KEY);
        if(!source.equals(uid)) {
            return ResultHelper.renderAsJson(ResultCode.VALIDATE_FAILURE);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            InputStream inputStream = request.getInputStream();//body inputstream
            if (inputStream == null) {
                return ResultHelper.renderAsJson(ResultCode.PARAMETER_ERROR);
            }
            int i = 0;
            int maxSize = 6 * SIZE;//最大6M
            while (true) {
                int _c = inputStream.read();
                if (_c == -1) {
                    break;
                }
                bos.write(_c);
                i++;
                if (i > maxSize) {
                    inputStream.close();
                    bos.close();
                    return ResultHelper.renderAsJson(ResultCode.VALIDATE_FAILURE, "文件尺寸超过最大限制");
                }
            }
            inputStream.close();
        } catch (Exception e) {
            return ResultHelper.renderAsJson(ResultCode.SYSTEM_ERROR);
        }

        //
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Result result = this.fileService.uploadImage(bis,uid,returnSize);
        return ResultHelper.renderAsJson(result);
    }

    @RequestMapping("/ds/{filename}")
    @ResponseBody
    public void displayFile(@PathVariable("filename")String filename,HttpServletResponse response) {
        Result result = this.fileService.displayFile(filename);
        if(result.isSuccess()) {
            InputStream stream = (InputStream)result.getModel("inputStream");
            try {
                OutputStream outputStream = response.getOutputStream();
                if (stream != null) {
                    BufferedInputStream bis = new BufferedInputStream(stream, 128);
                    while (true) {
                        int _current = bis.read();
                        if (_current < 0) {
                            break;
                        }
                        outputStream.write((byte) _current);
                    }
                }
            } catch (Exception e) {
                //
            } finally {
                try {
                    stream.close();
                }catch (Exception e) {
                    //
                }
            }
        }
    }

    @RequestMapping("/ds/{path}/{filename}")
    @ResponseBody
    public void displayImage(@PathVariable("filename")String filename,
                             @PathVariable("path")String path,
                             HttpServletResponse response) {

        Result result = this.fileService.displayImage(filename,path);
        if(result.isSuccess()) {
            InputStream stream = (InputStream)result.getModel("inputStream");
            try {
                OutputStream outputStream = response.getOutputStream();
                if (stream != null) {
                    BufferedInputStream bis = new BufferedInputStream(stream, 128);
                    while (true) {
                        int _current = bis.read();
                        if (_current < 0) {
                            break;
                        }
                        outputStream.write((byte) _current);
                    }
                }
//                String timestamp = (String)result.getModel("timestamp");
//                if(timestamp != null) {
//                    response.addDateHeader("Last-Modified",Long.valueOf(timestamp));
//                }
            } catch (Exception e) {
                //
            } finally {
                try {
                    stream.close();
                }catch (Exception e) {
                    //
                }
            }
        }
    }

}
