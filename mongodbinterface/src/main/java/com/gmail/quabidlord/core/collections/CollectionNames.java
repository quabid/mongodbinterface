package com.gmail.quabidlord.core.collections;

import java.util.ArrayList;
import java.util.Iterator;

public final class CollectionNames {
    public static final ArrayList<String> collectionNames = new ArrayList<String>();
    public static boolean hasTables = false;

    private CollectionNames() {
        super();
    }

    public static void setCollectionNames(Iterator<String> itr) {
        while(itr.hasNext()) {
            collectionNames.add(itr.next());
        }
        hasTables = collectionNames.size() > 0;
    }
}
