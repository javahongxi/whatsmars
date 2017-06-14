 package com.itlong.whatsmars.rpc.protocol.http;

 import com.itlong.whatsmars.grpc.service.HelloResponse;
 import org.apache.mina.core.buffer.IoBuffer;
 import org.apache.mina.core.session.IoSession;
 import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
 import org.apache.mina.filter.codec.ProtocolEncoderOutput;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import java.util.Iterator;
 import java.util.Map;

 public class HttpResponseEncoder extends ProtocolEncoderAdapter
 {
   private static Logger logger = LoggerFactory.getLogger(HttpResponseEncoder.class);
 
   public void encode(IoSession iosession, Object obj, ProtocolEncoderOutput out) throws Exception {
       HttpResponseMessage respMsg = (HttpResponseMessage)obj;

       String requestName = respMsg.getMessageHeader().get("Request-Name");
       if (requestName != null && !requestName.equals("")) {
           byte[] bytes = null;
           if (requestName.equals("hello")) {
               HelloResponse.Builder response = HelloResponse.newBuilder();

               bytes = response.build().toByteArray();
           }
           StringBuilder sb = new StringBuilder();
           sb.append(respMsg.getVersion()).append(" ").append(respMsg.getStatusCode()).append(" ").append(respMsg.getStatusMessage()).append("\r\n");

           Iterator iterator = respMsg.getMessageHeader().entrySet().iterator();
           while (iterator.hasNext()) {
               Map.Entry entry = (Map.Entry)iterator.next();
               sb.append((String)entry.getKey()).append(": ").append((String)entry.getValue()).append("\r\n");
           }
           sb.append("Content-Length").append(": ").append(bytes.length);
           sb.append("\r\n\r\n");
           byte[] headBytes = sb.toString().getBytes("UTF-8");
           logger.debug("#############################[响应请求解析完毕]###############################");
           IoBuffer buf = IoBuffer.allocate(headBytes.length + bytes.length, false);
           buf.setAutoExpand(true);
           buf.put(headBytes);
           buf.put(bytes);
           buf.flip();
           out.write(buf);
       } else {
           StringBuilder sb = new StringBuilder();
           sb.append(respMsg.getVersion()).append(" ").append(respMsg.getStatusCode()).append(" ").append(respMsg.getStatusMessage()).append("\r\n");

           Iterator iterator = respMsg.getMessageHeader().entrySet().iterator();
           while (iterator.hasNext()) {
               Map.Entry entry = (Map.Entry)iterator.next();
               sb.append((String)entry.getKey()).append(": ").append((String)entry.getValue()).append("\r\n");
           }

           sb.append("Content-Length").append(": ").append(respMsg.getContentLength());
           sb.append("\r\n\r\n");
           sb.append(respMsg.getMessageBody());
           if (logger.isDebugEnabled()) {
               String msg = respMsg != null ? respMsg.toString() : "";
               if (!msg.contains("<html") && !msg.contains("<HTML")) {
                   logger.debug("resp:{}", respMsg);
               }
               logger.debug("#############################[响应请求解析完毕]###############################");
           }

           IoBuffer buf = IoBuffer.allocate(sb.toString().length(), false);
           buf.setAutoExpand(true);
           buf.put(sb.toString().getBytes("UTF-8"));
           buf.flip();
           out.write(buf);
       }
   }
 }

