package cn.edu.zucc.email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
public class SendEmail {
    public static String sendemail(String p,String userid,String settoaddress){
        ApplicationContext context = new ClassPathXmlApplicationContext( "Mail.xml");
        JavaMailSenderImpl sender = (JavaMailSenderImpl)context.getBean("mailSender");
        //构建邮件
        MimeMessage message=sender.createMimeMessage();
//        String vc = String.valueOf((int)(((Math.random()*9+1))*1000));
        String vc = p+"/modifyuser1?userid="+userid;
        try {
            MimeMessageHelper helper= new MimeMessageHelper(message,true);
            helper.setFrom("845549442@qq.com");
            helper.setTo(settoaddress);
            message.setSubject("Spring Mail Test");
            //第二个参数true表明信息类型是multipart类型
            System.out.println(vc);
            helper.setText("<a href="+vc+">点击此处修改密码</a >",true);
            sender.send(message);

        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
        return vc;
    }
}
