package com.gmail.quabidlord.core.consumers;

import java.util.ArrayList;

import org.junit.Test;

public class StringConsumerTests {
        StringConsumer strCon = new StringConsumer();
        ArrayList<String>list = new ArrayList<String>();
        String[] vals = {"ham","taken","token","steak","stake","sacrifice","obedience","society"};

    @Test
    public void testPrintArrayList() {
        for (String v:vals) {
            list.add(v);
        }

        strCon.printArrayList(list);
    }
}
