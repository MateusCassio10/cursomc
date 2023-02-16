package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage message);

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage message);
}
