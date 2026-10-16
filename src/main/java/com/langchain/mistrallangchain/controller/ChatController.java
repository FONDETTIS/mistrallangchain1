package com.langchain.mistrallangchain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langchain.mistrallangchain.service.MistralModelClient;

@RestController
public class ChatController {
	
	String question = "Say 'Hello World'";
	
	private final MistralModelClient mistralmodelclient;   // injected, not new ChatService()

    ChatController(MistralModelClient mistralmodelclient) {
        this.mistralmodelclient = mistralmodelclient;
    }

    @GetMapping("/testBean")
    String ask() {
        return mistralmodelclient.testBean(question);
    }

}
