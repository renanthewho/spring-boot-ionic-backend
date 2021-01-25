package com.renanalmeida.services;

import org.springframework.mail.SimpleMailMessage;

import com.renanalmeida.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
