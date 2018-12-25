package com.company;

import java.util.*;

class Edge {
    int src, dest;

    Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
    }
}

class Graph {

    //List of lists to represent adjacency list
    private List<List<Integer>> adjacencyList = new ArrayList<>();

    Graph(List<Edge> edges) {

        //Allocate memory for adjacency list
        int max = 0;
        for (Edge e : edges) {
            if (e.src < e.dest) {
                if (e.dest > max)
                    max = e.dest;
            } else {
                if (e.src > max)
                    max = e.src;
            }
        }

        for (int i = 0; i < max + 1; i++) {
            adjacencyList.add(i, new ArrayList<>());
        }

        //add edges to the undirected graph
        for (Edge current : edges) {
            adjacencyList.get(current.src).add(current.dest);
            //check if this works
        }
    }

    static Graph addEdge(Graph graph) {
        Scanner sc = new Scanner(System.in);
        int src, dest;
        System.out.println("Enter the source of edge");
        src = sc.nextInt();
        System.out.println("Enter the destination of edge");
        dest = sc.nextInt();
        Edge edge = new Edge(src, dest);

        if (edge.src > edge.dest) {
            if (edge.src >= graph.adjacencyList.size()) {
                for (int i = graph.adjacencyList.size(); i < edge.src+1; i++)
                    graph.adjacencyList.add(i, new ArrayList<>());
            }
        } else {
            if (edge.dest >= graph.adjacencyList.size()) {
                for (int i = graph.adjacencyList.size(); i < edge.dest+1; i++)
                    graph.adjacencyList.add(i, new ArrayList<>());
            }
        }

        graph.adjacencyList.get(edge.src).add(edge.dest);

        boolean[] visited = new boolean[graph.adjacencyList.size()];
        boolean[] recStack = new boolean[graph.adjacencyList.size()];

        for (int i = 0; i < graph.adjacencyList.size(); i++)
            if (isCyclicUtil(i, visited, recStack, graph)) {
                System.out.println("Cannot add this edge as it leads to cycle.");
                graph.adjacencyList.get(edge.src).remove(new Integer(edge.dest));
                return graph;
            }

        return graph;
    }

    // returns true if given directed graph is DAG

    static boolean isCyclicUtil(int i, boolean[] visited,
                                boolean[] recStack, Graph graph) {

        // Mark the current node as visited and
        // part of recursion stack
        if (recStack[i])
            return true;


        if (visited[i]) {
            return false;
        }

        visited[i] = true;

        recStack[i] = true;
        List<Integer> children = graph.adjacencyList.get(i);

        for (Integer c : children)
            if (isCyclicUtil(c, visited, recStack, graph))
                return true;

        recStack[i] = false;

        return false;
    }

    static void printGraph(Graph graph) {
        int src = 0;
        int n = graph.adjacencyList.size();
        while (src < n) {
            for (int dest : graph.adjacencyList.get(src))
                System.out.print("(" + src + " --> " + dest + ")\t");
            System.out.println();
            src++;
        }
    }

    static Graph deleteNode(Graph graph) {
        Scanner sc = new Scanner(System.in);
        int node;
        System.out.println("Enter the node");
        node = sc.nextInt();
        int currentSrc = 0;
        int n = graph.adjacencyList.size();
        while (currentSrc < n) {
            if (currentSrc == node) {
                graph.adjacencyList.remove(node);
                graph.adjacencyList.add(node, new ArrayList<>());
                //This is to remove the node as src from all adjacency list
            } else if (graph.adjacencyList.get(currentSrc).contains(node))
                graph.adjacencyList.get(currentSrc).remove(new Integer(node));
            //This is to remove the node as dest from all adjacency list
            currentSrc++;
        }
        return graph;
    }

    static Graph deleteDependency(Graph graph) {
        Scanner sc = new Scanner(System.in);
        int src, dest;
        System.out.println("Enter the src");
        src = sc.nextInt();
        System.out.println("Enter the dest");
        dest = sc.nextInt();

        graph.adjacencyList.get(src).remove(new Integer(dest));

        return graph;
    }

    static void descendantOfNode(Graph graph, int node, HashMap<Integer, Boolean> map) {
        int src = 0;
        int n = graph.adjacencyList.size();
        while (src < n) {
            if (src == node) {
                for (int dest : graph.adjacencyList.get(src)) {
                    if (map.get(dest) == null) {
                        System.out.print(dest + " ");
                        map.put(dest, true);
                        descendantOfNode(graph, dest, map);
                    }
                }
            }
            src++;
        }
    }

    static void ancestorOfNode(Graph graph, int node, HashMap<Integer, Boolean> map) {
        int src = 0;
        int n = graph.adjacencyList.size();
        while (src < n) {
            for (int dest : graph.adjacencyList.get(src))
                if (dest == node) {
                    if (map.get(src) == null) {
                        System.out.print(src + " ");
                        map.put(src, true);
                        ancestorOfNode(graph, src, map);
                    }
                }
            src++;
        }
    }


    static void parentOfNode(Graph graph) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the node");
        int node = sc.nextInt();
        int src = 0;
        System.out.print("Parent of node " + node + " are ");
        int n = graph.adjacencyList.size();
        while (src < n) {
            for (int dest : graph.adjacencyList.get(src))
                if (dest == node)
                    System.out.print(src + " ");
            src++;
        }
    }


}

public class Main {

    public static void main(String[] args) {
        List<Edge> edges =
                Arrays.asList(new Edge(0, 1),
                        new Edge(2, 0),
                        new Edge(2, 1),
                        new Edge(3, 2));

        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph(edges);

        int response;
        while (true) {
            System.out.println();
            System.out.println("1. Add Edge.");
            System.out.println("2. Get Parent of a node.");
            System.out.println("3. Get Ancestor of a node");
            System.out.println("4. Get Descendants of a node");
            System.out.println("5. Delete Dependency from graph");
            System.out.println("6. Delete Node from graph");
            System.out.println("7. Display the graph");
            System.out.println("8. Exit");
            response = sc.nextInt();

            switch (response) {
                case 1:
                    Graph.addEdge(graph);
                    //TODO: Solve this static issue. Make the code proper modular.
                    break;
                case 2:
                    Graph.parentOfNode(graph);
                    break;
                case 3: {
                    System.out.println("Enter the node");
                    int node = sc.nextInt();
                    System.out.print("Ancestor of node " + node + " are ");
                    Graph.ancestorOfNode(graph, node, new HashMap<>());
                    break;
                }
                case 4:
                    System.out.println("Enter the node");
                    int node = sc.nextInt();
                    System.out.print("Descendants of node " + node + " are ");
                    Graph.descendantOfNode(graph, node, new HashMap<>());
                    break;
                case 5:
                    graph = Graph.deleteDependency(graph);
                    break;
                case 6:
                    graph = Graph.deleteNode(graph);
                    break;
                case 7:
                    Graph.printGraph(graph);
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input. Please Enter Again");
            }
        }
    }


}
