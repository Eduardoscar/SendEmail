package org.bedu.jms;

import lombok.extern.slf4j.Slf4j;
import org.bedu.jms.model.Email;
import org.bedu.jms.util.Mail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendEmail {

    @RabbitListener(queues = "${email.queue}")
    public void sentEmail (Email email) {
        log.info("Mensaje recibido ...");
        Email data = new Email(email.getSubject(), email.getTo(), email.getBody());
        Mail mail = new Mail();
        mail.send(data);
    }
}
