package com.langchain.mistrallangchain.service;

import java.util.List;
import java.util.ArrayList;


import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

import org.springframework.web.client.RestClient;

import java.time.LocalDate;

import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.langchain.mistrallangchain.inter.Friend;
//import com.networknt.schema.Result;
import dev.langchain4j.service.Result;



@Service
public class MyAIService {
	
	@Autowired
	ChatModel modelbean;
	
	@Autowired
	InMemoryEmbeddingStore<TextSegment> embeddingStore;
	
		
	public record Answer(int n) {}  
	 
	public static class ThrowingTool {                                                        
        @Tool(returnBehavior = ReturnBehavior.IMMEDIATE)
        public Answer doWork(String input) {                                                  
            // First call from the LLM carries bad args — the tool rejects them.
            // We expect the LLM to receive the error and retry, not for IMMEDIATE to fire.   
            throw new IllegalArgumentException("bad input: " + input);                        
        }                                                                                     
    }
	
	interface Assistant {

	    String chat(String userMessage);
	}
	
	interface RagAssistant {

	    String chat(String userMessage);
	}
	
	interface AssistantWithChatParams {

	    String chatParam(@UserMessage String userMessage, ChatRequestParameters params);
	}
	
	interface Assistantbis {
	    
	    //@UserMessage("Generate an outline for the article on the following topic: {{it}}")
	    //Result<List<String>> generateOutlineFor(String topic);
	}
	
	interface MathGenius {
	    
	    String ask(String question);
	
	}
	
	interface AdvancedMathTools {
		
		String askadvanced(String question);
	}
	
	class Calculator {
	    
	    @Tool
	    double add(int a, int b) {
	        return a + b;
	    }

	    @Tool("Sums 2 given numbers")
		double sum(double a, double b) {
		    return a + b;
		}

		@Tool("Returns a square root of a given number")
		double squareRoot(double x) {
		    return Math.sqrt(x);
		}
	}
	
	class AdvancedMathTool extends Calculator {

	    @Tool("Calculates the product of two numbers")
	    int multiply(int a, int b) {
	        return a * b;
	    }

		public String askadvanced(String string) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
    interface RouterAgent {

        @dev.langchain4j.service.UserMessage("""
            Analyze the following user request and categorize it as 'legal', 'medical' or 'technical',
            then forward the request as it is to the corresponding expert provided as a tool.
            Finally return the answer that you received from the expert without any modification.

            The user request is: '{{it}}'.
            """)
        String askToExpert(String request);
    }

    interface MedicalExpert {

        @dev.langchain4j.service.UserMessage("""
            You are a medical expert.
            Analyze the following user request under a medical point of view and provide the best possible answer.
            The user request is {{it}}.
            """)
        @Tool("A medical expert")
        String medicalRequest(String request);
    }

    interface LegalExpert {

        @dev.langchain4j.service.UserMessage("""
            You are a legal expert.
            Analyze the following user request under a legal point of view and provide the best possible answer.
            The user request is {{it}}.
            """)
        @Tool("A legal expert")
        String legalRequest(String request);
    }

    interface TechnicalExpert {

        @dev.langchain4j.service.UserMessage("""
            You are a technical expert.
            Analyze the following user request under a technical point of view and provide the best possible answer.
            The user request is {{it}}.
            """)
        @Tool("A technical expert")
        String technicalRequest(String request);
    }
	
	
	Assistant getassistant1()
	{
		Assistant assistant1 = AiServices.create(Assistant.class,modelbean);
		return assistant1;
	}
	
	Assistant getassistant2()
	{
		//FlUENTFORM
		Assistant assistant2 = AiServices.builder(Assistant.class)                             
                .chatModel(modelbean)
                .tools(new ThrowingTool())                                                    
                .build();
		return assistant2;
	}
	
	Assistant getassistant3()
	{
		//FlUENTFORM
		//In some circumstances it can be useful to modify the ChatRequest
		//before it is sent to the LLM
		Assistant assistant3 = AiServices.builder(Assistant.class)
			    .chatModel(modelbean)
			    //.chatRequestTransformer(transformingFunction)  // Configures the transformation function to be applied to the ChatRequest
			    .build();
		
		return assistant3;
	}
	
