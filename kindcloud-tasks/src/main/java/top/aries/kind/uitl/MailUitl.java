package top.aries.kind.uitl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUitl {

    /**
     * 发送邮件
     *
     * @param userEmail
     * @param title
     * @param content
     * @throws Exception
     */
    public static void SendMail(String userEmail, String title, String content) throws Exception {
        /*Properties prop = new Properties();
        prop.setProperty("mail.smtp.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true"); // 是否要验证用户
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.port", "465");*/
        Properties prop = PropertiesUtil.getProperties("config/mail.properties");
        String host = prop.getProperty("mail.smtp.host");
        String user = prop.getProperty("mail.smtp.user");
        String password = prop.getProperty("mail.smtp.password");
        // 使用JavaMail发送邮件5个步骤
        // 1.创建session
        Session session = Session.getInstance(prop);
        // 开启session的debug模式 查看程序发送Email的运行状态
        session.setDebug(true);
        // 2.通过session得到transport对象
        Transport ts = session.getTransport();
        // 3.使用邮箱的用户名和密码连上邮件服务器，发件人需要
        // 提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够
        // 正常发送邮件给收件人 QQ邮箱需要授权码
        //ts.connect("smtp.qq.com", "kindaries@qq.com", "yuvpvchqfqkwbibh");
        ts.connect(host, user, password);
        // 4.创建邮件
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指定发件人,必须和授权用户一致
        message.setFrom(new InternetAddress(user));
        // 指定收件人 自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        // 邮件标题
        message.setSubject(title);
        // 邮件的文本内容
        message.setContent(content, "text/html;charset=utf-8");
        // 5.发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    public static void main(String[] args) {
        try {
            SendMail("zhangshuai@szhtxx.com", "标题", "内容");
            System.out.println("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送失败！");
        }
    }

}
