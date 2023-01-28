package org.sparcs.hackathon.hteam.mozipserver.eventListeners;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Getter
@AllArgsConstructor
public class SendEmailEvent {

    private MailSender mailSender;
    private List<SimpleMailMessage> mailMessages;
}
