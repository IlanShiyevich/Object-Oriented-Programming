package ex1.src;

import java.util.*;

public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node> nodes;

    private int mc;
    private int numberOfEdgesInGraph;
    private int UniqueKey = 0;


    /**
     * WGraph_DS constructor.
     */
    public WGraph_DS() {
        nodes = new HashMap<>();
        mc = 0;
        numberOfEdgesInGraph = 0;
    }

    private class node implements node_info{
        private int key;
        private String Info;
        private double Tag;
        protected HashMap<Integer,node_info> neighbors;
        protected HashMap<Integer,Double> weight;


        public node(int key){
            this.key = key;
            this.Info = "";
            this.Tag = 0;
            neighbors = new HashMap<>();
            weight = new HashMap<>();
        }


        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return Info;
        }

        @Override
        public void setInfo(String s) {
            this.Info = s;
        }

        @Override
        public double getTag() {
            return Tag;
        }

        @Override
        public void setTag(double t) {
            this.Tag = t;
        }
    }

    @Override
    public node_info getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return nodes.get(node1).neighbors.containsKey(node2) && nodes.get(node2).neighbors.containsKey(node1);
    }

    @Override
    public double getEdge(int node1, int node2) {
        double a = 0;
        if(nodes.get(node1) != null && nodes.get(node2) != null && hasEdge(node1,node2) ) {
            a = nodes.get(node1).weight.get(node2);
            return a;
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!nodes.containsKey(key)) {
            node newNOde = new node(key);
            nodes.put(key, newNOde);
            mc++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(nodes.get(node1) != null && nodes.get(node2) != null && node1 != node2 ) {
            if (!nodes.get(node1).neighbors.containsKey(node2) ) {
                //adding neighbors and weight to both of the nodes.
                nodes.get(node1).neighbors.put(node2, nodes.get(node2));
                nodes.get(node1).weight.put(node2, w);

                nodes.get(node2).neighbors.put(node1, nodes.get(node1));
                nodes.get(node2).weight.put(node1, w);

                //updating the info of both nodes.
                String news = nodes.get(node1).getInfo() + node2 + ">" + w + " ";
                nodes.get(node1).setInfo(news);

                news = nodes.get(node2).getInfo() + node1 + ">" + w + " ";
                nodes.get(node2).setInfo(news);

                numberOfEdgesInGraph++;
                mc++;
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        Collection<node_info> c = new LinkedList<>();
        c.addAll(nodes.values());
        return c;
    }


    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> neighbors = new LinkedList<>();
        for(int next : nodes.get(node_id).neighbors.keySet()){
            neighbors.add(nodes.get(next));
        }
        return neighbors;
    }

    @Override
    public node_info removeNode(int key) {
        if(nodes.get(key) == null){
            return null;
        }
        mc++;
        node_info removed;
        //REMOVING EDGES
        Collection<node_info> node_neibs= nodes.get(key).neighbors.values();
        int size = node_neibs.size();
        numberOfEdgesInGraph =  numberOfEdgesInGraph-size;
        Collection<node_info> forLoop = new ArrayList<>();
        forLoop.addAll(node_neibs);

       // if(node_neibs.isEmpty()) {
         //   return nodes.remove(key);
       // }

        for (node_info neighbor_of_key: forLoop) {
            nodes.get(key).neighbors.remove(neighbor_of_key.getKey());
            nodes.get(key).weight.remove(neighbor_of_key.getKey());
            nodes.get(neighbor_of_key.getKey()).neighbors.remove(key);
            nodes.get(neighbor_of_key.getKey()).weight.remove(key);
            mc++;
        }
        //REMOVING NODE
        return nodes.remove(key);
    }


    @Override
    public void removeEdge(int node1, int node2) {
        if(nodes.get(node1) == null || nodes.get(node2) == null || node1 == node2) return;
        nodes.get(node1).neighbors.remove(node2);
        nodes.get(node1).weight.remove(node2);
        nodes.get(node2).neighbors.remove(node1);
        nodes.get(node2).weight.remove(node1);
        mc++;
        numberOfEdgesInGraph--;

    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return numberOfEdgesInGraph;
    }

    @Override
    public int getMC() {
        return mc;
    }
}