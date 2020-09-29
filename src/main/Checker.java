package main;

import java.util.Objects;

class Checker {
    static void assertNonNull(Object ...objects){
        for (Object object : objects){
            assert (Objects.nonNull(object)) : "Object is null";
        }
    }

    static void throwNull(Object ...objects){
        for (Object object : objects){
            assert (Objects.nonNull(object)) : "Object is null";
        }
    }
}
