package com.langchain.mistrallangchain;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.mistralai.MistralAiChatModel.MistralAiChatModelBuilder;
import dev.langchain4j.model.mistralai.MistralAiChatModelName;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.agent.tool.ReturnBehavior;                                             
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.segment.TextSegment;

import com.langchain.mistrallangchain.component.ChatComponent;
import com.langchain.mistrallangchain.config.ClientConfig;
import com.langchain.mistrallangchain.service.MyAIService;

import org.springframework.ai.chat.messages.UserMessage;

import org.springframework.ai.chat.client.ChatClient.Builder;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = {
		org.springframework.ai.model.chat.client.autoconfigure.ChatClientAutoConfiguration.class,
		org.springframework.ai.model.mistralai.autoconfigure.MistralAiChatAutoConfiguration.class,
		org.springframework.ai.model.mistralai.autoconfigure.MistralAiEmbeddingAutoConfiguration.class,
		org.springframework.ai.model.mistralai.autoconfigure.MistralAiModerationAutoConfiguration.class,
		org.springframework.ai.model.mistralai.autoconfigure.MistralAiOcrAutoConfiguration.class
	    //OpenAiAutoConfiguration.class,
	    //AzureOpenAiAutoConfiguration.class
	} , scanBasePackages = "com.langchain.mistrallangchain")
public class MistrallangchainApplication {
	
	
	public static MyAIService myaiservice;
	
	public static InMemoryEmbeddingStore<TextSegment> embeddingStore;
	
	public static InMemoryEmbeddingStore<TextSegment> embeddingStorebis;
	
	//Initialiser par l'adrese du BEAN ChatComponent
	//dans ApplicationRunner run(ChatComponent chatcomponent)
	//aprés la fin de cette classe MistrallangchainApplication
	public static ChatComponent chatcomponent;
	
	 @Value("${app.environment:production}")
	 private String developer;
		
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
	
