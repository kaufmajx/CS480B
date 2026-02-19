package testing;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import math.Vector;

class VectorTest {

    private static final double DELTA = 1e-9;

    // --- dot ---

    @Test
    void testDot_basicCase() {
        // [1,2] · [3,4] = 1*3 + 2*4 = 11
        assertEquals(11.0, Vector.dot(new double[]{1, 2}, new double[]{3, 4}), DELTA);
    }

    @Test
    void testDot_withZeroVector() {
        assertEquals(0.0, Vector.dot(new double[]{0, 0}, new double[]{3, 4}), DELTA);
    }

    @Test
    void testDot_orthogonalVectors_returnsZero() {
        // [1,0] · [0,1] = 0
        assertEquals(0.0, Vector.dot(new double[]{1, 0}, new double[]{0, 1}), DELTA);
    }

    @Test
    void testDot_parallelVectors() {
        // [2,0] · [2,0] = 4
        assertEquals(4.0, Vector.dot(new double[]{2, 0}, new double[]{2, 0}), DELTA);
    }

    @Test
    void testDot_negativeComponents() {
        // [-1,-2] · [3,4] = -3 + -8 = -11
        assertEquals(-11.0, Vector.dot(new double[]{-1, -2}, new double[]{3, 4}), DELTA);
    }

    // --- minus ---

    @Test
    void testMinus_basicCase() {
        double[] result = Vector.minus(new double[]{5, 3}, new double[]{2, 1});
        assertArrayEquals(new double[]{3.0, 2.0}, result, DELTA);
    }

    @Test
    void testMinus_resultIsZero() {
        double[] result = Vector.minus(new double[]{4, 4}, new double[]{4, 4});
        assertArrayEquals(new double[]{0.0, 0.0}, result, DELTA);
    }

    @Test
    void testMinus_negativeResult() {
        double[] result = Vector.minus(new double[]{1, 1}, new double[]{3, 3});
        assertArrayEquals(new double[]{-2.0, -2.0}, result, DELTA);
    }

    // --- norm ---

    @Test
    void testNorm_basicCase() {
        // norm([3,4]) = sqrt(9+16) = 5
        assertEquals(5.0, Vector.norm(new double[]{3, 4}), DELTA);
    }

    @Test
    void testNorm_zeroVector() {
        assertEquals(0.0, Vector.norm(new double[]{0, 0}), DELTA);
    }

    @Test
    void testNorm_unitVector() {
        assertEquals(1.0, Vector.norm(new double[]{1, 0}), DELTA);
    }

    @Test
    void testNorm_negativeComponents() {
        // norm([-3,-4]) = 5
        assertEquals(5.0, Vector.norm(new double[]{-3, -4}), DELTA);
    }

    // --- normalize ---

    @Test
    void testNormalize_basicCase() {
        // normalize([3,4]) = [0.6, 0.8]
        double[] result = Vector.normalize(new double[]{3, 4});
        assertArrayEquals(new double[]{0.6, 0.8}, result, DELTA);
    }

    @Test
    void testNormalize_resultHasNormOfOne() {
        double[] result = Vector.normalize(new double[]{5, 12});
        assertEquals(1.0, Vector.norm(result), DELTA);
    }

    @Test
    void testNormalize_alreadyUnitVector() {
        double[] result = Vector.normalize(new double[]{1, 0});
        assertArrayEquals(new double[]{1.0, 0.0}, result, DELTA);
    }

    // --- perp ---

    @Test
    void testPerp_basicCase() {
        // perp([1,0]) = [0,1]
        double[] result = Vector.perp(new double[]{1, 0});
        assertArrayEquals(new double[]{0.0, 1.0}, result, DELTA);
    }

    @Test
    void testPerp_isOrthogonalToOriginal() {
        double[] v = new double[]{3, 4};
        double[] p = Vector.perp(v);
        // dot(v, perp(v)) must be 0
        assertEquals(0.0, Vector.dot(v, p), DELTA);
    }

    @Test
    void testPerp_preservesMagnitude() {
        double[] v = new double[]{3, 4};
        assertEquals(Vector.norm(v), Vector.norm(Vector.perp(v)), DELTA);
    }

    @Test
    void testPerp_negativeComponents() {
        double[] result = Vector.perp(new double[]{0, -1});
        assertArrayEquals(new double[]{1.0, 0.0}, result, DELTA);
    }

    // --- plus ---

    @Test
    void testPlus_basicCase() {
        double[] result = Vector.plus(new double[]{1, 2}, new double[]{3, 4});
        assertArrayEquals(new double[]{4.0, 6.0}, result, DELTA);
    }

    @Test
    void testPlus_withZeroVector() {
        double[] v = new double[]{5, 7};
        assertArrayEquals(v, Vector.plus(v, new double[]{0, 0}), DELTA);
    }

    @Test
    void testPlus_negativeComponents() {
        double[] result = Vector.plus(new double[]{-1, -2}, new double[]{1, 2});
        assertArrayEquals(new double[]{0.0, 0.0}, result, DELTA);
    }

    // --- times(double, double[]) ---

    @Test
    void testTimes_scalar_basicCase() {
        double[] result = Vector.times(3.0, new double[]{2, 4});
        assertArrayEquals(new double[]{6.0, 12.0}, result, DELTA);
    }

    @Test
    void testTimes_scalar_zero() {
        double[] result = Vector.times(0.0, new double[]{5, 9});
        assertArrayEquals(new double[]{0.0, 0.0}, result, DELTA);
    }

    @Test
    void testTimes_scalar_negative() {
        double[] result = Vector.times(-2.0, new double[]{3, 1});
        assertArrayEquals(new double[]{-6.0, -2.0}, result, DELTA);
    }

    // --- times(double[], double) ---

    @Test
    void testTimes_vectorFirst_basicCase() {
        double[] result = Vector.times(new double[]{2, 4}, 3.0);
        assertArrayEquals(new double[]{6.0, 12.0}, result, DELTA);
    }

    @Test
    void testTimes_vectorFirst_matchesOtherOverload() {
        double[] v = new double[]{2, 5};
        double s = 4.0;
        assertArrayEquals(Vector.times(s, v), Vector.times(v, s), DELTA);
    }
}