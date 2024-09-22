package org.mike.com;

import java.sql.SQLOutput;
import java.util.Arrays;
//import java.util.LinkedList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        LinkedList list = new LinkedList();
//        list.add(10);
//        list.add(20);
//        list.add(30);
//        list.addFirst(5);
//        list.removeLast();
//        list.contains(10);
//        System.out.println(list.contains(10));
//        System.out.println(list.indexOf(10));
//        System.out.println(list.size());
//        System.out.println(list);
//        var array = list.toArray();
//        System.out.println(Arrays.toString(array));

        var list = new LinkedList();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.deleteLast();
//        list.deleteFirst();
//        System.out.println(list.indexOf(10));
//        System.out.println(list.indexOf(20));
//        System.out.println(list.indexOf(40));
//        System.out.println(list.contains(10));
//        list.addFirst(30);
//        list.addFirst(40);
//        list.deleteFirst();
    }
}