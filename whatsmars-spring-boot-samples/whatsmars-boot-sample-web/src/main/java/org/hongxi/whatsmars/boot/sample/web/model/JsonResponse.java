package org.hongxi.whatsmars.boot.sample.web.model;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class JsonResponse<T> {

    public static final int SUCCESS_CODE = 0;
  
    // 错误码，0表示成功
    private int code;
    // 错误原因
    private String msg;
    // 服务器下发数据时间
    private long millisecond;

    private T data;
  
    public int getCode() {
      return code;
    }
  
    public void setCode(int code) {
      this.code = code;
    }
  
    public String getMsg() {
      return msg;
    }
  
    public void setMsg(String msg) {
      this.msg = msg;
    }
  
    public long getMillisecond() {
      return millisecond;
    }
  
    public void setMillisecond(long millisecond) {
      this.millisecond = millisecond;
    }
  
    public T getData() {
      return data;
    }
  
    public void setData(T data) {
      this.data = data;
    }
  
    public static <T> JsonResponse success(T value) {
      JsonResponse<T> response = new JsonResponse<>();
      response.setCode(SUCCESS_CODE);
      response.setMsg("ok");
      response.setMillisecond(System.currentTimeMillis());
      response.setData(value);
      return response;
    }
  
    public static JsonResponse error(int ec, String em) {
      JsonResponse response = new JsonResponse<>();
      response.setCode(ec);
      response.setMsg(em);
      response.setMillisecond(System.currentTimeMillis());
      return response;
    }
}
