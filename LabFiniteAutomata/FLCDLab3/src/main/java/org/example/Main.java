package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        Scanner scanner = new Scanner("C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\p1error.txt",
//                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\pif2.txt",
//                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\st2.txt");
//
//        scanner.scan();

//        Scanner scanner = new Scanner("C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\p1.txt",
//                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\pif1.txt",
//                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\st1.txt");
//
//        scanner.scan();

        System.out.println("1. Test DFA");
        System.out.println("2. Scanner");
        System.out.println("Your option: ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> optionsForDFA();
            case 2 -> runScanner();
        }
    }

    private static void printMenu() {
        System.out.println("1. Print states.");
        System.out.println("2. Print alphabet.");
        System.out.println("3. Print final states.");
        System.out.println("4. Print transitions.");
        System.out.println("5. Check DFA acceptance");
        System.out.println("0. Exit");
    }

    private static void optionsForDFA() {
        FiniteAutomaton fa = new FiniteAutomaton("C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\FA.in");

        System.out.println("FA read from file.");
        printMenu();
        System.out.println("Your option: ");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int option = scanner.nextInt();

        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(fa.writeStates());
                case 2 -> System.out.println(fa.writeAlphabet());
                case 3 -> System.out.println(fa.writeFinalStates());
                case 4 -> System.out.println(fa.writeTransitions());
                case 5 -> {
                    if (fa.checkIfDFA()) {
                        System.out.println("Your sequence: ");
                        java.util.Scanner scanner2 = new Scanner(System.in);
                        String sequence = scanner2.nextLine();

                        if (fa.checkSequence(sequence))
                            System.out.println("Sequence is valid");
                        else
                            System.out.println("Invalid sequence");
                    } else {
                        System.out.println("FA is not deterministic.");
                    }
                }
            }
            System.out.println();
            printMenu();
            System.out.println("Your option: ");
            option = scanner.nextInt();
        }
    }

    private static void runScanner() {
        org.example.Scanner scanner = new org.example.Scanner("C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\p1.txt",
                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\pif1.txt",
                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\st1.txt");

        scanner.scan();

        //        Scanner scanner = new Scanner("C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\p1error.txt",
//                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\pif2.txt",
//                "C:\\Users\\PC\\IdeaProjects\\FLCDLab3\\src\\main\\java\\org\\example\\st2.txt");
//
//        scanner.scan();
    }
}