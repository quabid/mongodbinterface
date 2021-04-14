package com.gmail.quabidlord.core;

import java.io.PrintStream;

public class App {
    private final static PrintStream printer = new PrintStream(System.out);

    public static void main(String[] args) {
        println("ah");
        print("hah!");

    }

    private final static void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    private final static void print(Object obj) {
        printer.print(String.valueOf(obj));
    }
}
