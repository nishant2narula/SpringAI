package com.learn.AI.SpringAI.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private AIService aiService;

     @Test
    public void testGetJoke(){
        var joke = aiService.getJoke("Cats");
        System.out.println(joke);
     }

     @Test
     public void testEmbeddedText(){
         var embed = aiService.getEmbedding("This is about learning Spring AI");
         System.out.println("Embeeded value is: "+embed);
         System.out.println("Length of embeded test is: "+embed.length);
         for(float e:embed){
             System.out.println("Length of each embeded character is: "+e);
         }
     }

     @Test
    public void testStoreData(){
         aiService.ingestDataToVectorStore();
     }

    @Test
    public void testSimilaritySearch(){
        var response = aiService.similaritySearch("crime movie");
        for(var e:response){
            System.out.println(e);
        }

    }
}
