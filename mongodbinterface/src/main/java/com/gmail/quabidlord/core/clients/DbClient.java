package com.gmail.quabidlord.core.clients;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.gmail.quabidlord.core.collections.CollectionNames;
import com.gmail.quabidlord.core.queriers.CollectionInspector;
import com.gmail.quabidlord.core.queriers.DocumentFinder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DbClient {
    private MongoClient mongoClient = null;
    private String connectionString = null;
    private final DocumentFinder finder = new DocumentFinder();
    private boolean connected = false;
    private final PrintStream printer = new PrintStream(System.out);

    public DbClient() {
        super();
    }

    public DbClient(final String cs) {
        super();
        setConnectionString(cs);
    }

    public void setConnectionString(final String cs) {
        connectionString = cs;
        testConnection();
    }

    public boolean isConnected() {
        return connected;
    }

    public MongoClient getClient() {
        return mongoClient;
    }

    public ArrayList<String> getDatastoreNames() {
        return CollectionNames.names;
    }

    public void disconnect() throws NullPointerException {
        mongoClient.close();
        mongoClient = null;
    }

    public void printCollectionNames() {
        if (connected) {
            printer.println("\n\tDatastores in this cluster.");
            final Iterator<String> itr = CollectionNames.names.iterator();
            while (itr.hasNext()) {
                printer.println(itr.next());
            }
        }
    }

    public void inspectCollection(final String name) {
        printer.println("\n\tInspecting collection " + name);

    }

    public void findAllDocuments(final String databaseName, final String collectionName) {
        try {
            if (CollectionInspector.collectionExists(this.getClient(), databaseName, collectionName)) {
                printer.println("\n\tQuerying the " + collectionName.toUpperCase() + " collection in the "
                        + databaseName.toUpperCase() + " datastore.\n");
                finder.setCollectionName(collectionName);
                finder.find(mongoClient.getDatabase(databaseName));
            } else {
                printer.println(
                        "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
            }
        } catch (NoSuchElementException nse) {
            printer.println(
                    "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
        }
    }

    public void findAllDocuments(final String databaseName, final String collectionName, final String query) {
        try {
            if (CollectionInspector.collectionExists(this.getClient(), databaseName, collectionName)) {
                printer.println("\n\tQuerying the " + collectionName.toUpperCase() + " collection in the "
                        + databaseName.toUpperCase() + " datatore.\n");
                finder.setCollectionName(collectionName);
                finder.find(mongoClient.getDatabase(databaseName), query);
            } else {
                printer.println(
                        "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
            }
        } catch (NoSuchElementException nse) {
            printer.println(
                    "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
        }
    }

    public void findAllDocuments(final String databaseName, final String collectionName, final String field,
            final String query) {
        try {
            if (CollectionInspector.collectionExists(this.getClient(), databaseName, collectionName)) {
                printer.println("\n\tQuerying the " + collectionName.toUpperCase() + " collection in the "
                        + databaseName.toUpperCase() + " datastore.\n");
                finder.setCollectionName(collectionName);
                finder.findEquals(mongoClient.getDatabase(databaseName), field, query);
            } else {
                printer.println(
                        "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
            }
        } catch (NoSuchElementException nse) {
            printer.println(
                    "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
        }
    }

    public void findAllDocuments(final String databaseName, final String collectionName, final String field,
            final String ltq, final String gtq) {
        try {
            if (CollectionInspector.collectionExists(this.getClient(), databaseName, collectionName)) {
                printer.println("\n\tQuerying the " + collectionName.toUpperCase() + " collection in the "
                        + databaseName.toUpperCase() + " datastore.");
                finder.setCollectionName(collectionName);
                finder.findAndEquals(mongoClient.getDatabase(databaseName), field, ltq, gtq);
            } else {
                printer.println(
                        "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
            }
        } catch (NoSuchElementException nse) {
            printer.println(
                    "\n\tCollection " + collectionName + " does not exist in the " + databaseName + " datastore!");
        }
    }

    private void testConnection() {
        mongoClient = MongoClients.create(connectionString);
        if (null != mongoClient) {
            CollectionNames.setCollectionNames(mongoClient.listDatabaseNames().iterator());
            connected = true;
        } else {
            connected = false;
        }
    }

    public String toString() {
        return "DbConnector";
    }
}
