import geometrie.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {

    private static final double EPS = 1e-9;

    @Test
    public void testDefaultConstructorIsBlack() {
        Color c = new Color();
        assertEquals(0, c.getRed(), EPS);
        assertEquals(0, c.getGreen(), EPS);
        assertEquals(0, c.getBlue(), EPS);
    }

    @Test
    public void testClampValues() {
        Color c = new Color(1.5, -0.2, 0.7);
        assertEquals(1.0, c.getRed(), EPS);
        assertEquals(0.0, c.getGreen(), EPS);
        assertEquals(0.7, c.getBlue(), EPS);
    }

    @Test
    public void testAddColors() {
        Color c1 = new Color(0.4, 0.5, 0.6);
        Color c2 = new Color(0.6, 0.6, 0.6);
        Color sum = c1.addVector(c2);
        assertEquals(new Color(1.0, 1.0, 1.0), sum);
    }

    @Test
    public void testMultiplyByScalar() {
        Color c = new Color(0.3, 0.4, 0.5);
        Color r = c.multiply(2.0);
        assertEquals(new Color(0.6, 0.8, 1.0), r);
    }

    @Test
    public void testSchurProduct() {
        Color c1 = new Color(0.5, 0.2, 0.8);
        Color c2 = new Color(0.4, 0.5, 0.5);
        Color res = c1.schurProduct(c2);
        assertEquals(new Color(0.2, 0.1, 0.4), res);
    }

    @Test
    public void testToRGBConversion() {
        Color c = new Color(1.0, 0.5, 0.0);
        int rgb = c.toRGB();
        int expected = (255 << 16) | (128 << 8) | 0;
        assertEquals(expected, rgb);
    }
}
