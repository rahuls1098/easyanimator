package cs3500.animator.model.util;

/**
 * Represents a point in 2 dimensional plane.
 */
public class Position2D implements IPosition {
  private double x; // x coordinate
  private double y; // y coordinate

  /**
   * This is a representation of a point in the cartesian plane.
   *
   * @param x The x value.
   * @param y The y value.
   */
  public Position2D(double x, double y) throws IllegalArgumentException {
    this.x = x;
    this.y = y;
  }

  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public String textView() {
    return String.format("(%.1f,%.1f)", getX(), getY());
  }
}