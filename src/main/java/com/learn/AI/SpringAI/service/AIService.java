package com.learn.AI.SpringAI.service;

import com.learn.AI.SpringAI.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

        private final ChatClient chatClient;
        private final EmbeddingModel embeddingModel;
        private final VectorStore vectorStore;

    public float[] getEmbedding(String text){
            return embeddingModel.embed(text);
    }

    public void ingestDataToVectorStore(){
        /*Document document = new Document(text);
        vectorStore.add(List.of(document));*/
        List<Document> movies = List.of(
                new Document("A wrongly convicted banker forms a lifelong bond with a fellow inmate while maintaining hope and planning a brilliant, decades-long escape.",
                        Map.of("title","The Shawshank Redemption","genre","Drama","year",1994)),
                new Document("The reluctant youngest son of a powerful New York mafia family takes control of the criminal empire after an assassination attempt on his father.",
                        Map.of("title","The Godfather","genre","Crime,Drama","year",1972)),
                new Document("TBatman must face psychological warfare and absolute chaos when a sadistic, unpredictable anarchist known as the Joker wreaks havoc on Gotham City.",
                        Map.of("title","The Godfather","genre","Crime,Drama","year",1972))
        );
        vectorStore.add(movies);
    }

    public List<Document> similaritySearch(String text){
        return vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(text)
                            .topK(2)
                            .build()
        );
    }

        public String getJoke(String topic){


            String systemPrompt = """
                    You are a sarcastic joker, you make poetic jokes in 4 lines.
                    You don't make jokes about politics.
                    Give a joke on the topic: {topic}
                    """;

            PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
            String renderedText = promptTemplate.render(Map.of("topic",topic));

            var response =  chatClient.prompt()
                    .user(renderedText)
                    .advisors(
                            new SimpleLoggerAdvisor()
                    )
                    .call()
                    .entity(Joke.class);

            return response.text();
        }



}
