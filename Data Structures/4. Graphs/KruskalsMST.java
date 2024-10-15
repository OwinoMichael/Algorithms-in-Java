import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Node class represents a vertex in the graph
class Node {
   char data;

   Node(char data) {
       this.data = data;
   }
}

// WeightedEdge class represents an edge with a destination node, source node, and a weight
class WeightedEdge implements Comparable<WeightedEdge> {
   Node src;
   Node dest;
   int weight;

   WeightedEdge(Node src, Node dest, int weight) {
       this.src = src;
       this.dest = dest;
       this.weight = weight;
   }

   // Implement compareTo method to sort by weight
   @Override
   public int compareTo(WeightedEdge other) {
       return this.weight - other.weight;
   }
}

// Union-Find (Disjoint Set Union) class
class UnionFind {
   private int[] parent;  // Parent array to track the root of each element
   private int[] rank;    // Rank array to keep the trees balanced

   // Constructor initializes 'parent' and 'rank' arrays
   UnionFind(int size) {
       parent = new int[size];
       rank = new int[size];

       // Initially, every element is its own parent (a separate set)
       for (int i = 0; i < size; i++) {
           parent[i] = i;
           rank[i] = 0;  // Initially, all trees are of rank 0
       }
   }

   // Find operation with path compression
   public int find(int node) {
       // If the node is not its own parent, it means it's part of a larger set
       if (parent[node] != node) {
           // Recursively find the root, and compress the path by pointing directly to the root
           parent[node] = find(parent[node]);
       }
       return parent[node];  // Return the root of the set
   }

   // Union operation with union by rank
   public void union(int node1, int node2) {
       // Find the roots of the two nodes
       int root1 = find(node1);
       int root2 = find(node2);

       // If they are already in the same set, no need to union
       if (root1 != root2) {
           // Union by rank: attach the smaller tree under the larger tree
           if (rank[root1] > rank[root2]) {
               parent[root2] = root1;  // Root2 becomes a child of Root1
           } else if (rank[root1] < rank[root2]) {
               parent[root1] = root2;  // Root1 becomes a child of Root2
           } else {
               // If the ranks are equal, arbitrarily choose one to be the parent and increase its rank
               parent[root2] = root1;
               rank[root1]++;
           }
       }
   }
}


// Graph class represents an adjacency list for a weighted graph
class Graph {
   ArrayList<LinkedList<WeightedEdge>> arrayList;
   ArrayList<Node> nodes;  // Keep track of nodes separately

   Graph() {
       arrayList = new ArrayList<>();
       nodes = new ArrayList<>();
   }

   // Add a node to the graph
   public void addNode(Node node) {
       LinkedList<WeightedEdge> currentList = new LinkedList<>();
       // Add a self-reference node (with zero weight) to represent the node in the list
       currentList.add(new WeightedEdge(node, node, 0));
       arrayList.add(currentList);
       nodes.add(node);  // Track node
   }

   // Add an edge with a weight between two nodes
   public void addEdge(int src, int dst, int weight) {
       LinkedList<WeightedEdge> currentList = arrayList.get(src);
       Node srcNode = arrayList.get(src).get(0).src; // Source node reference
       Node dstNode = arrayList.get(dst).get(0).src; // Destination node reference
       currentList.add(new WeightedEdge(srcNode, dstNode, weight));
   }

   // Collect all edges from the graph
   public List<WeightedEdge> getAllEdges() {
       List<WeightedEdge> edges = new ArrayList<>();
       for (LinkedList<WeightedEdge> currentList : arrayList) {
           for (WeightedEdge edge : currentList) {
               // Avoid self-referencing nodes (where src == dest)
               if (edge.src != edge.dest) {
                   edges.add(edge);
               }
           }
       }
       return edges;
   }

   // Kruskal's MST algorithm
   public void kruskalMST() {
       List<WeightedEdge> edges = getAllEdges();  // Get all edges
       Collections.sort(edges);  // Sort edges by weight

       UnionFind uf = new UnionFind(nodes.size());  // Union-Find to track connected components

       List<WeightedEdge> mst = new ArrayList<>();  // To store MST edges

       for (WeightedEdge edge : edges) {
           int srcIndex = nodes.indexOf(edge.src);  // Find the index of the source node
           int destIndex = nodes.indexOf(edge.dest);  // Find the index of the destination node

           // If src and dest are not connected, add the edge to the MST
           if (uf.find(srcIndex) != uf.find(destIndex)) {
               uf.union(srcIndex, destIndex);  // Union the two nodes
               mst.add(edge);  // Add edge to MST
           }
       }

       // Print the MST edges
       System.out.println("Kruskal's MST:");
       for (WeightedEdge edge : mst) {
           System.out.println(edge.src.data + " --(" + edge.weight + ")--> " + edge.dest.data);
       }
   }

   // Print the graph in an adjacency list format
   public void print() {
       for (LinkedList<WeightedEdge> currentList : arrayList) {
           for (WeightedEdge edge : currentList) {
               if (edge.weight == 0) {
                   // Skip self-reference nodes when printing
                   System.out.print(edge.src.data + " -> ");
               } else {
                   System.out.print(edge.dest.data + " (weight " + edge.weight + ") -> ");
               }
           }
           System.out.println();
       }
   }
}

public class KruskalsMST {

   public static void main(String[] args) {

       Graph graph = new Graph();

       graph.addNode(new Node('A'));
       graph.addNode(new Node('B'));
       graph.addNode(new Node('C'));
       graph.addNode(new Node('D'));
       graph.addNode(new Node('E'));

       // Adding edges with weights
       graph.addEdge(0, 2, 5);  // A -> C (weight 5)
       graph.addEdge(1, 2, 3);  // B -> C (weight 3)
       graph.addEdge(1, 4, 7);  // B -> E (weight 7)
       graph.addEdge(2, 3, 2);  // C -> D (weight 2)
       graph.addEdge(2, 4, 8);  // C -> E (weight 8)
       graph.addEdge(4, 0, 6);  // E -> A (weight 6)
       graph.addEdge(4, 1, 4);  // E -> B (weight 4)

       graph.print();

       System.out.println("Kruskal's MST:");
       graph.kruskalMST();
   }
}
