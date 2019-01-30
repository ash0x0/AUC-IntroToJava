package com.intro;

public class Main {
    /*
    IMPORTANT NOTE: In this problem I am not focusing on the number of edges connected to the node,
    I am focusing on the number of nodes connected to the node.
    The reason for this is these two things are the same, each edge connects two nodes to each other, in other words
    each edge connects one node to another. Hence if a node is connected to n edges it is therefore connected to n nodes.
    The terminology is just different but the problem is the same and the solution is the same.
     */
    public static void main(String[] args) {
        // The number of nodes
        final int nodeCount = 6;
        // The provided adjacency matrix. Closed for modification.
        final int[][] adjacencyMatrix =
                {       {0,1,0,0,0,0},
                        {1,0,1,0,1,0},
                        {0,1,0,1,1,0},
                        {0,0,1,0,1,0},
                        {0,1,1,1,0,1},
                        {0,0,0,0,1,0}
                };
        // This variable is used to count the number of nodes with more than two connected nodes
        int nodeCounter = 0;
        // Node states simply stores the number of nodes a particular node of index i is connected to
        int[] nodeStates = new int[nodeCount];

        // Loop over the adjacency matrix
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == 0) {
                    // This means that node i isn't connected to node j so move on
                    continue;
                }
                if (adjacencyMatrix[i][j] == 1 && adjacencyMatrix[j][i] == 1) {
                    /*
                     These two nodes are connected to each other, update node i with the information that
                     there is now one extra node connected to it
                     */
                    nodeStates[i] = nodeStates[i] + 1;
                }
            }
        }

        // Loop over the nodes looking at the number of connected nodes for each
        for (int i = 0; i < nodeStates.length; i++) {
            if (nodeStates[i] > 1) {
                // This means node i is connected to more than one node, increase the counter for the output
                nodeCounter++;
            }
        }
        // Print the number of nodes that have two or more nodes connected to them
        System.out.println(nodeCounter);
    }
}
