package com.gmail.quabidlord.core;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.gmail.quabidlord.core.clients.DbClient;
import com.gmail.quabidlord.core.queriers.DatabaseInspector;
import com.gmail.quabidlord.core.utils.MyFileChooser;
import com.mongodb.client.MongoClient;

public class App {
    private final static PrintStream printer = new PrintStream(System.out);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                new MyFileChooser().createAndShowGUI();
            }
        });
    }

    public static void main_(String[] args) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        DatabaseInspector databaseInspector = null;
        DbClient dbc = null;
        try {
            switch (args.length) {
            case 0:
                throw new ArrayIndexOutOfBoundsException();

            case 2:
                printer.println("Connecting to DB");
                dbc = new DbClient(args[0]);
                printer.println("Connected? " + dbc.isConnected());
                if (dbc.isConnected()) {
                    MongoClient client = dbc.getClient();
                    databaseInspector = new DatabaseInspector(dbc.getDatastoreNames());
                    databaseInspector.inspect(client, args[1]);
                }
                break;
            case 3:
                printer.println("Connecting to DB");
                dbc = new DbClient(args[0]);
                printer.println("Connected? " + dbc.isConnected());
                if (dbc.isConnected()) {
                    dbc.printCollectionNames();
                    dbc.findAllDocuments(args[1], args[2]);
                }
                break;

            case 4:
                printer.println("Connecting to DB");
                dbc = new DbClient(args[0]);
                printer.println("Connected? " + dbc.isConnected());
                if (dbc.isConnected()) {
                    dbc.printCollectionNames();
                    dbc.findAllDocuments(args[1], args[2], args[3]);
                }
                break;

            case 5:
                printer.println("Connecting to DB");
                dbc = new DbClient(args[0]);
                printer.println("Connected? " + dbc.isConnected());
                if (dbc.isConnected()) {
                    dbc.printCollectionNames();
                    dbc.findAllDocuments(args[1], args[2], args[3], args[4]);
                }
                break;

            case 6:
                printer.println("Connecting to DB");
                dbc = new DbClient(args[0]);
                printer.println("Connected? " + dbc.isConnected());
                if (dbc.isConnected()) {
                    dbc.printCollectionNames();
                    dbc.findAllDocuments(args[1], args[2], args[3], args[4], args[5]);
                }
                break;

            default:
                printer.println("Connecting to DB");
                dbc = new DbClient(args[0]);
                printer.println("Connected? " + dbc.isConnected());
                if (dbc.isConnected()) {
                    dbc.printCollectionNames();
                    MongoClient client = dbc.getClient();
                    databaseInspector = new DatabaseInspector(dbc.getDatastoreNames());
                    databaseInspector.inspect(client);
                }
                break;
            }
        } catch (ArrayIndexOutOfBoundsException aiob) {
            printer.println("\t\tThis program is expecting at a minimum a datastore URL connection string. ");
        } catch (Exception e) {
            printer.println("\n");
            e.printStackTrace();
        } finally {
            try {
                dbc.disconnect();
            } catch (NullPointerException npe) {
                return;
            } finally {
                dbc = null;
                databaseInspector = null;
            }
        }

    }

    private final static void println(Object obj) {
        printer.println(String.valueOf(obj));
    }

    private final static void print(Object obj) {
        printer.print(String.valueOf(obj));
    }
}
