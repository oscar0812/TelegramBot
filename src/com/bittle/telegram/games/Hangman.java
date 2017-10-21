package com.bittle.telegram.games;

import java.util.ArrayList;

/**
 * Created by oscartorres on 7/2/17.
 */

// Takes care of hangman game logic
public class Hangman {

    private class MyChar {

        private char character = ' ';
        private boolean is_guessed = false;

        private MyChar(char ch) {
            character = ch;
        }
    }

    private int currentWrong = 0;
    private String answer;

    private final ArrayList<MyChar> charactersInAnswer = new ArrayList<>();
    private final ArrayList<Character> guessedChars = new ArrayList<>();

    public Hangman(String word) {
        answer = word;

        for (int x = 0; x < answer.length(); x++) {
            charactersInAnswer.add(new MyChar(answer.charAt(x)));
        }
    }

    public int getCurrentWrong() {
        return currentWrong;
    }

    public String getCurrentString() {
        StringBuilder all = new StringBuilder();
        for (MyChar current : charactersInAnswer) {
            if (current.is_guessed) {
                all.append(current.character);
                all.append(" ");
            } else
                all.append("_ ");
        }

        return all.toString().trim();
    }

    public String getCorrectAnswer() {
        return answer;
    }

    public boolean isAlreadyGuessed(char c) {
        return guessedChars.contains(c);
    }

    public void guess(char c) {
        boolean is_correct_guess = false;

        for (MyChar aCharactersInAnswer : charactersInAnswer) {
            if (aCharactersInAnswer.character == c) {
                aCharactersInAnswer.is_guessed = true;
                is_correct_guess = true;
            }
        }

        if (!guessedChars.contains(c))
            guessedChars.add(c);

        if (!is_correct_guess)
            currentWrong++;
    }

    public boolean isWin() {
        boolean flag = true;

        for (MyChar aCharactersInAnswer : charactersInAnswer) {
            if (!aCharactersInAnswer.is_guessed)
                flag = false;
        }

        return flag;
    }

    public boolean isGameOver() {
        return currentWrong >= 7;
    }

    public String getAllGuesses() {
        StringBuilder all = new StringBuilder();
        for (Character guessedChar : guessedChars) {
            all.append(guessedChar);
            all.append(" ");
        }
        return all.toString();
    }

    public String getDrawing() {
        String spaceLookAlike = "\u00a0";
        if (currentWrong == 0) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   | \n" +
                    "   | \n" +
                    "   | \n" +
                    "   | \n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 1) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   |    o\n" +
                    "   |\n" +
                    "   |\n" +
                    "   | \n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 2) {
            return spaceLookAlike + "  ____\n" +
                    "   |    |\n" +
                    "   |   o\n" +
                    "   |    |\n" +
                    "   |\n" +
                    "   |\n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 3) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   |    o\n" +
                    "   |     |\n" +
                    "   |     |\n" +
                    "   |\n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 4) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   |    o\n" +
                    "   |   /|\n" +
                    "   |     |\n" +
                    "   |\n" +
                    " _ |_\n" +
                    "|     |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 5) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   |    o\n" +
                    "   |   /|\\\n" +
                    "   |     |\n" +
                    "   |\n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 6) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   |    o\n" +
                    "   |   /|\\\n" +
                    "   |     |\n" +
                    "   |    /\n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else if (currentWrong == 7) {
            return spaceLookAlike + "  ____\n" +
                    "   |     |\n" +
                    "   |    o\n" +
                    "   |   /|\\\n" +
                    "   |     |\n" +
                    "   |    /\\\n" +
                    " _|_\n" +
                    "|    |________\n" +
                    "|                  |\n" +
                    "|__________|\n";
        } else {
            return "Game Over.";
        }
    }
}
