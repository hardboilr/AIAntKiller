package a3.test;

import a3.algorithm.model.Node;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.Test;
import static org.junit.Assert.*;
import a3.utility.Debug;
import static a3.utility.Debug.print;
import static a3.utility.Debug.println;

/**
 * @author Tobias Jacobsen
 */
public class OpenListTest {

    public OpenListTest() {
        Debug.isDebug = false;
    }
    
    @Test
    public void testNode() {
        PriorityQueue<Node> openList = new PriorityQueue(Comparator.naturalOrder());
        Node n0 = new Node(0, 1, 1);
        n0.setgVal(0);
        n0.sethVal(0);
        //fVal: 0

        Node n1 = new Node(0, 2, 2);
        n1.setgVal(3);
        n1.sethVal(10);
        //fVal: 13

        Node n2 = new Node(0, 3, 3);
        n2.setgVal(6);
        n2.sethVal(9);
        //fVal: 15

        Node n3 = new Node(0, 4, 4);
        n3.setgVal(9);
        n3.sethVal(6);
        //fVal: 15

        Node n4 = new Node(0, 5, 5);
        n4.setgVal(17);
        n4.sethVal(2);
        //fVal: 19;

        openList.add(n0);
        openList.add(n2);
        openList.add(n3);
        openList.add(n1);
        openList.add(n4);

        Node poll = openList.poll();
        assertEquals(1, poll.getDirection());

        poll = openList.poll();
        assertEquals(2, poll.getDirection());

        poll = openList.poll();
        assertEquals(4, poll.getDirection());

        Node new1 = new Node(0, 6, 6);
        new1.setgVal(10);
        new1.sethVal(6);
        //fVal: 16

        Node new2 = new Node(0, 7, 7);
        new2.setgVal(30);
        new2.sethVal(12);
        //fVal: 42
        
        openList.add(new1);
        openList.add(new2);
        
        poll = openList.peek();
        println(poll);
        assertEquals(3, poll.getDirection());

        if (openList.contains(new2)) {
            new2.setgVal(10);
            new2.sethVal(5);
            openList.remove(new2);
            openList.add(new2);
            //fVal: 15

            for (Node n : openList) {
                print(n);
                print("-");
                print(n.getfVal());
                print("\n");
            }
            print("\n");
        }

        poll = openList.poll();
        println(poll);
        assertEquals(7, poll.getDirection());

        poll = openList.poll();
        assertEquals(3, poll.getDirection());

        poll = openList.poll();
        assertEquals(6, poll.getDirection());
    }
}
