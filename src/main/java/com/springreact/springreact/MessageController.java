package com.springreact.springreact;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
//@RequestMapping(value = "/kafka")
public class MessageController {

	@RequestMapping("/")
	void handleService(HttpServletResponse response) throws IOException{
		response.sendRedirect("/index.html");
	}
	
	private KafkaService service;
	
	public MessageController(KafkaService serviceMessage) {
		this.service = serviceMessage;
		
	}
	
	
	@GetMapping(value = "/kafka/getMsgs")
	public List<String> getMessage(){
		
		return service.consumeMessage();
		
	}
	@GetMapping(value = "/kafka/sendMessage/{message}")
	public String sendMessage(@PathVariable("message") String message) throws Exception{
		System.out.println(message);
		return service.producedMessage("sent "+ message);
	}
}
