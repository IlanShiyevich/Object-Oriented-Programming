# readme for ex1 (second assignment).
 
The project and it's classes are designed for the use of creating a weighted graph, adding/removing nodes and edges. Also performing algorithms on the graph like BFS and finding the shortest path and it's length via Dijkstra's, etc.

The classes in this project are:
WGraph_DS:
This class represents a weighted undirected graph. This class can create a undirectional and weighted graphs and add or remove nodes or edges on a given graph. The Graph_DS class implements the weighted_graph interface.
WGraph_Algo:
This class represents a “regular” Graph Theory algorithm, including operations:
0. copy.
1. Init (graph);
2. IsConnected- which returns true if there is a valid path from every node to each other via BFS algorithm.
3. shortestPathDist(int src, int dest)- returns the shortest distance between 2 nodes via Dijkstra's algorithm.
4. shortestPath(int src, int dest) - return a collection of nodes which is the shortest path between src and dest via Dijkstra's algorithm.
5.save and load - Save a graph to a file and load a graph from a file.
WGraph_Algo class implements the weighted_graph_algorithms interface.

The data structure I chose for the graph(which contains all of the nodes of the graph) is hashMap.
Each entry of a Map contains exactly one unique key and its corresponding value.
Each node contains a key and data which is exactly the mapping we need to represent the nodes key >> data pattern.

Between some of the nodes in our graph(for ex. some node n) we have edges which are in turn the neigbors of node n. so all of n's neighbors have an edge to n.
To represent the neighbors we also use a hashMap.
