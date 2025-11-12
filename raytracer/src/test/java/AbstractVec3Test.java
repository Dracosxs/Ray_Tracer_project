
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour les classes :
 * AbstractVec3, Vector, Point et Color.
 */
public class AbstractVec3Test {

    private static final double EPS = 1e-9;

    // --- Tests sur Vector ---

    @Test
    void testAdditionVector() {
        Vector a = new Vector(1, 2, 3);
        Vector b = new Vector(4, 5, 6);
        Vector r = (Vector) a.add(b);
        assertEquals(new Vector(5, 7, 9), r);
    }

    @Test
    void testSoustractionVector() {
        Vector a = new Vector(5, 7, 9);
        Vector b = new Vector(1, 2, 3);
        Vector r = (Vector) a.sub(b);
        assertEquals(new Vector(4, 5, 6), r);
    }

    @Test
    void testMultiplicationScalaireVector() {
        Vector v = new Vector(1, -2, 3);
        Vector r = (Vector) v.mul(2);
        assertEquals(new Vector(2, -4, 6), r);
    }

    @Test
    void testProduitScalaireVector() {
        Vector a = new Vector(1, 2, 3);
        Vector b = new Vector(4, -5, 6);
        double dot = a.dot(b);
        assertEquals(12.0, dot, EPS); // 1*4 + 2*(-5) + 3*6 = 12
    }

    @Test
    void testProduitVectorielVector() {
        Vector a = new Vector(1, 0, 0);
        Vector b = new Vector(0, 1, 0);
        Vector cross = a.cross(b);
        assertEquals(new Vector(0, 0, 1), cross);
    }

    @Test
    void testProduitDeSchurVector() {
        Vector a = new Vector(1, 2, 3);
        Vector b = new Vector(2, 3, 4);
        Vector r = (Vector) a.schur(b);
        assertEquals(new Vector(2, 6, 12), r);
    }

    @Test
    void testLongueurEtNormalisation() {
        Vector v = new Vector(3, 4, 0);
        assertEquals(5.0, v.length(), EPS);
        Vector n = (Vector) v.normalize();
        assertEquals(1.0, n.length(), EPS);
        assertEquals(new Vector(0.6, 0.8, 0), n);
    }

    @Test
    void testNormalizeZeroVector() {
        Vector v = new Vector(0, 0, 0);
        Vector n = (Vector) v.normalize();
        assertEquals(Vector.zero(), n);
    }

    @Test
    void testNegateAndIsZero() {
        Vector v = new Vector(1, -2, 3);
        assertEquals(new Vector(-1, 2, -3), v.negate());
        assertTrue(Vector.zero().isZero());
    }

    // --- Tests sur Point ---

    @Test
    void testSubtractionPointReturnsVector() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 6, 8);
        Vector v = p2.sub(p1);
        assertEquals(new Vector(3, 4, 5), v);
    }

    @Test
    void testAddVectorToPoint() {
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(1, 1, 1);
        Point result = p.add(v);
        assertEquals(new Point(2, 3, 4), result);
    }

    @Test
    void testDistanceAndMidpoint() {
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(0, 3, 4);
        assertEquals(5.0, p1.distance(p2), EPS);
        Point mid = p1.midpoint(p2);
        assertEquals(new Point(0, 1.5, 2), mid);
    }

    @Test
    void testToVectorConversion() {
        Point p = new Point(1, 2, 3);
        Vector v = p.toVector();
        assertEquals(new Vector(1, 2, 3), v);
    }

    // --- Tests sur Color ---

    @Test
    void testConstructeurClamp() {
        Color c = new Color(1.5, -0.5, 0.5);
        assertEquals(new Color(1, 0, 0.5), c);
    }

    @Test
    void testAdditionColor() {
        Color c1 = new Color(0.2, 0.3, 0.4);
        Color c2 = new Color(0.4, 0.5, 0.6);
        Color sum = c1.add(c2);
        assertEquals(new Color(0.6, 0.8, 1.0), sum);
    }

    @Test
    void testMultiplicationScalarColor() {
        Color c = new Color(0.2, 0.4, 0.6);
        Color r = c.mul(2.0);
        assertEquals(new Color(0.4, 0.8, 1.0), r);
    }

    @Test
    void testSchurProductColor() {
        Color c1 = new Color(0.2, 0.5, 1.0);
        Color c2 = new Color(0.5, 0.5, 0.5);
        Color result = c1.mul(c2);
        assertEquals(new Color(0.1, 0.25, 0.5), result);
    }

    @Test
    void testToRGB() {
        Color c = new Color(1.0, 0.5, 0.0);
        int rgb = c.toRGB();
        int expected = (255 << 16) | (128 << 8) | 0;
        assertEquals(expected, rgb);
    }

    // --- Tests communs ---

    @Test
    void testEqualsAndToString() {
        Vector v1 = new Vector(1.000000001, 2.0, 3.0);
        Vector v2 = new Vector(1.000000002, 2.0, 3.0);
        assertEquals(v1, v2);
        assertTrue(v1.toString().contains("1.000"));
    }
}
