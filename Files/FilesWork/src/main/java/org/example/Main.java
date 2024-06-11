package org.example;

import org.example.dataclasses.DateLines;
import org.example.dataclasses.Depths;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataAdder adder = new DataAdder();
        adder.generateFirstJson("data/json1.json");
        System.out.println("First file was created");
        adder.generateSecondJson("data/json2.json");
        System.out.println("Second file was created");
    }
}