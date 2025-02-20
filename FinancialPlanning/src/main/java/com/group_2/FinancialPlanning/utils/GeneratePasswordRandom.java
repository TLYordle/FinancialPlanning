package com.group_2.FinancialPlanning.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Component
public class GeneratePasswordRandom {

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;

    private static final Random generator = new Random();


    public String randomPassword(int numberOfCharacter) {
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s -> {
            int number = randomNumber(0, s.length() - 1);
            result.add("" + s.charAt(number));
        };
        appendChar.accept(digits);
        appendChar.accept(specials);
        while (result.size() < numberOfCharacter) {
            appendChar.accept(ALL);
        }
        Collections.shuffle(result, generator);
        return String.join("", result);
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

}
