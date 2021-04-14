package com.gmail.quabidlord.core.collections;

import java.util.ArrayList;
import java.util.Iterator;

public final class DatabaseNames {
    public final static ArrayList<String> databaseNames = new ArrayList<String>();
    public static boolean hasDatastores = false;

    private DatabaseNames() {
        super();
    }

    public static void setDatabaseNames(Iterator<String> itr) {
        while(itr.hasNext()) {
            databaseNames.add(itr.next());
        }
        hasDatastores = databaseNames.size() > 0;
    }

}
