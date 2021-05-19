package cn.edu.ncu.common.util.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: XiongZhiCong
 * @Description: 邮件工具类
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Slf4j
public class MailUtil {
    /**
     * 邮箱正则表达式
     */
    static final Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    /**
     * 发送文本邮件
     *
     * @param sendMail 发件人邮箱
     * @param sendMailPwd 发件人密码
     * @param sendMailName 发件人昵称(可选)
     * @param receiveMail 收件人邮箱
     * @param receiveMailName 收件人昵称(可选)
     * @param sendSMTPHost 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
     * @param title 邮件主题
     * @param content 邮件正文
     * @author Administrator
     * @date 2017年12月13日 下午1:51:38
     */
    public static void sendMail(String sendMail, String sendMailPwd, String sendMailName, String[] receiveMail, String receiveMailName, String sendSMTPHost, String title, String content) {

        Session session = createSession(sendSMTPHost);
        // 3. 创建一封邮件
        MimeMessage message;
        try {
            message = createMimeMessage(session, sendMail, sendMailName, receiveMail, receiveMailName, title, content);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            // PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
            // 仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
            // 类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            // PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            // (1) 邮箱没有开启 SMTP 服务;
            // (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            // (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            // (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            // (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            // PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
            transport.connect(sendMail, sendMailPwd);
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 发送带附件的邮件
     *
     * @param sendMail 发件人邮箱
     * @param sendMailPwd 发件人密码
     * @param sendMailName 发件人昵称(可选)
     * @param receiveMail 收件人邮箱
     * @param receiveMailName 收件人昵称(可选)
     * @param sendSMTPHost 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
     * @param title 邮件主题
     * @param content 邮件正文
     * @author Administrator
     * @date 2017年12月13日 下午1:51:38
     */
    public static void sendFileMail(String sendMail, String sendMailPwd, String sendMailName, String[] receiveMail, String receiveMailName, String sendSMTPHost, String title, String content,
                                    InputStream is, String fileName, String port) {

        Session session = createSSLSession(sendSMTPHost, port, sendMailName, sendMailPwd);
        // 3. 创建一封邮件
        MimeMessage message;
        try {
            message = createMimeMessage(session, sendMail, sendMailName, receiveMail, receiveMailName, title, content);
            // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            MimeMultipart mm = new MimeMultipart();
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(content, "text/html;charset=UTF-8");
            mm.addBodyPart(text);
            if (null != is && is.available() > 0) {
                MimeBodyPart attachment = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(is, "application/msexcel");
                // 将附件数据添加到"节点"
                attachment.setDataHandler(new DataHandler(source));
                // 设置附件的文件名（需要编码）
                attachment.setFileName(MimeUtility.encodeText(fileName));
                // 10. 设置文本和 附件 的关系（合成一个大的混合"节点" / Multipart ）
                // 如果有多个附件，可以创建多个多次添加
                mm.addBodyPart(attachment);
            }
            message.setContent(mm);
            message.saveChanges();
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport("smtp");
            transport.connect(sendSMTPHost, sendMail, sendMailPwd);
            //            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 创建session
     *
     * @author lidoudou
     * @date 2019/2/16 14:59
     */
    private static Session createSSLSession(String sendSMTPHost, String port, String userName, String pwd) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties(); // 参数配置

        props.setProperty("mail.smtp.user", userName);
        props.setProperty("mail.smtp.password", pwd);
        props.setProperty("mail.smtp.host", sendSMTPHost);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.auth", "true");

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            //身份认证
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, pwd);
            }
        });
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
        return session;
    }

    /**
     * 创建session
     *
     * @author lidoudou
     * @date 2019/2/16 14:59
     */
    private static Session createSession(String sendSMTPHost) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties(); // 参数配置
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", sendSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        // 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        // 打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
         * // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接, // 需要改为对应邮箱的 SMTP 服务器的端口,
         * 具体可查看对应邮箱服务的帮助, // QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看) final String smtpPort = "465";
         * props.setProperty("mail.smtp.port", smtpPort);
         * props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         * props.setProperty("mail.smtp.socketFactory.fallback", "false");
         * props.setProperty("mail.smtp.socketFactory.port", smtpPort);
         */
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
        return session;
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param sendMailName 发件人昵称
     * @param receiveMail 收件人邮箱
     * @param receiveMailName 收件人昵称
     * @param title 邮件主题
     * @param content 邮件正文
     * @return
     * @throws Exception
     * @author Administrator
     * @date 2017年12月13日 下午1:55:45
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String sendMailName, String[] receiveMail, String receiveMailName, String title, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, sendMailName, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        List<InternetAddress> to = new LinkedList<>();
        for (String s : receiveMail) {
            if (pattern.matcher(s).matches()) {
                to.add(new InternetAddress(s));
            }
        }
        //Address[] addresses = new Address[]{new InternetAddress(receiveMail),new InternetAddress(receiveMail)};
        message.addRecipients(MimeMessage.RecipientType.TO, to.toArray((new InternetAddress[to.size()])));
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(title, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }

    /**
     * Spring的邮件工具类，实现了MailSender和JavaMailSender接口
     */
    private JavaMailSenderImpl mailSender;

    /**
     * 初始化邮件发送数据
     * @param host 服务器
     * @param username 发送人
     * @param passwd 发送人密码(授权码）
     */
    public void setInitData(String host, String username, String passwd){
        //创建邮件发送服务器
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(passwd);
        //加认证机制
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.timeout", 5000);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
    /**
     * 发送普通文本
     * @param email 对方邮箱地址
     * @param subject 主题
     * @param text 邮件内容
     */
    public void simpleMailSend(String email, String subject, String text) {
        //创建邮件内容
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(mailSender.getUsername());
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        mailSender.send(message);
        log.info("发送成功");
    }

    /**
     * 发送附件,支持多附件
     * //使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
     //MimeMessages为复杂邮件模板，支持文本、附件、html、图片等。
     * @param email 对方邮箱
     * @param subject 主题
     * @param text 内容
     * @param paths 附件路径，和文件名
     * @throws MessagingException
     */
    public void attachedSend(String email, String subject, String text, Map<String,String> paths) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        //创建MimeMessageHelper对象，处理MimeMessage的辅助类
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //使用辅助类MimeMessage设定参数
        helper.setFrom(mailSender.getUsername());
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text);

        if (paths != null){
            paths.forEach((k, v)->{
                //加载文件资源，作为附件
                FileSystemResource file = new FileSystemResource(v);
                try {
                    //添加附件
                    helper.addAttachment(k, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        //发送邮件
        mailSender.send(message);
    }

    /**
     * 发送html文件，支持多图片
     * @param email 对方邮箱
     * @param subject 主题
     * @param text 内容
     * @param paths 富文本中添加用到的路径，一般是图片，或者css,js文件
     * @throws MessagingException
     */
    public void richContentSend(String email, String subject, String text, Map<String, String> paths) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(mailSender.getUsername());
        helper.setTo(email);
        helper.setSubject(subject);
        //第二个参数true，表示text的内容为html，然后注意<img/>标签，src='cid:file'，'cid'是contentId的缩写，'file'是一个标记，
        //需要在后面的代码中调用MimeMessageHelper的addInline方法替代成文件
        helper.setText(text,true);
        //文件地址相对应src目录
        // ClassPathResource file = new ClassPathResource("logo.png");

        if (paths!=null){
            paths.forEach((k, v)->{
                //文件地址对应系统目录
                FileSystemResource file = new FileSystemResource(v);
                try {
                    helper.addInline(k, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        mailSender.send(message);
    }

    /**
     * 群发多人，且多附件
     * @param emails 多人邮件地址
     * @param subject 主题
     * @param text 内容
     * @param filePath 文件路径
     * @throws Exception
     */
    public void sendBatchMailWithFile(String[] emails, String subject, String text, String[] filePath) throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(new InternetAddress(MimeUtility.encodeText(mailSender.getUsername())));
        messageHelper.setSubject(subject);
        if (filePath != null) {
            // 新建一个存放信件内容的BodyPart对象
            BodyPart mdp = new MimeBodyPart();
            // 给BodyPart对象设置内容和格式/编码方式
            mdp.setContent(text, "text/html;charset=UTF-8");
            // 新建一个MimeMultipart对象用来存放BodyPart对象
            Multipart mm = new MimeMultipart();
            // 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
            mm.addBodyPart(mdp);
            // 把mm作为消息对象的内容
            MimeBodyPart filePart;
            FileDataSource filedatasource;
            // 逐个加入附件
            for (int j = 0; j < filePath.length; j++) {
                filePart = new MimeBodyPart();
                filedatasource = new FileDataSource(filePath[j]);
                filePart.setDataHandler(new DataHandler(filedatasource));
                try {
                    filePart.setFileName(MimeUtility.encodeText(filedatasource.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mm.addBodyPart(filePart);
            }
            mimeMessage.setContent(mm);
        } else {
            messageHelper.setText(text, true);
        }
        // 不能使用string类型的类型，这样只能发送一个收件人
        List<InternetAddress> list = new ArrayList<>();
        for (int i = 0; i < emails.length; i++) {
            list.add(new InternetAddress(emails[i]));
        }
        InternetAddress[] address = list.toArray(new InternetAddress[list.size()]);

        mimeMessage.setRecipients(Message.RecipientType.TO, address);
        mimeMessage = messageHelper.getMimeMessage();

        mailSender.send(mimeMessage);
    }
}