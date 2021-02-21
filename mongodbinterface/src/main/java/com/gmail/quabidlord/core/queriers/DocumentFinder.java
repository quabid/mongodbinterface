package com.gmail.quabidlord.core.queriers;

import java.io.PrintStream;
import java.util.function.Consumer;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

public class DocumentFinder {
    private String collectionName = null;
    private final PrintStream printer = new PrintStream(System.out);

    public DocumentFinder() {
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

        printer.println("\n\tThe documents from the " + collectionName.toUpperCase() + " collection.\n");

        itr.forEach(printConsumer);

    }

    /**
     * Find all documents with given field.
     * 
     * @param database
     * @param field
     */
    public void find(final MongoDatabase database, final String field) {
        final MongoCollection<Document> collection = database.getCollection(collectionName);
        final FindIterable<Document> itr = collection.find(Filters.exists(field));

        final Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toJson() + "\n");
            }
        };

        if (itr.iterator().hasNext()) {
            printer.println("\n\tThe documents from the " + collectionName.toUpperCase() + " collection.\n");
            itr.forEach(printConsumer);
        } else {
            printer.println("\n\tNo record with field " + field.toUpperCase() + " found!\n\n");
        }

    }

    /**
     * Find all documents where given field equals given query.
     * 
     * @param database
     * @param field
     * @param query
     */
    public void findEquals(final MongoDatabase database, final String field, final String query) {
        final MongoCollection<Document> collection = database.getCollection(collectionName);
        final FindIterable<Document> itr = collection.find(Filters.eq(field, query));
        final Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toJson() + "\n");
            }
        };

        if (itr.iterator().hasNext()) {
            printer.println("\n\tThe documents from the " + collectionName.toUpperCase() + " collection.\n");
            itr.forEach(printConsumer);
        } else {
            printer.println("\n\tNo record with field " + field.toUpperCase() + " that equals " + query.toUpperCase()
                    + " found!\n\n");
        }
    }

    /**
     * Find all documents where given field(number) is between less and greater
     * values.
     * 
     * @param database
     * @param field
     * @param lessThanQuery
     * @param greaterThanQuery
     */
    public void findAndEquals(final MongoDatabase database, final String field, final String lessThanQuery,
            final String greaterThanQuery) {
        final MongoCollection<Document> collection = database.getCollection(collectionName);
        final FindIterable<Document> itr = collection
                .find(Filters.and(Filters.lt(field, Integer.parseInt(lessThanQuery)),
                        (Filters.gt(field, Integer.parseInt(greaterThanQuery)))));

        final Consumer<Document> printConsumer = new Consumer<Document>() {
            public void accept(final Document document) {
                System.out.println(document.toJson() + "\n");
            }
        };

        if (itr.iterator().hasNext()) {
            printer.println("\n\tThe documents from the " + collectionName.toUpperCase() + " collection.\n");
            itr.forEach(printConsumer);
        } else {
            printer.println("\n\tNo record with field " + field.toUpperCase() + " that is less than " + lessThanQuery.toUpperCase() + " and greater than " + greaterThanQuery + " found!\n\n");
        }
    }
}
