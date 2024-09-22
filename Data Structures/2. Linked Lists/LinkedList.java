package org.mike.com;


import java.util.NoSuchElementException;

public class LinkedList {

    private class Node {
    private int value;
    private Node next;

    public Node(int value){
        this.value = value;
    }
}

    private Node first;
    private Node last;

    //addFirst
    public void addFirst(int item){
        Node node = new Node(item);
        //check if the linked list is null
        if(first == null){
            first = node;
        }else{
            node.next = first;
            first = node;
        }
    }
    //addLast
    public void addLast(int item){
        var node = new Node(item); //New node must always have a value on creation
        //Check for the status of the list
        if(first == null){
            first = last = node;
        }else{
            last.next = node;
            last = node;
        }

    }
    //deleteFirst
    public void deleteFirst(){
        // [10 -> 20 -> 30]
        // We need 1st and 2nd variables to keep track

        if(first == null){
            throw new NoSuchElementException();
        }else{
            if (first == last){
                first = last = null;
                return;
            }
            var second = first.next;
            first.next = null;
            first = second;

        }
    }
    //deleteLast
    public void deleteLast() {
        // [10 -> 20 -> 30]
        //Find the previous node
        // last -> 20
        var previous = getPrevious(last);
        last = previous;
        last.next = null;

    }
    private Node getPrevious(Node node){
        var current = first;
        while (current != null){
            if (current.next == node){
                return current;
            }
        }
        return null;
    }


    //indexOf
    public int indexOf(int item){


        int index = 0;
        var current = first;
        while (current != null ){
            if (current.value == item){
                return index;
            }else{
                current = current.next;
                index++;
            }
        }
        return -1;
    }
        //contains
        public boolean contains(int item) {
            return indexOf(item) != -1;
        }

//    }

}
