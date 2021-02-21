package com.gmail.quabidlord.core.collections;

import java.util.ArrayList;
import java.util.Iterator;

public final class CollectionNames {
    public static final ArrayList<String> names = new ArrayList<String>();
    public static boolean hasTables = false;

    private CollectionNames() {
        super();
    }

    public static void setCollectionNames(Iterator<String> itr) {
        while(itr.hasNext()) {
            names.add(itr.next());
        }
        hasTables = names.size() > 0;
    }
}
