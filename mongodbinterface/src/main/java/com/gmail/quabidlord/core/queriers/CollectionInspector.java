package com.gmail.quabidlord.core.queriers;

import java.io.PrintStream;
import java.util.Iterator;

import com.mongodb.client.MongoClient;

public final class CollectionInspector {
    private final static PrintStream printer = new PrintStream(System.out);

    private CollectionInspector() {
        super();
    }

    public final static boolean collectionExists(MongoClient dbClient, String datastoreName, String collectionName) {
        Iterator<String> itr = dbClient.getDatabase(datastoreName).listCollectionNames().iterator();
        boolean exists = false;

        while (itr.hasNext()) {
            String collection = itr.next().toString().toLowerCase().trim();
            if (collection.equals(collectionName) || collection == collectionName) {
                exists = true;
                break;
            }
        }

        return exists;
    }

}