	public static void main(String[] args) {
		SpringApplication.run(MistrallangchainApplication.class, args);
		
		System.out.println("MISTRALLANGCHAIN1-MAIN-START");
//		MistralAiChatModel chatModel = MistralAiChatModel.builder()
//		.apiKey(System.getenv("MISTRAL_AI_API_KEY")) // Please use your own Mistral AI API key
//		.modelName(MistralAiChatModelName.MISTRAL_SMALL_LATEST)
//		.logRequests(true)
//		.logResponses(true)
//		.build();

		ChatModel chatModel = MistralAiChatModel.builder()
		//.baseUrl("https://api.mistral.ai/v1/chat/completions")
		.baseUrl("https://api.mistral.ai/v1")
		.apiKey(System.getenv("MISTRAL_AI_API_KEY")) // Please use your own Mistral AI API key
		//.modelName(MistralAiChatModelName.MISTRAL_SMALL_LATEST)
		.modelName("mistral-tiny")
		//.responseFormat(ResponseFormat.JSON)
		.logRequests(true)
		.logResponses(true)
		//.returnThinking(true)
		.build();
		
		//List<Document> documents = FileSystemDocumentLoader.loadDocuments("/home/broaduser/SPRING-AI-EXAMPLES");
		List<Document> documents = FileSystemDocumentLoader.loadDocuments("/home/broaduser/eclipse-workspace-LUNA2/MyJQUERY/js");
		System.out.println("FILESYSTEMDOCUMENTLOADER-LISTSIZE = " + documents.size());
		
		System.out.println("FILESYSTEMDOCUMENTLOADER1");
		int i = 0;
		for (Document document : documents)
		{	
			i++;
			//if(i==5)
			//{
			//System.out.println("DOCUMENTTEXT" + i + " = " + document.text());
			//System.out.println("DOCUMENTMETADATA" + i + " = " + document.metadata());
			//}
		};
		System.out.println("FILESYSTEMDOCUMENTLOADER2");
		
		
		System.out.println("EMBEDDINGSTOREINGESTOR1");
		String my_string = "Creating LangChain documents has never been easier!";
		Document langchain_doc = Document.from(my_string);
		EmbeddingStoreIngestor.ingest(langchain_doc, embeddingStore);
		//String serializedStore = embeddingStore.serializeToJson();
		//System.out.println("EMBEDDINGSTOREINGESTOR = " + serializedStore);
		System.out.println("EMBEDDINGSTOREINGESTOR2");
		
		System.out.println("EMBEDDINGSTOREINGESTORBIS1");
		//Document document = FileSystemDocumentLoader.loadDocument("/home/broaduser/eclipse-workspace-LUNA2/MyJQUERY/ch01/ch01.txt", new TextDocumentParser());
		Document document = FileSystemDocumentLoader.loadDocument("/home/broaduser/eclipse-workspace-LUNA2/MyJQUERY/ch01/ch01.txt");
		EmbeddingStoreIngestor.ingest(document, embeddingStorebis);
		System.out.println("EMBEDDINGSTOREINGESTORBIS2");
		
		String raganswer = myaiservice.chatragassistant();
		System.out.println("MistrallangchainApplication-CHATRAGASSISTANT = " + raganswer + "\n");
		
		try {
			//String responseother = chatModel.chat("Say 'Hello World'");
			//String responseother = chatModel.chat("Explain quantum computing in 1 simple sentence.");
			//String responseother = chatModel.chat("What do you think about the JAVA Language in 1 simple sentence.");
			//String responseother = chatModel.chat("Why did you choose 'Mistral' as the name of your product.");
			//String responseother = chatModel.chat("What do you think of Mister Yann LECUN.");
			//System.out.println("RESPONSEOTHER");
			//String responseother = chatModel.chat("Who is Florent COUDERT engineer from Grenoble INP in 1 simple sentence.");
			//String responseother = chatModel.chat("What do you think about the OCCITAN Language in 1 simple sentence.");
			//System.out.println("RESPONSEOTHER = " + responseother);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
		
		
		
//String question = "Explain quantum computing in 1 simple sentences.";

//String response =  chatModel.chat("Say 'Hello World'");
//System.out.println(response);
		
		System.out.println("MistrallangchainApplication-CHATCOMPONENT1");
		//String response =  chatcomponent.testQuestion("Say 'Hello World'");
		//sSystem.out.println(response);
		System.out.println("MistrallangchainApplication-CHATCOMPONENT2");
		
		//String answer5 = myaiservice.chatassistant5();
		//System.out.println("MistrallangchainApplication-SQUAREROOT = " + answer5 + "\n");
		//String answer6 = myaiservice.chatassistant6();
		//System.out.println("MistrallangchainApplication-AISERVICESASTOOLS = " + answer6 + "\n");
		
		System.out.println("MistrallangchainApplication-CHATADVANCEDASSISTANT1");
		//String answer7 = myaiservice.chatadvancedassistant();
		//System.out.println("MistrallangchainApplication-CHATADVANCEDASSISTANT = " + answer7 + "\n");
		System.out.println("MistrallangchainApplication-CHATADVANCEDASSISTANT2");
	
		
		
//chatClient.prompt()
//.user(question)
//.call()
//.chatResponse()
//.getResult()
//.getOutput()
//.getContent();
		
		System.out.println("MYAISERVICE1");
		//String answer1 = myaiservice.chatassistant1();
		//System.out.println("MYAISERVICE = " + answer1);
		System.out.println("MYAISERVICE2");

//		Assistant assistant1 = AiServices.create(Assistant.class, chatModel);
//		
//		System.out.println("ASSISTANT1");
//		String answer1 = assistant1.chat("Hello1");
//		System.out.println("ASSISTANT1 = " + answer1);
//		
//		Assistant assistant2 = AiServices.builder(Assistant.class)                             
//	                .chatModel(chatModel)
//	                .tools(new ThrowingTool())                                                    
//	                .build();
//		
//		System.out.println("ASSISTANT2");
//		String answer2 = assistant2.chat("Hello2");
//		System.out.println("ASSISTANT2 = " + answer2);
//
//		
//		System.out.println("MISTRALLANGCHAIN-MAIN-END");



//- method: POST
//- url: https://api.mistral.ai/v1/chat/completions
//- headers: [Authorization: Beare...Dd], [Content-Type: application/json], [User-Agent: langchain4j-mistral-ai]
//- body: {
//  "model" : "mistral-small-latest",
//  "messages" : [ {
//    "role" : "user",
//    "content" : [ {
//      "text" : "Say 'Hello World'",
//      "type" : "text"
//    } ]
//  } ],
//  "stream" : false,
//  "stop" : [ ]
		

	
	}
	
	@Bean
	ApplicationRunner debug(ChatModel modelbean) {   // constructor/parameter injection
	    return args -> System.out.println("ChatModel bean OK: " + modelbean.getClass());
	}
	
	@Bean
	ApplicationRunner run(ChatComponent chatcomponent,MyAIService myaiservice,InMemoryEmbeddingStore<TextSegment> embeddingStore,InMemoryEmbeddingStore<TextSegment> embeddingStorebis) {   // constructor/parameter injection
	    return args -> {this.chatcomponent = chatcomponent;
	    				this.myaiservice = myaiservice;
	    				this.embeddingStore = embeddingStore;
	    				this.embeddingStorebis = embeddingStorebis;
	    				};
	    				
	    }
	    			 

		
	
	
//	 @Bean
//	 @Profile("!test")
//	 public ApplicationRunner run(ChatClient.Builder builder) {
//	@Override
//	public void run(String... args) throws Exception {
//		 
//	 System.out.println("Je suis dans MISTRALCHATCLIENT-RUN");
//		 
//	
//	 }	
	
//	@Bean
//   CommandLineRunner cli(MistralAiChatModelBuilder builder, ConfigurableApplicationContext context) {
//       return args -> {
//           var chat = builder
//              	.modelName(MistralAiChatModelName.MISTRAL_SMALL_LATEST)
//           		.apiKey(System.getenv("MISTRAL_AI_API_KEY"))
//           		.build();
//           System.out.println("\nSpring AI Hello World!");
//           System.out.println("USER: Tell me a joke");
//           System.out.println("ASSISTANT: " + 
//                   ((ChatClient) chat).prompt("Tell me a joke").call().content());
//           System.out.println("\nHello World demo completed!");
//           context.close();
//       };
//   }

}
