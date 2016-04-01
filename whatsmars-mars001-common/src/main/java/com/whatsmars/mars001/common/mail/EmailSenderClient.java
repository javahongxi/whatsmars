package com.whatsmars.mars001.common.mail;

import org.apache.commons.mail.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuguanqing on 15/4/11.
 */
public class EmailSenderClient {

    private String hostName;//发送端Host

    private int smtpPort;//发送端口

    private String username;

    private String password;

    private String fromAddress;//发送邮件的发送地址

    private boolean sslOn = true;

    private static final String DEFAULT_CHARSET = "utf-8";

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setSslOn(boolean sslOn) {
        this.sslOn = sslOn;
    }


    public void sendEmail(Email email) throws Exception {
        email.setAuthentication(username, password);
        email.setCharset(DEFAULT_CHARSET);
        email.setFrom(fromAddress);
        email.setSSLOnConnect(sslOn);
        email.setHostName(hostName);
        email.setSmtpPort(smtpPort);
        email.send();
    }

    public void sendTextEmail(String targetAddress,String title,String content) throws Exception {
        Email email = new SimpleEmail();
        email.setMsg(content);
        email.setSubject(title);
        email.addTo(targetAddress);
        sendEmail(email);
    }


    /**
     * content为html，此方法将会对html进行转义。
     * @param targetAddress
     * @param title
     * @param content
     * @throws Exception
     */
    public void sendHtmlEmail(String targetAddress,String title,String content) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setSubject(title);
        email.setHtmlMsg(content);
        email.addTo(targetAddress);
        sendEmail(email);
    }


    /**
     * 发送多媒体邮件，可以携带附件信息
     * @param urls 附件的URL,key为附件的名称
     */
    public void sendMultipartEmail(String targetAddress,String title,String content,Map<String,URL> urls) throws Exception {
        MultiPartEmail email = new MultiPartEmail();
        for(Map.Entry<String,URL> entry : urls.entrySet()) {
            email.attach(entry.getValue(),entry.getKey(),EmailAttachment.ATTACHMENT);
        }
        email.setSubject(title);
        email.addTo(targetAddress);
        email.setMsg(content);
        sendEmail(email);

    }


    public static void main(String[] args) throws Exception{
        EmailSenderClient client = new EmailSenderClient();
        client.setSmtpPort(25);
        client.setHostName("smtp.qq.com");
        client.setUsername("service@xuehaodai.com");
        client.setPassword("xuehaodai99");//您的邮箱密码
        client.setSslOn(true);
        client.setFromAddress("service@xuehaodai.com");

        String targetAddress = "shift_alt_ctrl@163.com";
        //client.sendTextEmail("shift_alt_ctrl@163.com","测试邮件","是否可以收到邮件！");
        Map<String,URL> attaches = new HashMap<String, URL>();
        attaches.put("logo",new URL("http://www.baidu.com/img/bd_logo1.png"));
        attaches.put("logo2",new URL("http://commons.apache.org/proper/commons-email/images/commons-logo.png"));
        client.sendMultipartEmail(targetAddress, "测试邮件", "test", attaches);
        System.out.println("发送成功！");
    }
}
