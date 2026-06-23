package com.learn.AI.SpringAI.dto;

public record Joke(
        String text,
        String category,
        Double laughScore,
        Boolean isNSFW
) {



}
