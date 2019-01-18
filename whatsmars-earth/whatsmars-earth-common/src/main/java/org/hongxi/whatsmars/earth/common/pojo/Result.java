package org.hongxi.whatsmars.earth.common.pojo;

import java.util.HashMap;
import java.util.Map;

public class Result {

	
	private boolean success = false;
	
	private String message;
	
	private ResultCode resultCode = ResultCode.SYSTEM_ERROR;
	
	private Map<String,Object> model = new HashMap<String,Object>(32);

    private String responseUrl;//响应的URL，需要跳转的URL;

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
		if(success){
			resultCode = ResultCode.SUCCESS;
		}
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result(){}
	
	public Result(boolean success){
		this.success = success;
	}

    public Result addModel(String key,Object value){
		this.model.put(key, value);
		return this;
	}
	
	public Result addModel(Map<String,Object> kv){
        if(kv != null)
		    this.model.putAll(kv);
		return this;
	}

    public Object getModel(String key) {
        return this.model.get(key);
    }
	
	public Map<String,Object> getAll(){
		return this.model;
	}
}
