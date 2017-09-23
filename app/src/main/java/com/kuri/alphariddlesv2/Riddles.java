package com.kuri.alphariddlesv2;

//RIDDLE BANK

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuri on 2016-11-07.
 */
public class Riddles {

    Map<Integer,String> riddles;
    Map<Integer,String> answers;
    Map<Integer,Boolean> visited;
    public Riddles(){
        riddles = new HashMap<Integer, String>();
        answers = new HashMap<Integer, String>();
        visited = new HashMap<Integer, Boolean>();

        riddles.put(0,"test0");
        riddles.put(1,"test1");
        riddles.put(2,"test2");
        riddles.put(3,"test3");
        answers.put(0,"B");
        answers.put(1,"B");
        answers.put(2,"B");
        answers.put(3,"B");
        visited.put(0,false);
        visited.put(1,false);
        visited.put(2,false);
        visited.put(3,false);
    }
}
