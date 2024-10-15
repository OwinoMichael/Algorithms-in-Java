import java.util.ArrayList;
import java.util.PriorityQueue;
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

public class PrimsMST {

   // Function to calculate MST using Prim's algorithm
   public static int primMST(int n, ArrayList<ArrayList<Edge>> graph) {
       boolean[] inMST = new boolean[n]; // Track nodes in MST
       PriorityQueue<Edge> pq = new PriorityQueue<>(); // Min-heap for edges
       int[] key = new int[n];  // Key values to pick minimum weight edge
       int totalWeight = 0;

       // Start from the first vertex
       pq.add(new Edge(-1, 0, 0)); // Add vertex 0 with weight 0

       while (!pq.isEmpty()) {
           Edge edge = pq.poll();
           int u = edge.dest;

           if (inMST[u]) continue; // Skip if already in MST
           inMST[u] = true; // Mark as included in MST
           totalWeight += edge.weight; // Add the edge's weight to the total MST weight

           // Add adjacent edges to the priority queue
           for (Edge nextEdge : graph.get(u)) {
               if (!inMST[nextEdge.dest]) {
                   pq.add(nextEdge);
               }
           }
       }

       return totalWeight;
   }

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       // Read number of vertices
       int n = scanner.nextInt();
       ArrayList<ArrayList<Edge>> graph = new ArrayList<>();

       for (int i = 0; i < n; i++) {
           graph.add(new ArrayList<>());
       }

       // Read the adjacency matrix
       for (int i = 0; i < n; i++) {
           for (int j = 0; j < n; j++) {
               int weight = scanner.nextInt();
               if (weight != -1 && i < j) {
                   // Add both directions (i to j and j to i)
                   graph.get(i).add(new Edge(i, j, weight));
                   graph.get(j).add(new Edge(j, i, weight));
               }
           }
       }

       // Calculate the initial MST weight using Prim's Algorithm
       int mstWeight = primMST(n, graph);
       System.out.println("Initial MST weight: " + mstWeight);

       // Process weight changes
       while (true) {
           int u = scanner.nextInt() - 1;  // Convert to 0-indexed
           int v = scanner.nextInt() - 1;  // Convert to 0-indexed
           int newWeight = scanner.nextInt();
           if (u == -1 && v == -1 && newWeight == -1) break;

           // Update the weight of the edge between u and v
           boolean edgeUpdated = false;
           for (Edge edge : graph.get(u)) {
               if (edge.dest == v) {
                   edge.weight = newWeight; // Update the weight
                   edgeUpdated = true;
                   break;
               }
           }
           for (Edge edge : graph.get(v)) {
               if (edge.dest == u) {
                   edge.weight = newWeight; // Update the reverse direction weight
                   edgeUpdated = true;
                   break;
               }
           }

           if (!edgeUpdated) {
               // If edge didn't exist before, add it in both directions
               graph.get(u).add(new Edge(u, v, newWeight));
               graph.get(v).add(new Edge(v, u, newWeight));
           }

           // Recalculate the MST weight after the update
           int newMSTWeight = primMST(n, graph);
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
