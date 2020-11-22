package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

import org.junit.jupiter.api.Test;

import java.util.Collection;


import static org.junit.jupiter.api.Assertions.*;

public class WGraphTest {

    private WGraph_DS createGraph1(){
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


    @Test
    void nodeSize() {
        WGraph_DS g = createGraph1();
        assertEquals(6 , g.nodeSize());

        g.removeNode(0);
        assertEquals(5,g.nodeSize());
    }

    @Test
    void edgeSize(){
        WGraph_DS g = createGraph1();
        assertEquals(7,g.edgeSize());

        g.removeNode(2);
        assertEquals(4,g.edgeSize());
    }

    @Test
    void getV() {
        WGraph_DS g = createGraph1();

        Collection<node_info> v = g.getV();
        for(node_info next : v){
            assertNotNull(next);
        }
    }

    @Test
    void hasEdge() {
        WGraph_DS g = createGraph1();
        boolean b = g.hasEdge(0,1);
        assertTrue(b);
        assertTrue(g.hasEdge(0,2));
        assertTrue(g.hasEdge(3,5));
        assertFalse(g.hasEdge(3,4));
    }

    @Test
    void connect() {
        weighted_graph g = createGraph1();
        assertFalse(g.hasEdge(3,4));
        g.removeEdge(2,0);
        g.connect(0,4,1);
        double w = g.getEdge(0,4);
        assertEquals(w,1);
    }


    @Test
    void removeNode() {
        weighted_graph g = createGraph1();
        g.removeNode(2);
        g.removeNode(0);
        assertFalse(g.hasEdge(1,0));
        int e = g.edgeSize();
        assertEquals(3,e);
        assertEquals(4,g.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph g = createGraph1();
        g.removeEdge(2,3);
        double w = g.getEdge(0,3);
        assertEquals(w,-1);
    }
}