	AssistantWithChatParams getassistant4()
	{
		
		AssistantWithChatParams assistant4 = AiServices.builder(AssistantWithChatParams.class)
			    .chatModel(modelbean)  // or whichever model
			    .build();
		
		return assistant4;
	}
	
	MathGenius getassistant5()
	{
		//Utilisation de TOOLS
		MathGenius mathGenius = AiServices.builder(MathGenius.class)
			    .chatModel(modelbean)
			    .tools(new Calculator())
			    .build();
		
		return mathGenius;
		
		
	}
	
	AdvancedMathTools getadvancedassistant()
	{
	
		AdvancedMathTools advancedassistant = AiServices.builder(AdvancedMathTools.class)
		    .chatModel(modelbean)
		    .tools(new AdvancedMathTool()) // both "sum" and "multiply" are available
		    .build();
		
		return advancedassistant;
	
	}
	
	MedicalExpert getmedicalexpert()
	{
		MedicalExpert medicalExpert = AiServices.builder(MedicalExpert.class)
		        .chatModel(modelbean)
		        .build();
		
		return medicalExpert;
	}
	
	LegalExpert getlegalexpert()
	{
		LegalExpert legalExpert = AiServices.builder(LegalExpert.class)
		        .chatModel(modelbean)
		        .build();
		
		return legalExpert;
	}
	
	TechnicalExpert gettechnicalexpert()
	{
		TechnicalExpert technicalExpert = AiServices.builder(TechnicalExpert.class)
		        .chatModel(modelbean)
		        .build();
		
		return technicalExpert;
	}
	
	public RouterAgent getrouteragent()
	{
		MedicalExpert medicalExpert = getmedicalexpert();
		LegalExpert legalExpert = getlegalexpert();
		TechnicalExpert technicalExpert = gettechnicalexpert();
		
		RouterAgent routerAgent = AiServices.builder(RouterAgent.class)
		        .chatModel(modelbean)
		        .tools(medicalExpert, legalExpert, technicalExpert)
		        .build();
		
		return routerAgent;
	}
	
	public RagAssistant getragassistant()
	{
		
	RagAssistant ragassistant = AiServices.builder(RagAssistant.class)
		    .chatModel(modelbean)
		    .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
		    .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
		    .build();
	
		return ragassistant;
	
	}
	
	
	public String chatassistant1() {
		System.out.println("CHATASSISTANT1");
		String answer1 = getassistant1().chat("Hello1");
		return answer1;
	}
	
	public String chatassistant2() {
		String answer2 = getassistant2().chat("Hello1");
		return answer2;
	}
	
	public String chatassistant5() {
		System.out.println("MYAISERVICE-SQUAREROOT");
		String answer5 = getassistant5().ask("What is the square root of 475695037565?");
		return answer5;
	}
	
	public String chatadvancedassistant() {
		System.out.println("MYAISERVICE-MULTIPLY");
		String answer6 = getadvancedassistant().askadvanced("Can you multiply 20 by 20?");
		return answer6;
	}
	
	public String chatassistant6() {
		System.out.println("MYAISERVICE-SERVICESASTOOLS");
		String answer6 = getrouteragent().askToExpert("I broke my leg what should I do");
		return answer6;
	}
	
	public String chatragassistant() {
		System.out.println("MYAISERVICE-RAGASSISTANT");
		String answer7 = getragassistant().chat("How to do Easy RAG with LangChain4j?");
		return answer7;
	}
	
	
	
	public void getFriend() {
		
		Friend friend = AiServices.builder(Friend.class)
			    .chatModel(modelbean)
			    .systemMessageProvider(chatMemoryId -> "You are a good friend of mine. Answer using slang.")
			    .systemMessageTransformer(systemMessage -> systemMessage + " Today's date is " + LocalDate.now() + ".")
			    .systemMessageTransformer((systemMessage, context) ->
	            	systemMessage + " Tenant: " + context.invocationParameters().get("tenant") + ".")
			    .build();
		
	}
	
		
}
