package geography;

/**
 * Implements the Albers Conical Equal Area map projection.
 */
public class ConicalEqualAreaProjection extends AbstractMapProjection
{

  private double lambdaZero;
  private double n;
  private double c;
  private double p0;

  /**
   * Constructs a ConicalEqualAreaProjection with the given reference meridian, parallel, and
   * standard parallels.
   * 
   * @param refM
   *          the reference meridian in degrees
   * @param refP
   *          the reference parallel in degrees
   * @param refP1
   *          the first standard parallel in degrees
   * @param refP2
   *          the second standard parallel in degrees
   */
  public ConicalEqualAreaProjection(double refM, double refP, double refP1, double refP2)
  {
    this.lambdaZero = Math.toRadians(refM);
    double refPRadian = Math.toRadians(refP);
    double refP1Radian = Math.toRadians(refP1);
    double refP2Radian = Math.toRadians(refP2);

    this.n = 0.5 * (Math.sin(refP1Radian) + Math.sin(refP2Radian));
    this.c = Math.pow(Math.cos(refP1Radian), 2) + (2 * n * Math.sin(refP2Radian));
    this.p0 = Math.sqrt(this.c - (2 * this.n * Math.sin(refPRadian))) / this.n;
  }

  @Override
  public double[] forward(double lambda, double phi)
  {

    double p = Math.sqrt(this.c - 2 * this.n * Math.sin(phi)) / this.n;
    double theta = this.n * (lambda - lambdaZero);

    double[] val;
    double p1 = R * p * Math.sin(theta);
    double p2 = R * (this.p0 - p * Math.cos(theta));

    val = new double[] {p1, p2};
    return val;
  }

  @Override
  public double[] inverse(double ew, double ns)
  {
    double a = Math.sqrt(Math.pow(ew / R, 2) + Math.pow(this.p0 - ns / R, 2));
    double b = Math.atan2((ew / R), (this.p0 - ns / R));

    double[] val;
    double phi = Math.asin((this.c - Math.pow(a, 2) * Math.pow(n, 2))) / (2 * this.n);
    double lambda = lambdaZero + b / this.n;

    val = new double[] {phi, lambda};
    return val;
  }

}
