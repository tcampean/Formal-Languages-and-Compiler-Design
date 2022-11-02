package org.example;

import java.security.KeyPair;
import java.util.ArrayList;


public class SymTable {
    private ArrayList<ArrayList<String>> symbols;
    private Integer size;

    public SymTable(Integer size) {
        this.size = size;
        this.symbols = new ArrayList<>();
        for(int i = 0; i < size; i++)
            this.symbols.add(new ArrayList<>());
    }
    public ArrayList<ArrayList<String>> getSymbols() {
        return symbols;
    }

    public boolean add(String key) {
        int hashedKey = hashValue(key);
        if(!symbols.get(hashedKey).contains(key)) {
            symbols.get(hashedKey).add(key);
            return true;
        }
        return false;
    }
    private Integer hashValue(String value) {
        int sum = 0;
        for(int i=0;i< value.length();++i){
            sum += value.charAt(i);
        }
        return sum % size;
    }

    public Pair<Integer, Integer> getPos(String key) {

        int hashedKey = hashValue(key);

        if (symbols.get(hashedKey).contains(key)){
            int listIndex = 0;
            for(String value: symbols.get(hashedKey)) {
                if (!value.equals(key))
                    listIndex++;
                else
                    break;
            }

            return new Pair<>(hashedKey, listIndex);
        }
        return new Pair<>(-1, -1);
    }
}

