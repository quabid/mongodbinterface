package com.gmail.quabidlord.core.consumers;

import java.util.function.Consumer;

import com.mongodb.client.FindIterable;

import org.bson.Document;

public class DocumentConsumer {

    public DocumentConsumer() {
        super();
    }

    public void printToJson(FindIterable<Document> itr) {
        Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toJson() + "\n");
            }
        };
        itr.forEach(printConsumer);
    }

    public void printToBson(FindIterable<Document> itr) {
        Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toBsonDocument() + "\n");
            }
        };
        itr.forEach(printConsumer);
    }
}
