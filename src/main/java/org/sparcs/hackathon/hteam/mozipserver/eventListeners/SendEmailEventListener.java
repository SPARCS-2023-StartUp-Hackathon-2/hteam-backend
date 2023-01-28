package org.sparcs.hackathon.hteam.mozipserver.eventListeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendEmailEventListener {

    @Async
    @EventListener
    public void sendEmail(SendEmailEvent event) {
        MailSender mailSender = event.getMailSender();
        mailSender.send(event.getMailMessages().toArray(new SimpleMailMessage[0]));
        log.info("sending mail complete");
    }
}
