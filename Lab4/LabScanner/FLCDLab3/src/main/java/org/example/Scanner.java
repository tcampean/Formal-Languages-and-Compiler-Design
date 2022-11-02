package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private LanguageSpecification ls = new LanguageSpecification();
    private PIF pif = new PIF();
    private int capacity = 16;
    private SymTable symbolTable = new SymTable(capacity);
    private String programFile;
    private String PIFFile;
    private String STFile;

    public Scanner(String programFile, String PIFFile, String STFile) {
        this.programFile = programFile;
        this.PIFFile = PIFFile;
        this.STFile = STFile;
    }

    public void scan() {
        List<Pair<String, Integer>> tokenPairs = new ArrayList<>();
        try {
            File file = new File(programFile);
            java.util.Scanner reader = new java.util.Scanner(file);

            int lineNr = 1;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                List<String> tokens = tokenize(line);

                for (String token : tokens) {
                    tokenPairs.add(new Pair<>(token, lineNr));
                }

                ++lineNr;
            }

            reader.close();

            buildPIF(tokenPairs);
            writeResults();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> tokenize(String line) {
        ArrayList<String> tokens = new ArrayList<>();

        for (int i = 0; i < line.length(); ++i) {
            if (ls.isSeparator(String.valueOf(line.charAt(i))) && !(String.valueOf(line.charAt(i))).equals(" ")) {
                tokens.add(String.valueOf(line.charAt(i)));
            } else if (line.charAt(i) == '\"') {
                String constant = identifyStringConstant(line, i);
                tokens.add(constant);
                i += constant.length() - 1;
            } else if (line.charAt(i) == '\'') {
                String constant = identifyCharConstant(line, i);
                tokens.add(constant);
                i += constant.length() - 1;
            } else if (line.charAt(i) == '-') {
                String token = identifyMinusToken(line, i, tokens);
                tokens.add(token);
                i += token.length() - 1;
            } else if (line.charAt(i) == '+') {
                String token = identifyPlusToken(line, i, tokens);
                tokens.add(token);
                i += token.length() - 1;
            } else if (ls.isPartOfOperator(line.charAt(i))) {
                String operator = identifyOperator(line, i);
                tokens.add(operator);
                i += operator.length() - 1;
            } else if (line.charAt(i) != ' ') {
                String token = identifyToken(line, i);
                tokens.add(token);
                i += token.length() - 1;
            }
        }
        return tokens;
    }

    public String identifyStringConstant(String line, int position) {
        StringBuilder constant = new StringBuilder();

        for (int i = position; i < line.length(); ++i) {
            if ((ls.isSeparator(String.valueOf(line.charAt(i))) || ls.isOperator(String.valueOf(line.charAt(i)))) && ((i == line.length() - 2 && line.charAt(i + 1) != '\"') || (i == line.length() - 1)))
                break;
            constant.append(line.charAt(i));
            if (line.charAt(i) == '\"' && i != position)
                break;
        }

        return constant.toString();
    }

    public String identifyCharConstant(String line, int position) {
        StringBuilder constant = new StringBuilder();

        for (int i = position; i < line.length(); ++i) {
            if ((ls.isSeparator(String.valueOf(line.charAt(i))) || ls.isOperator(String.valueOf(line.charAt(i)))) && ((i == line.length() - 2 && line.charAt(i + 1) != '\'') || (i == line.length() - 1)))
                break;
            constant.append(line.charAt(i));
            if (line.charAt(i) == '\'' && i != position)
                break;
        }

        return constant.toString();
    }

    public String identifyMinusToken(String line, int position, ArrayList<String> tokens) {
        if (tokens.size()>0 && (ls.isIdentifier(tokens.get(tokens.size() - 1)) || ls.isConstant(tokens.get(tokens.size() - 1)))) {
            return "-";
        }

        StringBuilder token = new StringBuilder();
        token.append('-');

        for (int i = position + 1; i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.'); ++i) {
            token.append(line.charAt(i));
        }

        return token.toString();
    }

    public String identifyPlusToken(String line, int position, ArrayList<String> tokens) {
        if (tokens.size()>0 && (ls.isIdentifier(tokens.get(tokens.size() - 1)) || ls.isConstant(tokens.get(tokens.size() - 1)))) {
            return "+";
        }

        StringBuilder token = new StringBuilder();
        token.append('+');

        for (int i = position + 1; i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.'); ++i) {
            token.append(line.charAt(i));
        }

        return token.toString();
    }

    public String identifyOperator(String line, int position) {
        StringBuilder operator = new StringBuilder();
        operator.append(line.charAt(position));
        operator.append(line.charAt(position + 1));

        if (ls.isOperator(operator.toString()))
            return operator.toString();

        return String.valueOf(line.charAt(position));
    }

    public String identifyToken(String line, int position) {
        StringBuilder token = new StringBuilder();

        for (int i = position; i < line.length()
                && !ls.isSeparator(String.valueOf(line.charAt(i)))
                && !ls.isPartOfOperator(line.charAt(i))
                && line.charAt(i) != ' '; ++i) {
            token.append(line.charAt(i));
        }

        return token.toString();
    }

    public void buildPIF(List<Pair<String, Integer>> tokens) {
        List<String> invalidTokens = new ArrayList<>();
        boolean isLexicallyCorrect = true;
        for (Pair<String, Integer> tokenPair : tokens) {
            String token = tokenPair.getKey();

            if (ls.isOperator(token) || ls.isReservedWord(token) || ls.isSeparator(token)) {
                int code = ls.getCode(token);
                pif.add(code, new Pair<>(-1, -1));
            } else if (ls.isIdentifier(token)) {
                symbolTable.add(token);
                Pair<Integer, Integer> position = symbolTable.getPos(token);
                pif.add(0, position);
            } else if (ls.isConstant(token)) {
                symbolTable.add(token);
                Pair<Integer, Integer> position = symbolTable.getPos(token);
                pif.add(1, position);
            } else if (!invalidTokens.contains(token)) {
                invalidTokens.add(token);
                isLexicallyCorrect = false;
                System.out.println("Error at line " + tokenPair.getValue() + ": invalid token " + token);
            }
        }

        if (isLexicallyCorrect) {
            System.out.println("Program is lexically correct");
        }
    }

    public void writeResults() {
        try {
            File pifFile = new File(PIFFile);
            FileWriter pifFileWriter = new FileWriter(pifFile, false);
            BufferedWriter pifWriter = new BufferedWriter(pifFileWriter);
            pifWriter.write(pif.toString());
            pifWriter.close();

            File symbolTableFile = new File(STFile);
            FileWriter symbolTableFileWriter = new FileWriter(symbolTableFile, false);
            BufferedWriter symbolTableWriter = new BufferedWriter(symbolTableFileWriter);
            symbolTableWriter.write(symbolTable.toString());
            symbolTableWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}