import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.LinkedList;

// Adjacency list is an ARRAY LIST of LINKED LIST
        //Each linked list has a unique node at the end
        //All Adjacent neighbours to that node are added to the linked list

    //  Runtime complexity to check an Edge: O(v)
    //  Space complexity: O(v + e)

class Node{
    char data;

    Node(char data){
        this.data = data;
    }
}

class Graph{

    ArrayList<LinkedList<Node>> adjList;

    Graph(){
        adjList = new ArrayList<>();
    }

    public void addNode(Node node){
        LinkedList<Node> currentList = new LinkedList<>(); // Create a linked list
        currentList.add(node); //Every node we create we add to the Linked list
        adjList.add(currentList); //Add Linked List to the Array List
    }
    public void addEdge(int src, int dst){
        LinkedList<Node> currentList = adjList.get(src); // 1.Get a linked list from array list. 2.
        Node dstNode = adjList.get(dst).get(0); //Get destination index of Array list then get head of Linked List
        currentList.add(dstNode); // Add *dstNode to tail of *currentList
    }
    public boolean checkEdge(int src, int dst){
        LinkedList<Node> currentList = adjList.get(src);
        Node dstNode = adjList.get(dst).get(0);
        //Iterate over the *currentList and see if there is a match between

        for(Node node : currentList){
            if(node == dstNode){
                return true;
            }
        }
        return false;
    }
    public void print(){
        for(LinkedList<Node> currentList: adjList){ //Iterate over Linked List in the array list
            for (Node node : currentList){
                System.out.print(node.data + " -> ");
            }
            System.out.println();
        }
    }
}



public class GrapghAdjList {

    public static void main(String[] args) {

        Graph graph = new Graph();

        graph.addNode(new Node('A'));
        graph.addNode(new Node('B'));
        graph.addNode(new Node('C'));
        graph.addNode(new Node('D'));
        graph.addNode(new Node('E'));

        graph.addEdge(0, 2);    // A -> B, src & dst are indexes of the nodes created
        graph.addEdge(1, 2);   // B -> C
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);

        graph.print();
    }
}
