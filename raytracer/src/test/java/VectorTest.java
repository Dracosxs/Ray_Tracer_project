import geometrie.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {

    private static final double EPSILON = 1e-9;

    @Test
    public void testAddition() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, -2, 0);
        Vector res = (Vector) v1.addVector(v2);
        assertEquals(5, res.getX(), EPSILON);
        assertEquals(0, res.getY(), EPSILON);
        assertEquals(3, res.getZ(), EPSILON);
    }

    @Test
    public void testSubtraction() {
        Vector v1 = new Vector(3, 5, 7);
        Vector v2 = new Vector(1, 2, 3);
        Vector res = (Vector) v1.subtract(v2);
        assertEquals(2, res.getX(), EPSILON);
        assertEquals(3, res.getY(), EPSILON);
        assertEquals(4, res.getZ(), EPSILON);
    }

    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        assertEquals(32, v1.dotProduct(v2), EPSILON);
    }

    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector cross = v1.crossProduct(v2);
        assertEquals(-3, cross.getX(), EPSILON);
        assertEquals(6, cross.getY(), EPSILON);
        assertEquals(-3, cross.getZ(), EPSILON);
    }

    @Test
    public void testNormalize() {
        Vector v = new Vector(3, 0, 4);
        Vector norm = (Vector) v.normalize();
        assertEquals(1.0, norm.length(), EPSILON);
    }

    @Test
    public void testIsZero() {
        Vector v = new Vector(1e-10, 0, 0);
        assertTrue(v.isZero());
    }
}