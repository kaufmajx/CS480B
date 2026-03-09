package geography;

/**
 * Implements the Sinusoidal map projection.
 */
public class SinusoidalProjection extends AbstractMapProjection
{
  private static final double LAMBDA_ZERO = 0.0;

  @Override
  public double[] forward(final double lambda, final double phi)
  {
    double[] val;
    double d1 = R * Math.cos(phi) * (lambda - LAMBDA_ZERO);
    double d2 = R * phi;

    val = new double[] {d1, d2};
    return val;
  }

  @Override
  public double[] inverse(final double ew, final double ns)
  {
    double phi = ns / R;
    double[] val;
    double d1 = LAMBDA_ZERO + ew / R * Math.cos(phi);
    double d2 = phi;
    val = new double[] {d1, d2};
    return val;
  }

}
