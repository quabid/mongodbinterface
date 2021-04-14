package com.gmail.quabidlord.core.clients;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.gmail.quabidlord.core.collections.CollectionNames;
import com.gmail.quabidlord.core.collections.DatabaseNames;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DbClient {
    private MongoClient mongoClient = null;
    private String connectionString = null;
    private boolean connected = false;
    private final PrintStream printer = new PrintStream(System.out);
    private final Map<String, ArrayList<String>> datastoreCollectionMap = new HashMap<String, ArrayList<String>>();

    public DbClient() {
        super();
    }

    public DbClient(final String cs) {
        super();
        setConnectionString(cs);
    }

    public boolean isConnected() {
        return connected;
    }

    // Setters

    /**
     * Sets the datastore connection string
     * 
     * @param dbConnectionString
     */
    public void setConnectionString(final String dbConnectionString) {
        connectionString = dbConnectionString;
        testConnection();
    }

    public final void setDatabaseCollectinMap() {
        ArrayList<String> databases = this.getDatabaseNames();
        for (String db : databases) {
            Iterator<String> itr = mongoClient.getDatabase(db).listCollectionNames().iterator();
            ArrayList<String> collections = new ArrayList<String>();
            while (itr.hasNext()) {
                collections.add(itr.next().toString().toLowerCase().trim());
            }
            datastoreCollectionMap.put(db, collections);
        }
    }

    // Getters

    /**
     * Returns the MongoClient object
     * @return MongoClient
     */
    public MongoClient getClient() {
        return mongoClient;
    }

    /**
     * Returns list of all database names
     * @return ArrayList<String>
     */
    public ArrayList<String> getDatabaseNames() {
        return DatabaseNames.databaseNames;
    }

    /**
     * Returns mapping of all databases and their collections
     * @return Map<String, ArrayList<String>>
     */
    public Map<String, ArrayList<String>> getDatastoreCollectionMap() {
        return datastoreCollectionMap;
    }

    // Utils

    public final void printDatabaseCollectionMap() {
        Iterator<?> itr = datastoreCollectionMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<?, ?> me = (Map.Entry<?, ?>) itr.next();
            printer.println(me.getKey().toString().toUpperCase() + "'s Collections");
            ArrayList<String> coll = (ArrayList<String>) me.getValue();

            for (String c : coll) {
                printer.println("\t" + c);
            }

            printer.println("\n");
        }
    }

    public final void printDatabaseCollections(String databaseName) {
        if (!datastoreCollectionMap.containsKey(databaseName)) {
            printer.println("\n\tDatastore " + databaseName.toUpperCase() + " does not exist!\n");
        } else {
            printer.println(databaseName.toUpperCase() + "'s Collections");
            ArrayList<String> coll = datastoreCollectionMap.get(databaseName);
            CollectionNames.setCollectionNames(coll.iterator());
            for (String c : coll) {
                printer.println(c);
            }
        }
    }

    public void disconnect() throws NullPointerException {
        mongoClient.close();
        mongoClient = null;
    }

    private void testConnection() {
        mongoClient = MongoClients.create(connectionString);
        if (null != mongoClient) {
            DatabaseNames.setDatabaseNames(mongoClient.listDatabaseNames().iterator());
            setDatabaseCollectinMap();
            connected = true;
        } else {
            connected = false;
        }
    }

    public String toString() {
        return "DB Client";
    }
}
