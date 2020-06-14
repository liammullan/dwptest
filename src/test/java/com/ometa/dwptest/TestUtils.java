package com.ometa.dwptest;

import java.util.Random;

public class TestUtils {

    static double anyDouble(){
        return new Random().nextDouble();
    }

    static String anyString() {
        return String.valueOf(new Random().nextInt());
    }
}
