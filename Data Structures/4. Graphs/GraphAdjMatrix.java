import javax.management.modelmbean.ModelMBean;
import java.security.PublicKey;

class Node {
    char data;

    Node(char data){
        this.data = data;
    }

}


class Graph {
    int [][] matrix;

    Graph(int size){
        matrix = new int[size][size];
    }

    public void addNode(Node node){

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
        for (int i = 0; i < matrix.length; i++){ // go through the rows
            for (int j = 0; j < matrix[i].length; j++){ //matrix[i] gets the size of the row
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
    }

}


public class GraphAdjMatrix {

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
    }

}
