import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Edge class to represent an edge with source, destination, and weight
class Edge implements Comparable<Edge> {
   int src, dest, weight;

   Edge(int src, int dest, int weight) {
       this.src = src;
       this.dest = dest;
       this.weight = weight;
   }

   // Implement compareTo method to sort edges by weight
   @Override
   public int compareTo(Edge other) {
       return this.weight - other.weight;
   }
}

// Union-Find (Disjoint Set Union) class
class UnionFind {
   private int[] parent, rank;

   // Constructor initializes parent and rank arrays
   UnionFind(int size) {
       parent = new int[size];
       rank = new int[size];
       for (int i = 0; i < size; i++) {
           parent[i] = i;  // Initially, each node is its own parent
           rank[i] = 0;    // Initially, all trees are of rank 0
       }
   }

   // Find operation with path compression
   public int find(int node) {
       if (parent[node] != node) {
           parent[node] = find(parent[node]);  // Path compression
       }
       return parent[node];
   }

   // Union operation with union by rank
   public void union(int node1, int node2) {
       int root1 = find(node1);
       int root2 = find(node2);
       if (root1 != root2) {
           // Union by rank
           if (rank[root1] > rank[root2]) {
               parent[root2] = root1;  // Attach root2 under root1
           } else if (rank[root1] < rank[root2]) {
               parent[root1] = root2;  // Attach root1 under root2
           } else {
               parent[root2] = root1;   // Arbitrarily choose root1 as parent
               rank[root1]++;            // Increase rank
           }
       }
   }
}

// Main class for Kruskal's MST
public class KruskalsMST1 {

   // Function to calculate MST using Kruskal's algorithm
   public static int kruskalMST(int n, ArrayList<Edge> edges) {
       UnionFind uf = new UnionFind(n);
       int mstWeight = 0;

       // Sort edges by weight
       Collections.sort(edges);

       // Process edges
       for (Edge edge : edges) {
           if (uf.find(edge.src) != uf.find(edge.dest)) {
               uf.union(edge.src, edge.dest);  // Union the two nodes
               mstWeight += edge.weight;        // Add weight to MST
           }
       }
       return mstWeight;
   }

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       // Read number of vertices
       int n = scanner.nextInt();
       ArrayList<Edge> edges = new ArrayList<>();

       // Read the adjacency matrix
       for (int i = 0; i < n; i++) {
           for (int j = 0; j < n; j++) {
               int weight = scanner.nextInt();
               if (weight != -1 && i < j) {  // Only add edges in one direction
                   edges.add(new Edge(i, j, weight));
               }
           }
       }

       // Calculate the initial MST weight
       int mstWeight = kruskalMST(n, edges);
       System.out.println("Initial MST weight: " + mstWeight);

       // Process weight changes
       while (true) {
           int u = scanner.nextInt() - 1;  // Convert to 0-indexed
           int v = scanner.nextInt() - 1;  // Convert to 0-indexed
           int newWeight = scanner.nextInt();
           if (u == -1 && v == -1 && newWeight == -1) break;

           // Check if the edge already exists
           boolean edgeExists = false;
           for (Edge edge : edges) {
               if ((edge.src == u && edge.dest == v) || (edge.src == v && edge.dest == u)) {
                   edge.weight = newWeight; // Update the weight
                   edgeExists = true;
                   break;
               }
           }

           // If the edge did not exist, add the new edge
           if (!edgeExists) {
               edges.add(new Edge(u, v, newWeight));
           }

           // Recalculate the MST weight after the update
           int newMSTWeight = kruskalMST(n, edges);
           if (newMSTWeight != mstWeight) {
               System.out.println("MST weight changes to " + newMSTWeight);
               mstWeight = newMSTWeight; // Update the mstWeight
           } else {
               System.out.println("MST weight does not change");
           }
       }

       scanner.close();
   }
}
