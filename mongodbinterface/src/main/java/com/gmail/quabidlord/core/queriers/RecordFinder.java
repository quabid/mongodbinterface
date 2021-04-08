package com.gmail.quabidlord.core.queriers;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gmail.quabidlord.core.clients.DatastoreClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class RecordFinder {
    private String collectionName = null;
    private final PrintStream printer = new PrintStream(System.out);

    public RecordFinder() {
        super();
    }

    public void setCollectionName(final String cn) {
        this.collectionName = cn;
    }

    /**
     * Find all documents.
     * 
     * @param database
     */
    public void find(final MongoDatabase database) {
        final MongoCollection<Document> collection = database.getCollection(collectionName);
        final FindIterable<Document> itr = collection.find(new Document());

        final Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toJson() + "\n");
            }
        };

        printer.println("\nQuery: find all records in this collection.\n");

        itr.forEach(printConsumer);

    }

    public final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    public String toString() {
        return "Record Finder";
    }

    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        DatastoreClient client = new DatastoreClient();
        client.setConnectionString("mongodb+srv://rjwltd:HHPfaOkLUSxZ6udu@cluster0.mrx2r.mongodb.net/proshop");
        FindIterable<Document> collection = null;

        if (null != (collection = client.getCollectionIterable("proshop", "contacts"))) {
            Iterator<Document> itr = collection.iterator();
            while(itr.hasNext()) {
                System.out.println(itr.next().toJson());
            }
        }
    }
}
