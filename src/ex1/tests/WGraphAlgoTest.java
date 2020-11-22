package ex1.tests;
import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraphAlgoTest {

    @Test
    void shortestPath() {
        WGraph_DS g = createGraph1();
        WGraph_Algo g0 = new WGraph_Algo();
        g0.init(g);
        List<node_info> sp = g0.shortestPath(0, 5);

        int[] checkKey = {0, 2, 4, 5};
        int i = 0;
        for (node_info n : sp) {
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }

        g = addToGraph1(g);
        g0.init(g);
        List<node_info> sk = g0.shortestPath(0, 8);

        int[] checkKey1 = {0, 2, 3, 7, 8};
        i = 0;
        for (node_info n : sk) {
            assertEquals(n.getKey(), checkKey1[i]);
            i++;
        }
    }

    @Test
    void shortestDistance() {
        WGraph_DS g = createGraph1();
        WGraph_Algo g0 = new WGraph_Algo();
        g0.init(g);
        double a = g0.shortestPathDist(0, 5);
        assertEquals(4.5,a);


        g = addToGraph1(g);
        g0.init(g);
        double b = g0.shortestPathDist(0, 8);
        assertEquals(17,b);
    }


    @Test
    void connectivity(){
        WGraph_DS g = new WGraph_DS();
        WGraph_Algo g0 = new WGraph_Algo();

        g.addNode(0);
        g.addNode(1);
        g.connect(0,1,1);


        g0.init(g);
        assertTrue(g0.isConnected());

        g.removeEdge(0,1);
        g0.init(g);
        assertFalse(g0.isConnected());

        g = createGraph1();
        g0.init(g);
        assertTrue(g0.isConnected());

        g.removeNode(2);
        g.removeNode(2);
        g.removeNode(4);
        g0.init(g);
        assertFalse(g0.isConnected());

        g.addNode(9);
        g.connect(9,1,1);
        g.connect(9,5,1);
        g0.init(g);
        assertTrue(g0.isConnected());
    }

    @Test
    void saveload() {
        WGraph_DS g0 = new WGraph_DS();
        g0.addNode(0);
        g0.addNode(1);
        g0.addNode(2);
        g0.connect(0,1,1.5);
        g0.connect(0,2,2.5);

        WGraph_Algo ag0 = new WGraph_Algo();

        ag0.init(g0);
        ag0.save("g0.obj");

        WGraph_DS g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0,1,1);
        g1.connect(0,2,2);

        ag0.load("g0.obj");

        assertTrue(assertSL(g0,g1));
    }

    public boolean assertSL (WGraph_DS g1,  WGraph_DS g2){
        if(g1.getMC() != g2.getMC() || g1.nodeSize() != g2.nodeSize()) return false;
        for(node_info nodeOfG1 : g1.getV()) {
            node_info compareToNode = g2.getNode(nodeOfG1.getKey());
            if (compareToNode == null) {
                return false;
            }
            for (node_info neighborsOfG1 : g1.getV(nodeOfG1.getKey())) {
                 node_info compareToNode2 = g2.getNode(nodeOfG1.getKey());
                 if (compareToNode2 == null ){
                     return false;
                 }
            }
        }
        return true;
    }

    public WGraph_DS createGraph1(){
        WGraph_DS g = new WGraph_DS();
        for(int i = 0 ; i < 6 ; i++){
            g.addNode(i);
        }
        g.connect(0,1,0.5);
        g.connect(0,2,1);
        g.connect(2,3,8);
        g.connect(2,4,1.5);
        g.connect(3,5,9);
        g.connect(4,1,7);
        g.connect(4,5,2);

        return g;
    }

    public WGraph_DS addToGraph1(WGraph_DS g){
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.connect(5, 6, 20);
        g.connect(6, 8, 3);
        g.connect(7, 8, 3);
        g.connect(3, 7, 5);
        return g;
    }
}
