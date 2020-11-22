package ex1.src;

import com.sun.jdi.IntegerValue;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms{
    weighted_graph g = new WGraph_DS();
    private boolean isInitialized = false;

    HashMap<Integer , Integer> prevNodes;
    HashMap<Integer , Double> totalCosts;

    HashMap<Integer, HashMap<Integer, Double>> Weight;

    /**
     * this class is a (comparable) object to help us in implementing the priority queue in Dijkstra's algorithm.
     */
    private class tuple implements Comparable<tuple>{
        private int key;
        private Double distance;

        tuple (int key){
            this.key = key;
            this.distance = Double.MAX_VALUE;
        }

        tuple (int key , double distance){
            this.key = key;
            this.distance = distance;
        }

        @Override
        public int compareTo(tuple o) {
            return this.distance.compareTo(o.distance);
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double weight) {
            this.distance = weight;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

    }

    @Override
    public void init(weighted_graph g) {
        if(g == null){
            return;
        }
        this.g = g;
        isInitialized = true;
        neighborMapping();
    }

    /**
     * returns underlying graph.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        if (isInitialized) return g;
        return null;
    }

    /**
     * this method deep copies a graph to a new one.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copy = new WGraph_DS();
        HashMap<Integer, node_info> nodesOfc = new HashMap<>();

        //add nodes to new graph.
        for(node_info next : g.getV()){
            nodesOfc.put(next.getKey(), next);
        }
        for(node_info next : nodesOfc.values()){
            copy.addNode(next.getKey());
        }

        //connects all the nodes in copy.
        for(node_info next : copy.getV()){
            for(int keyOfNeighbor : Weight.get(next.getKey()).keySet()){
                copy.connect(next.getKey(), keyOfNeighbor, Weight.get(next.getKey()).get(keyOfNeighbor));
            }
        }
        return copy;
    }
    /**
     * this method checks if the number of nodes that we visited is the same quantity of nodes that the graph has.
     * if so the graph must be fully connected.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (isInitialized) {
            if (g.nodeSize() == 1 || g.nodeSize() == 0) return true;
            return BFS(getMeKey(g)) == g.nodeSize();
        }
        return false;
    }

    /**
     * this method is Dijkstra's algorithm from a given source node.
     *
     * @param src
     */
    private void Dijkstra (int src){
        totalCosts = new HashMap<>();
        prevNodes = new HashMap<>();
        PriorityQueue<tuple> minPQ = new PriorityQueue<>();
        HashSet<Integer> visited = new HashSet<>();

        totalCosts.put(src, 0.0);
        tuple tuple = new tuple(src , 0);
        minPQ.add(tuple);

        //assigning starting values to all nodes.
        for(node_info node : g.getV()){
            if(node.getKey() != src){
                totalCosts.put(node.getKey(),Double.MAX_VALUE);
            }
        }
        while(!minPQ.isEmpty()){
            tuple shortestDist = minPQ.poll();

            for(node_info neighbor : g.getV(shortestDist.getKey())){
                if(!visited.contains(neighbor.getKey())){
                    double path = totalCosts.get(shortestDist.getKey()) + Weight.get(shortestDist.getKey()).get(neighbor.getKey());

                    if(path < totalCosts.get(neighbor.getKey())){
                        totalCosts.put(neighbor.getKey() , path);
                        prevNodes.put(neighbor.getKey(), shortestDist.getKey());
                        tuple tuple1 = new tuple(neighbor.getKey() , path);
                        minPQ.add(tuple1);
                    }
                }
            }
            visited.add(shortestDist.getKey());
        }
    }

    /**
     * this method executes Dijkstra method and then  return the dest node distance(totalCosts).
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(isInitialized) {
            Dijkstra(src);
        }
        return totalCosts.get(dest);
    }

    /**
     * this method executes Dijkstra method, then return the linkedList of the previous nodes of dest.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> query = new LinkedList<>();
        if(isInitialized){
            Dijkstra(src);
            while ( dest != src){
                query.addFirst(g.getNode(dest));
                dest = prevNodes.get(dest);
            }
            query.addFirst(g.getNode(dest));
        }
        return query;
    }


    /**
     * this method creates a neighbor > weight mapping to all vertices.
     */
    private void neighborMapping(){
        Weight = new HashMap<>();
        for(node_info next : g.getV()){
            String intel = next.getInfo();
            String []firstBreakdown = intel.split(" ");
            HashMap <Integer,Double> temp = new HashMap<>();
            for(int i = 0; i < firstBreakdown.length; i++){
                String []secondBreakdown = firstBreakdown[i].split(">");
                if(secondBreakdown.length == 2) {

                    temp.put(Integer.parseInt(secondBreakdown[0]), Double.parseDouble(secondBreakdown[1]));

                }
            }
            Weight.put(next.getKey(), temp);
        }
    }

    /**
     * this method saves the underlying graph into a file.
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        if(isInitialized) {
            try {
                PrintWriter pw = new PrintWriter(new File(file));
                StringBuilder sb = new StringBuilder();

                for(node_info next : g.getV()) {
                    sb.setLength(0);
                    sb.append(next.getKey());
                    sb.append(">");
                    sb.append(Weight.get(next.getKey()));
                    sb.append("\n");
                    pw.write(sb.toString());
                }
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * this method loads a graph from a file.
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        String line = "";
        weighted_graph loaded = new WGraph_DS();
        try{
            BufferedReader br = new BufferedReader((new FileReader(file)));

            while((line = br.readLine()) != null){
                line = line.replace("{" , "");
                line = line.replace("}" , "");
                String[] breakdown1 = line.split(">");
//                System.out.println(breakdown1[1]);
                String[] breakdown2 = breakdown1[1].split(", ");
//                System.out.println(breakdown2[0]);

                for(int i = 0;i<breakdown2.length;i++){
                    String[] breakdown3 = breakdown2[i].split("=");
                    int temp = Integer.parseInt(breakdown1[0]);
                    loaded.addNode(temp);
                    int temp2 = Integer.parseInt(breakdown3[0]);
                    loaded.addNode(temp2);
                    loaded.connect(temp, temp2 ,Double.parseDouble(breakdown3[1]));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        this.g = loaded;
        return true;
    }

    /**
     * this method performs the Breadth First Search algorithm.
     * @param src
     * @return
     */
    private int BFS(int src) {
        zeroTag();
        int NumberOfnodesVisited = 0;

        Queue<node_info> q = new LinkedList<node_info>();

        q.add(g.getNode(src));
        g.getNode(src).setTag(1);
        NumberOfnodesVisited++;

        while (!q.isEmpty()) {
            node_info node = q.remove();
            Collection<node_info> neighbors = g.getV(node.getKey());

            for (node_info next : neighbors) {
                if (next.getTag() != 1) {
                    q.add(next);
                    next.setTag(1);
                    NumberOfnodesVisited++;
                }
            }
        }
        return NumberOfnodesVisited;
    }

    /**
     * this method resets the the tags of all the nodes. O(V).
     */
    private void zeroTag() {
        for (node_info node : g.getV()) {
            node.setTag(0);
        }
    }

    /**
     * this method return the first key it gets from g. O(1).
     * @param g
     * @return
     */
    private int getMeKey(weighted_graph g) {
        int firstKey = 0;
        for(node_info node : g.getV()){
            firstKey = node.getKey();
            break;
        }
        return firstKey;
    }
}


