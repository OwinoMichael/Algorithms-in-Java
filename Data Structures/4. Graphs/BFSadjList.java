import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    // Breadth-First Search (BFS) method
    public void breadthFirstSearch(int src) {
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS
        boolean[] visited = new boolean[adjList.size()]; // Array to track visited nodes, initialize to Array list size

        visited[src] = true; // Mark the start node as visited
        queue.add(src); // Add the start node to the queue

        while (!queue.isEmpty()) {
            int currentIndex = queue.poll(); // Get and remove the front of the queue i.e. src
            Node currentNode = adjList.get(currentIndex).get(0); // Get the current node i.e. node of src
            System.out.print(currentNode.data + " "); // Print the current node

            // Traverse all the neighbors of the current node
            LinkedList<Node> neighbors = adjList.get(currentIndex); // want to get to neighbours of src
            for (int i = 1; i < neighbors.size(); i++) { // starting at 1 since 0 is src
                Node neighborNode = neighbors.get(i);
                int neighborIndex = findNodeIndex(neighborNode);
                if (!visited[neighborIndex]) { // If neighbor has not been visited
                    visited[neighborIndex] = true;
                    queue.add(neighborIndex); // Add it to the queue
                }
            }
        }
        System.out.println(); // Print a new line after traversal
    }

    // Helper method to find the index of a node in the adjacency list
    private int findNodeIndex(Node node) {
        for (int i = 0; i < adjList.size(); i++) {
            if (adjList.get(i).get(0) == node) {
                return i;
            }
        }
        return -1; // If the node is not found (though this case won't occur here)
    }


}


public class BFSadjList {

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

        graph.breadthFirstSearch(0);
    }
}
