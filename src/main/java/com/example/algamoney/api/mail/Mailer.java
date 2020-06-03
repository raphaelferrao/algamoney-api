package com.example.algamoney.api.mail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Usuario;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	

	/*
	@EventListener
	private void teste(ApplicationReadyEvent event) {
		this.enviarEmail("strawlley@gmail.com", Arrays.asList("warysson.gomes@gmail.com"), 
				"Testando email", "Olá <br/>Teste OK!");
		System.out.println("Email enviado com sucesso!");
	}
	
	@EventListener
	private void testeTemplate(ApplicationReadyEvent event) {
		String template = "mail/aviso-lancamentos-vencidos";
		
		List<Lancamento> lancamentos = repo.findAll();
		Map<String, Object> variaveis = new HashMap();
		variaveis.put("lancamentos", lancamentos);
		
		this.enviarEmail("strawlley@gmail.com", Arrays.asList("warysson.gomes@gmail.com"), 
				"Testando email", template, variaveis);
		System.out.println("Email enviado com sucesso!");
	}
	 */
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			throw new RuntimeException("Erro ao enviar email!", e);
		}
	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, 
			String template, Map<String, Object> variaveis) {
		
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = templateEngine.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void avisarSobreLancamentosVencidos(List<Lancamento> lancVencidos, 
			List<Usuario> destinatarios) {
		
		String template = "mail/aviso-lancamentos-vencidos";
		
		Map<String, Object> variaveis = new HashMap();
		variaveis.put("lancamentos", lancVencidos);
		
		List<String> emailsDestinatarios = destinatarios.stream()
				.map( u -> u.getEmail() )
				.collect(Collectors.toList());
		
		this.enviarEmail("no-reply@email.com", emailsDestinatarios, "Lançamentos Vencidos", template, variaveis);
		
	}
	
}
