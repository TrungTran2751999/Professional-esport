package com.cg.service.email;

import com.cg.domain.esport.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SendEmailThread implements Runnable{
    @Autowired
    private EmailSender emailSender;
    private Thread t;
    private User user;
    private String email;
    @Override
    public void run() {
        emailSender.sendRegisterUserEmail(user, email);
    }
    public void start(User user, String email){
        this.user = user;
        this.email = email;
        t = new Thread(this);
        t.start();
    }
}
