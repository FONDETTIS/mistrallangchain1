package com.langchain.mistrallangchain.component;

import org.springframework.stereotype.Component;

import com.langchain.mistrallangchain.service.MistralModelClient;

import dev.langchain4j.agent.tool.Tool;

@Component
public class ChatComponent {
	
	public final MistralModelClient mistralmodelclient; 
	
	ChatComponent(MistralModelClient mistralmodelclient) {
        this.mistralmodelclient = mistralmodelclient;
    }
	
	public String testQuestion(String myquestion) {
		System.out.println("CHATCOMPONENT");
        return mistralmodelclient.testBean(myquestion);
    }
	
}
