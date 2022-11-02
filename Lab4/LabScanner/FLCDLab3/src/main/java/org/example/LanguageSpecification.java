package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguageSpecification {

    private final List<String> reservedWords = Arrays.asList("work", "when", "Number", "Numbers", "finish", "display", "do", "cNumber", "String", "Strings", "cStrings",
            "while", "Char", "vChar", "prog", "exit", "otherwise", "read", "write");
    private final List<String> operators = Arrays.asList("+", "-", "|mul| ", "|div|", "|mod|", "|eq|", "|ieq|", "|!eq|", "|s|", "|b|", "|es|", "|eb|");
    private final List<String> separators = Arrays.asList("(", ")", ";", "{", "}","[", "]", " ", "\t", "\n", ",");

    private final HashMap<String, Integer> codification = new HashMap<>();

    public LanguageSpecification() {
        createCodification();
    }

    private void createCodification() {
        codification.put("identifier", 0);
        codification.put("constant", 1);

        int code = 2;

        for (String reservedWord : reservedWords) {
            codification.put(reservedWord, code);
            code++;
        }

        for (String operator : operators) {
            codification.put(operator, code);
            code++;
        }

        for (String separator : separators) {
            codification.put(separator, code);
            code++;
        }
    }

    public boolean isReservedWord(String token) {
        return reservedWords.contains(token);
    }

    public boolean isOperator(String token) {
        return operators.contains(token);
    }

    public boolean isPartOfOperator(char op) {
        return op == '!' || (isOperator(String.valueOf(op)));
    }

    public boolean isSeparator(String token) {
        return separators.contains(token);
    }

    public boolean isIdentifier(String token) {
        String stringPattern = "^[a-zA-Z0-9_]+";
        return token.matches(stringPattern);
    }

    public boolean isConstant(String token) {
        String charPattern = "^\'[a-zA-Z0-9_?!#*./%+=<>;)(}{ ]\'";
        String stringPattern = "^\"[a-zA-Z0-9_?!#*./%+=<>;)(}{ ]+\"";
        return token.matches(charPattern) || token.matches(stringPattern);
    }

    public Integer getCode(String token) {
        return codification.get(token);
    }
}