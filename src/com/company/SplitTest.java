package com.company;

import java.util.Scanner;

public class SplitTest {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter string to split: ");

        String x = reader.nextLine();
        String[] strings;

        if (x.indexOf(':') != -1) {
            strings = x.split(":", 2);
        } else {
            x = "all:" + x;
            strings = x.split(":", 2);
        }


        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }
}
