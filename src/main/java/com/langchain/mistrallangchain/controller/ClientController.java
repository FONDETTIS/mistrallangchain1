package com.langchain.mistrallangchain.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langchain.mistrallangchain.service.MistralModelClient;
import com.langchain.mistrallangchain.component.ChatComponent;


@RestController
public class ClientController {
	
		@Autowired
		ChatComponent chatcomponent;
	
		@Autowired
		MistralModelClient mistralmodelclient;
	 	 
	 	String question = "Tell me a joke about the Java developpers.";
	 	
	 	String response;
		
		//private MistralModelClient mistralmodelclient;   // injected, not new ChatService()

//	    ClientController(MistralModelClient mistralmodelclient) {
//	        this.mistralmodelclient = mistralmodelclient;
//	    }

	    @GetMapping("/otherBean")
	    String ask() {
	    	response =  chatcomponent.testQuestion(question);
			System.out.println(response);
			System.out.println("ClientController-OTHERQUESTION");
	        //return mistralmodelclient.testBean();
	        return response;
	    
	        	    
	    
	    }
	    
	    
//
//	    // Le ChatClient.Builder est injecté automatiquement par Spring Boot
//	    public ClientController(ChatClient.Builder chatClientBuilder) {
//	        this.chatClient = chatClientBuilder.build();
//	    }
//
//	    @GetMapping("/joke")
//	    public String getJoke() {
//	        return this.chatClient.prompt()
//	                .user("Tell me a joke about the Java developpers.")
//	                .call()
//	                .content(); // Extrait directement le texte de la réponse
//	    }

}
