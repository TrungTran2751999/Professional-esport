package com.cg.service.email;

import com.cg.domain.esport.entities.Otp;
import com.cg.domain.esport.entities.User;
import com.cg.service.esport.otp.IOtpService;
import com.cg.service.esport.otp.OtpServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@ConfigurationProperties(prefix = "spring.mail")
@Service
public class EmailSender {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.domainServer}")
    private String domainServer;

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private String smtpPort;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starrrls.enable}")
    private String smtpStarttlsEnable;

    @Autowired
    private IOtpService otpService;

    @Transactional
    public void sendRegisterUserEmail(User recipient, String recipientEmail) {
        Otp otp = new Otp().setUser(recipient);
        otp = otpService.save(otp);
        Properties prop = new Properties();
        prop.put("mail.smtp.host", smtpHost);
        prop.put("mail.smtp.port", smtpPort);
        prop.put("mail.smtp.auth", smtpAuth);
        prop.put("mail.smtp.starttls.enable", smtpStarttlsEnable);

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            MimeMessage message = new MimeMessage(session);

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail + ",sapoproject02@gmail.com")
            );

            message.setSubject("Thông báo", "UTF-8");
            message.setText("Xin chào,"
                    + "\n\n Tài khoản của bạn đã được tạo thành công!"
                    + "\n\n Email: " + recipientEmail + ","
                    + "\n\n Tài khoản: "+ recipient.getUsername()+","
                    + "\n\n Không cung cấp đường dẫn này cho bất kỳ ai để đảm bảo tính bảo mật tài khoản."
                    + "\n\n Đây là email trả lời tự động, vui lòng không reply lại nội dung email này. Chỉ những yêu cầu hỗ trợ phù hợp mới được chúng tôi phản hồi."
                    + "\n\n Cám ơn bạn đã tham gia với chúng tôi!"
                    + "\n\n Kind regards,"
                    + "\n\n Professionl Esport"
                    ,"UTF-8");

            message.setSentDate(new Date());

            Transport.send(message);

            System.out.println("Gửi mail thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
