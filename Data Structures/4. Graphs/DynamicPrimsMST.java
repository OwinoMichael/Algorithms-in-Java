import java.util.*;

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

public class DynamicPrimsMST {
    private static final int INF = Integer.MAX_VALUE;

    // Prim's algorithm to calculate the MST weight
    public static int primMST(int[][] graph, int n) {
        boolean[] inMST = new boolean[n];  // Track nodes in MST
        int[] key = new int[n];  // Track minimum edge weight for each node
        int[] parent = new int[n];  // Track parent of each node
        PriorityQueue<Edge> pq = new PriorityQueue<>();  // Min heap to pick minimum weight edge

        // Initialize keys as infinite and MST as false for all vertices
        Arrays.fill(key, INF);
        key[0] = 0;  // Start from vertex 0
        parent[0] = -1;

        pq.add(new Edge(-1, 0, 0));  // Start with vertex 0 (key = 0)

        int mstWeight = 0;

        // Process all vertices
        while (!pq.isEmpty()) {
            // Extract the minimum weight edge
            Edge edge = pq.poll();
            int u = edge.dest;

            if (inMST[u]) {
                continue;  // If it's already in the MST, skip
            }

            // Add the vertex to the MST
            inMST[u] = true;
            mstWeight += edge.weight;

            // For each adjacent vertex, check and update the key
            for (int v = 0; v < n; v++) {
                int weight = graph[u][v];

                if (weight != -1 && !inMST[v] && key[v] > weight) {
                    key[v] = weight;
                    pq.add(new Edge(u, v, weight));
                    parent[v] = u;
                }
            }
        }
        return mstWeight;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of vertices
        int n = scanner.nextInt();
        int[][] graph = new int[n][n];

        // Read the adjacency matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        // Calculate the initial MST weight
        int mstWeight = primMST(graph, n);
        System.out.println("Initial MST weight: " + mstWeight);

        // Process weight changes
        while (true) {
            int u = scanner.nextInt() - 1;  // Convert to 0-indexed
            int v = scanner.nextInt() - 1;  // Convert to 0-indexed
            int newWeight = scanner.nextInt();

            if (u == -1 && v == -1 && newWeight == -1) {
                break;  // End of input
            }

            // Update the graph with the new weight
            graph[u][v] = newWeight;
            graph[v][u] = newWeight;  // Since the graph is undirected

            // Recalculate the MST weight after the update using Dynamic Prim's approach
            int newMSTWeight = primMST(graph, n);
            if (newMSTWeight != mstWeight) {
                System.out.println("MST weight changes to " + newMSTWeight);
                mstWeight = newMSTWeight;  // Update the mstWeight
            } else {
                System.out.println("MST weight does not change");
            }
        }

        scanner.close();
    }
}
