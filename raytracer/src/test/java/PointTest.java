import geometrie.Point;
import geometrie.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    private static final double EPS = 1e-9;

    @Test
    public void testSubtractionReturnsVector() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 6, 8);
        Vector v = p2.sub(p1);
        assertEquals(new Vector(3, 4, 5), v);
    }

    @Test
    public void testAdditionWithVectorReturnsPoint() {
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(2, -1, 0);
        Point result = p.add(v);
        assertEquals(new Point(3, 1, 3), result);
    }

    @Test
    public void testDistanceBetweenPoints() {
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(3, 4, 0);
        assertEquals(5.0, p1.distance(p2), EPS);
    }

    @Test
    public void testMidpoint() {
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(2, 2, 2);
        Point mid = p1.midpoint(p2);
        assertEquals(new Point(1, 1, 1), mid);
    }

    @Test
    public void testToVectorConversion() {
        Point p = new Point(1, 2, 3);
        Vector v = p.toVector();
        assertEquals(new Vector(1, 2, 3), v);
    }
}
