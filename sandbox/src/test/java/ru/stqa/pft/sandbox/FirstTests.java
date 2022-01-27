package ru.stqa.pft.sandbox;
import org.testng.Assert;
import org.testng.annotations.Test;
import static ru.stqa.pft.sandbox.MyFirstProgram.distance;


public class FirstTests {

    @Test
    public void testLength() {
        Point newPointOne = new Point(8.4, 8.7);
        Point newPointTwo = new Point(9.5, 9.7);
        double actual = distance(newPointOne, newPointTwo);
        Assert.assertEquals(actual, 2.048808848170151);
    }

    @Test
    public void testSecond() {
        Point newPointOne = new Point(8.4, 8.7);
        Point newPointTwo = new Point(9.5, 9.7);
        Assert.assertNotEquals(newPointOne,newPointTwo);
    }
}
