 package com.itlong.whatsmars.rpc.protocol.http;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import java.io.UnsupportedEncodingException;
 import java.util.Iterator;
 import java.util.Map.Entry;

 public class HttpResponseMessage extends HttpMessage
 {
   private static Logger logger = LoggerFactory.getLogger(HttpResponseMessage.class);
 
   private int statusCode = 200;
 
   private String statusMessage = "OK";
 
   public HttpResponseMessage()
   {
     this.messageHeader.put("Content-Type", "text/json; charset=UTF-8");
   }
 
   public int getStatusCode()
   {
     return this.statusCode;
   }
 
   public void setStatusCode(int statusCode) {
     this.statusCode = statusCode;
   }
 
   public String getStatusMessage() {
     return this.statusMessage;
   }
 
   public void setStatusMessage(String statusMessage) {
     this.statusMessage = statusMessage;
   }
 
   public byte[] getByteBody() throws UnsupportedEncodingException {
     if (this.messageBody == null) {
       return new byte[0];
     }
     return this.messageBody.getBytes("UTF-8");
   }
 
   public int getContentLength() throws UnsupportedEncodingException {
     if (this.messageBody == null) {
       return 0;
     }
     return this.messageBody.getBytes("UTF-8").length;
   }
 
   public String toString()
   {
     StringBuilder sb = new StringBuilder();
     sb.append("\r\n\r\n");
     sb.append("\r\n");
     sb.append("HTTP/1.1").append(" ").append(200).append(" ").append(this.statusMessage).append("\r\n");
 
     Iterator iterator = this.messageHeader.entrySet().iterator();
     while (iterator.hasNext()) {
       Entry entry = (Entry)iterator.next();
       sb.append((String)entry.getKey()).append(": ").append((String)entry.getValue()).append("\r\n");
     }
     try
     {
       sb.append("Content-Length").append(": ").append(String.valueOf(getContentLength()));
     }
     catch (Exception e) {
       logger.error("Encoding Error!", e);
     }
     sb.append("\r\n\r\n");
     sb.append(this.messageBody);
     sb.append("\r\n\r\n");
     return sb.toString();
   }
 }

