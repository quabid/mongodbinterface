package com.gmail.quabidlord.core.clients;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.gmail.quabidlord.core.queriers.CollectionInspector;
import com.gmail.quabidlord.core.queriers.DocumentFinder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

public class DatastoreClient {
    private MongoClient mongoClient = null;
    private String connectionString = null;
    private boolean connected = false;
    private final DocumentFinder finder = new DocumentFinder();
    private final PrintStream printer = new PrintStream(System.out);
    private final Map<String, ArrayList<String>> datastoreCollectionMap = new HashMap<String, ArrayList<String>>();
    private final ArrayList<String> datastoreNames = new ArrayList<String>();

    // Constructors

    public DatastoreClient() {
        super();
    }

    public DatastoreClient(final String cs) {
        super();
        setConnectionString(cs);
    }

    // Setters

    /**
     * Set the connection string, then test the connection
     * 
     * @param cs
     */
    public void setConnectionString(final String cs) {
        connectionString = cs;
        testConnection();
    }

    /** Collection the datastores with their collections */
    public final void setDatastoreCollectionMap() {
        datastoreCollectionMap.clear();
        ArrayList<String> databases = this.getDatastoreNames();
        for (String db : databases) {
            Iterator<String> itr = mongoClient.getDatabase(db).listCollectionNames().iterator();
            ArrayList<String> collections = new ArrayList<String>();
            while (itr.hasNext()) {
                collections.add(itr.next().toString().toLowerCase().trim());
            }
            datastoreCollectionMap.put(db, collections);
        }
    }

    /** Collect the datastore names */
    public final void setDatastoreNames() {
        datastoreNames.clear();
        Iterator<String> itr = mongoClient.listDatabaseNames().iterator();
        while (itr.hasNext()) {
            datastoreNames.add(itr.next());
        }
    }

    /**
     * Confirms whether or not connection was successful
     * 
     * @return boolean
     */
    public boolean isConnected() {
        return connected;
    }

    // Getters

    /**
     * Returns a MongoCollection<Document> object
     * @param databaseName
     * @param collectionName
     * @return
     */
    public MongoCollection<Document> getCollection(String databaseName, String collectionName) {
        if (!datastoreNames.contains(databaseName)) {
            println("Datastore " + databaseName + " does not exist!\n");
            return null;
        } else {
            if (CollectionInspector.collectionExists(mongoClient, databaseName, collectionName)) {
                return mongoClient.getDatabase(databaseName).getCollection(collectionName);
            } else {
                println("Collections " + collectionName + " does not exist!\n");
                return null;
            }
        }
    }

    /**
     * Returns a FindIterable<Document> object
     * @param databaseName
     * @param collectionName
     * @return
     */
    public FindIterable<Document> getCollectionIterable(String databaseName, String collectionName) {
        if (!datastoreNames.contains(databaseName)) {
            println("Datastore " + databaseName + " does not exist!\n");
            return null;
        } else {
            if (CollectionInspector.collectionExists(mongoClient, databaseName, collectionName)) {
                return mongoClient.getDatabase(databaseName).getCollection(collectionName).find(new Document());
            } else {
                println("Collections " + collectionName + " does not exist!\n");
                return null;
            }
        }
    }

    /**
     * Returns the MongoClient object
     * 
     * @return Object
     */
    public MongoClient getClient() {
        return mongoClient;
    }

    /**
     * Returns a list of all database names in the cluster
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getDatastoreNames() {
        return datastoreNames;
    }

    /**
     * Returns a Map of the datastore names mapped to a list of it's collections
     * 
     * @return Map<String,ArrayList<String>>
     */
    public Map<String, ArrayList<String>> getDatastoreCollectionMap() {
        return datastoreCollectionMap;
    }

    // Members

    /**
     * Disconnect from the datastore, then nullifies the MongoClient object
     */
    public void disconnect() {
        if (null != mongoClient) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    /** Prints all datastore names */
    public final void printDatastoreNamesAndTheirCollectionsNames() {
        Map<String, ArrayList<String>> dcm = this.getDatastoreCollectionMap();
        Set set = dcm.entrySet();
        Iterator itr = set.iterator();

        println("\n");
        while (itr.hasNext()) {
            Map.Entry<String, ArrayList<String>> me = (Map.Entry<String, ArrayList<String>>) itr.next();
            String datastore = me.getKey();
            ArrayList<String> collections = me.getValue();

            println("\t" + datastore.toUpperCase() + " Collections");
            for (String c : collections) {
                println(c);
            }
            println("\n");

        }
    }

    /**
     * Test the MongoClient object connection, then collect the datastore names and
     * map datastores to their collections
     */
    private void testConnection() {
        mongoClient = MongoClients.create(connectionString);
        if (null != mongoClient) {
            setDatastoreNames();
            setDatastoreCollectionMap();
            connected = true;
        } else {
            connected = false;
        }
    }

    /**
     * Prints the string value of given object
     * 
     * @param obj
     */
    private final void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    public String toString() {
        return "Datastore Client";
    }
}
