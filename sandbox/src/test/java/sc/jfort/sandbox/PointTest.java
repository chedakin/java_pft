package sc.jfort.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import static sc.jfort.sandbox.MyFirstHomeWork.distance;

public class PointTest {

    @Test
    public void TestDistance() {
       Point p1 = new Point (-1, 9);
       Point p2 = new Point (2,5 );
       Assert.assertEquals(distance(p1,p2), 5);
    };

    @Test
    public void TestDistanceToT() {
        Point p1 = new Point (-1, 9);
        Point p2 = new Point (2,5 );
        Assert.assertEquals(p1.distanceToP(p2), 5);
    }

}