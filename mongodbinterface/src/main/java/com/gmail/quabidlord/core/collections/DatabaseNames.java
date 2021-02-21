package com.gmail.quabidlord.core.collections;

import java.util.ArrayList;
import java.util.Iterator;

public final class DatabaseNames {
    public final static ArrayList<String> datastores = new ArrayList<String>();
    public static boolean hasDatastores = false;

    private DatabaseNames() {
        super();
    }

    public static void setDatastores(Iterator<String> itr) {
        while(itr.hasNext()) {
            datastores.add(itr.next());
        }
        hasDatastores = datastores.size() > 0;
    }

    /* public static void setDatastores(Iterator<String> itr, ) {
        while(itr.hasNext()) {
            datastores.add(itr.next());
        }
        hasDatastores = datastores.size() > 0;
    } */

}
