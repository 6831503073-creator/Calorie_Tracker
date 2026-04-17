package util;

import java.util.ArrayList;

public class Utils {

    // Generic method
    public static <T> void printList(ArrayList<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }
}