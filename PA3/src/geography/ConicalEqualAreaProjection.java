package geography;

public class ConicalEqualAreaProjection extends AbstractMapProjection
{

  private static final double LAMBDA_ZERO = 0.0;
  private double n;
  private double c;
  private double p0;

  public ConicalEqualAreaProjection(double refM, double refP, double refP1, double refP2)
  {
    this.n = 0.5 * (Math.sin(refP1) + Math.sin(refP2));
    this.c = Math.pow(Math.cos(refP1), 2) + (2 * n * Math.sin(refP1));
    this.p0 = Math.sqrt(this.c - (2 * this.n * Math.sin(refP))) / this.n;
  }

  @Override
  public double[] forward(double lambda, double phi)
  {

    double p = Math.sqrt(this.c - 2 * this.n * Math.sin(phi)) / this.n;
    double theta = this.n * (lambda - LAMBDA_ZERO);

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
    double b = Math.pow(Math.tan((ew / 3) / (this.p0 - ns / R)), -1);

    double[] val;
    double phi = Math.pow((Math.sin(this.c - Math.pow(a, 2) * Math.pow(n, 2)) / (2 * this.n)), 2);
    double lambda = LAMBDA_ZERO + b / this.n;

    val = new double[] {phi, lambda};
    return val;
  }

}
