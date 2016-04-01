package com.whatsmars.mars001.common.util;

import com.whatsmars.mars001.common.pojo.Result;
import com.whatsmars.mars001.common.pojo.ResultCode;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Author: qing
 * Date: 14-10-14
 */
public class ResultHelper {

    /**
     * 页面渲染
     * @param result
     * @return
     */
    public static String renderAsJson(Result result) {
        JSONObject source = new JSONObject();
        source.put("code",result.getResultCode().code);
        source.put("message",result.getResultCode().getMessage());
        if(result.getMessage() != null) {
            source.put("message",result.getMessage());
        }
        //渲染实际的数据
        Map<String,Object> model = result.getAll();
        String responseUrl = result.getResponseUrl();
        if(responseUrl != null) {
            model.put("_redirect_url",responseUrl);
        }
        source.put("data",model);
        return source.toString();
    }

    public static String renderAsJson(ResultCode resultCode) {
        return renderAsJson(resultCode,null);
    }

    /**
     * 直接跳转URL
     * @param responseUrl
     * @return
     */
    public static String renderAsJson(String responseUrl) {
        Result result = new Result();
        result.setSuccess(true);
        result.setResponseUrl(responseUrl);
        return renderAsJson(result);
    }

    /**
     * 直接提示信息
     * @param resultCode
     * @param message
     * @return
     */
    public static String renderAsJson(ResultCode resultCode,String message) {
        JSONObject source = new JSONObject();
        source.put("code",resultCode.code);
        if(message == null) {
            source.put("message", resultCode.getMessage());
        }else {
            source.put("message",message);
        }
        return source.toString();
    }


}
