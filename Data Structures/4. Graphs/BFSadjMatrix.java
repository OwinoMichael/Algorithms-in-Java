import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Node {
    char data;

    Node(char data){
        this.data = data;
    }

}


class Graph {

    ArrayList<Node> nodes; // conf.
    int [][] matrix;

    Graph(int size){
        nodes = new ArrayList<>(); // initiate the conf.
        matrix = new int[size][size];
    }

    public void addNode(Node node){
        nodes.add(node);
    }

    public void addEdge(int src, int dst){
        matrix[src][dst] = 1;
    }

    public boolean checkEdge(int src, int dst){
        if(matrix[src][dst] == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public void print(){
        System.out.print("  ");
        for (Node node : nodes){
            System.out.print(node.data + " ");
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++){ // go through the rows
            System.out.print(nodes.get(i).data + " ");
            for (int j = 0; j < matrix[i].length; j++){ //matrix[i] gets the size of the row
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
    }

    public void breadthFirstSearch(int src){ //start from an index we to search
        Queue<Integer> queue = new LinkedList<>(); //create a Queue and use Linked list to interface it
        boolean[] visited = new boolean[matrix.length]; // create a boolean array

        queue.add(src); // add src to queue
        visited[src] = true; // change the visit status to true

        while(queue.size() != 0){ // while Queue size is not equal to zero / empty
            src = queue.remove(); // DEQUEUE Source
            System.out.println(nodes.get(src).data + " = visited");

            for(int i = 0; i < matrix[src].length; i++){ // loop the length of the row
                if(matrix[src][i] == 1 && !visited[i]){ //if not visited
                    queue.add(i); // Add to queue
                    visited[i] = true; // change visit status to true
                }
            }
        }
    }

}

public class BFSadjMatrix {

    public static void main(String[] args) {

        Graph graph = new Graph(5);
        graph.addNode(new Node('A'));
        graph.addNode(new Node('B'));
        graph.addNode(new Node('C'));
        graph.addNode(new Node('D'));
        graph.addNode(new Node('E'));

        graph.addEdge(0, 1); //Node at index 0 linked to node at index 1
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(4, 0);
        graph.addEdge(4, 2);

        graph.print();

        System.out.println(graph.checkEdge(0, 1));

        graph.breadthFirstSearch(0);


    }
}
