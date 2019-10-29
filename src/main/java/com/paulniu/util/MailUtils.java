package com.paulniu.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * author:niupuyue
 * date: 2019/10/28
 * time: 23:31
 * version: 发送邮件工具类
 * desc:
 **/
public class MailUtils {

    private static final String USER = "niupuyue@aliyun.com"; // 发件人称号
    private static final String PASSWORD = "Npl13533578766"; // 如果是qq邮箱可以使用客户端授权码或者登陆密码

    /**
     * 发送验证信息邮件
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 邮件标题
     * @return
     */
    public static boolean sendMail(String to,String text,String title){
        try {
            final Properties properties = new Properties();
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.host","smtp.qq.com");

            // 发件人账号
            properties.put("mail.user",USER);
            // 发件人密码
            properties.put("mail.password",PASSWORD);

            // 构建授权信息,用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名和密码
                    String username = properties.getProperty("mail.user");
                    String password = properties.getProperty("mail.password");
                    return new PasswordAuthentication(username,password);
                }
            };
            // 使用环境属性和授权信息，创建邮箱会话
            Session mailSession = Session.getInstance(properties,authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = properties.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        MailUtils.sendMail("niupuyue@renruihr.com","你好，这是一封测试邮件，无需回复。","测试邮件");
        System.out.println("发送成功");
    }


}
