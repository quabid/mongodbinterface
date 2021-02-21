package com.gmail.quabidlord.core.consumers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class StringConsumer {
    private final PrintStream printer = new PrintStream(System.out);

    public StringConsumer() {
        super();
    }

    public void printArrayList(ArrayList<String> iterableObject) {

        Iterator<String> itr = iterableObject.iterator();
        while(itr.hasNext()) {
            printer.println(itr.next());
        }
    }

    public void printStringArray(Iterable<String[]> iterableObject) {
        Consumer<String[]> printConsumer = new Consumer<String[]>() {
            public void accept(final String[] item) {
                for (String i:item) {
                    printer.println(i);
                }
            }
        };
        iterableObject.forEach(printConsumer);
    }
}
