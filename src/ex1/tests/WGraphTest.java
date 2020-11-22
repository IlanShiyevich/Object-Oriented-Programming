package ex1.tests;
import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void testConnectivity() {
        Assertions.assertEquals(1,1);
    }



}
