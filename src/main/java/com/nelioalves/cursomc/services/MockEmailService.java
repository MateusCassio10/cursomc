package com.nelioalves.cursomc.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public class MockEmailService extends AbstractEmailService{
    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage message) {
        LOG.info("Simulando o envio de email...");
        LOG.info(message.toString());
        LOG.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        LOG.info("Simulando o envio de email HTML...");
        LOG.info(message.toString());
        LOG.info("Email enviado");
    }
}
