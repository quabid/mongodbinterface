package com.gmail.quabidlord.core.queriers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoClient;

import org.bson.Document;

public class DatabaseInspector {
    private final PrintStream printer = new PrintStream(System.out);
    private ArrayList<String> datastoreNames = null;

    public DatabaseInspector() {
        super();
    }

    public DatabaseInspector(ArrayList<String> dsn) {
        super();
        this.datastoreNames = dsn;
    }

    protected boolean datastoreExists(String name) {
        return datastoreNames.contains(name);
    }

    public void inspect(MongoClient dbClient, String datastoreName) {
        ListCollectionsIterable<Document> collection = dbClient.getDatabase(datastoreName).listCollections();

        Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toJson() + "\n");
            }
        };

        if (!this.datastoreExists(datastoreName)) {
            printer.println("\n\tDatabase " + datastoreName + " does not exist!");
        } else {
            if (collection.iterator().hasNext()) {
                printer.println("\n\tInpecting datastore " + datastoreName.toUpperCase() + "\n");
                collection.forEach(printConsumer);
            } else {
                printer.println("\nDatastore " + datastoreName.toUpperCase() + " is empty.\n");
            }
        }
    }

    public void inspect(MongoClient dbClient) {

        Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                printer.println(document.toJson() + "\n\n");
            }
        };

        if (datastoreNames.size() > 0) {
            Iterator<String> itrDatastoreNames = datastoreNames.iterator();
            int i = 0;
            while (itrDatastoreNames.hasNext()) {
                String databaseName = datastoreNames.get(i);

                ListCollectionsIterable<Document> collection = dbClient.getDatabase(itrDatastoreNames.next())
                        .listCollections();

                if (collection.iterator().hasNext()) {
                    printer.println("\n\t\t\t\tDatastore " + databaseName.toUpperCase() + " Collections\n");
                    collection.forEach(printConsumer);
                } else {
                    printer.println("\n\t\t\t\tDatastore " + databaseName.toUpperCase() + " is empty!\n");
                }
                i++;
            }
        }
    }
}
