package org.example;

public class Main {
    public static void main(String[] args) {
        SymTable symbolTable = new SymTable(16);
        symbolTable.add("a");
        symbolTable.add("b");
        System.out.println(symbolTable.getSymbols());

        if(!symbolTable.add("a")) {
            System.out.println("ELEMENT NOT ADDED");
            System.out.println(symbolTable.getSymbols());
        }

        symbolTable.add("ab");
        symbolTable.add("dddd");
        symbolTable.add("ba");
        System.out.println(symbolTable.getSymbols());

        System.out.println(symbolTable.getPos("ab"));
        System.out.println(symbolTable.getPos("a"));
        System.out.println(symbolTable.getPos("ba"));
    }
}