package com.langchain.mistrallangchain.service;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;

import org.springframework.web.client.RestClient;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Service
public class MistralModelClient {
	
	private final ChatModel modelbean;
	
		
	public MistralModelClient(ChatModel modelbean) {
		//System.out.println("MistralModelClientSTART");
		this.modelbean = modelbean;
	}
	
	public String testBean(String myquestion) {
		
		System.out.println("MistralModelClientSTART");
		//String responselangbean = modelbean.chat("Say 'Hello World'");
		String responselangbean = modelbean.chat(myquestion);
		System.out.println("MistralModelClientRESPONSE = " + responselangbean);
		
		
		return "MistralModelClientFIN";
	
	
	}
	
	
//	public String testBean() {
//		    RuntimeException lastError = null;
//		    
//		    System.out.println("RESPONSELANGBEAN2");
//
//		    for (int attempt = 1; attempt <= 5; attempt++) {
//		        try {
//		        	System.out.println("RESPONSELANGBEAN3");
//		            return modelbean.chat("Say 'Hello World'");
//		        } catch (RuntimeException e) {
//		            lastError = e;
//		            if (!e.getMessage().contains("429")) throw e;   // non-rate-limit: fail fast
//		            try {
//		                Thread.sleep((long) Math.pow(2, attempt) * 1000);
//		            } catch (InterruptedException ie) {
//		                Thread.currentThread().interrupt();
//		                throw e;                                    // don't swallow interrupts
//		            }
//		        }
//		    }
//		    throw new IllegalStateException("Rate limit still exceeded after 5 attempts", lastError);
//       
//    }
}

