
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

    // Depth-First Search (DFS) method
    public void depthFirstSearch(int src) {
        boolean[] visited = new boolean[adjList.size()]; // Track visited nodes
        dfsRecursive(src, visited); // Call recursive helper method
        System.out.println(); // Print a new line after traversal
    }

    // Recursive DFS helper method
    private void dfsRecursive(int currentIndex, boolean[] visited) {
        visited[currentIndex] = true; // Mark current node as visited
        Node currentNode = adjList.get(currentIndex).get(0); // Get the current node
        System.out.print(currentNode.data + " "); // Print the current node

        // Iterate over neighbors of the current node
        LinkedList<Node> neighbors = adjList.get(currentIndex);
        for (int i = 1; i < neighbors.size(); i++) { // Start from index 1 because index 0 is the node itself
            Node neighborNode = neighbors.get(i);
            int neighborIndex = findNodeIndex(neighborNode);
            if (!visited[neighborIndex]) { // If the neighbor has not been visited
                dfsRecursive(neighborIndex, visited); // Recursively call DFS on the neighbor
            }
        }
    }

    // Find the index of the given node in the adjacency list
    private int findNodeIndex(Node node) {
        for (int i = 0; i < adjList.size(); i++) {
            if (adjList.get(i).get(0) == node) {
                return i; // Return the index of the node
            }
        }
        return -1; // Return -1 if the node is not found
    }
}


public class DFSadjList {

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

        graph.depthFirstSearch(0);
    }
}
