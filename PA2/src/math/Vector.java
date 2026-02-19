package math;

public class Vector
{

  private Vector()
  {
  }

  /**
   * Dot product of 2 vectors. p⋅q = p1q1+p2q2
   * 
   * @param v
   * @param w
   * @return dot product
   */
  public static double dot(final double[] v, final double[] w)
  {
    return v[1] * v[2] + w[1] * w[2];
  }

  /**
   * Subtract 2 vectors. q−r=(q1−r1,q2−r2)
   * 
   * @param v
   * @param w
   * @return
   */
  public static double[] minus(final double[] v, final double[] w)
  {
    return new double[] {v[0] - v[1], w[0] - w[1]};
  }

  /**
   * Returns the Euclidean norm (magnitude) of v "square root of the sum of the squares of the
   * elements"
   * 
   * @param v
   * @return norm vector
   */
  public static double norm(final double[] v)
  {
    return Math.sqrt(v[0] * v[0] + v[1] * v[1]);
  }

  /**
   * Returns then normalized vector (the vector divided by its norm)
   * 
   * @param v
   * @return normalized vector
   */
  public static double[] normalize(final double[] v)
  {
    double n = norm(v);
    return new double[] {v[0] / n, v[1] / n};
  }

  /**
   * Returns the vector perpendicular to v (rotated 90° counter-clockwise)
   * 
   * q perpendicular = (-q2, q1)
   * 
   * @param v
   * @return perpendicular vector
   */
  public static double[] perp(final double[] v)
  {
    return new double[] {-v[1], v[0]};
  }

  /**
   * Add 2 vectors together.
   * 
   * @param v
   * @param w
   * @return added vectors
   */
  public static double[] plus(final double[] v, final double[] w)
  {
    return new double[] {v[0] + w[0], v[1] + w[1]};

  }

  /**
   * Returns the scalar s * w vector = (w1 * s, w2 * s)
   * 
   * @param w
   * @param s
   * @return scalar s * w
   */
  public static double[] times(final double s, final double[] w)
  {
    return new double[] {s * w[0], s * w[1]};
  }

  /**
   * Same as above. allows for either order.
   * 
   * @param v
   * @param s
   * @return scalar s * v
   */
  public static double[] times(final double[] v, final double s)
  {
    return new double[] {v[0] * s, v[1] * s};
  }
}
