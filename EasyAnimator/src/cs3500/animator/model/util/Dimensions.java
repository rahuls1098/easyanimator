package cs3500.animator.model.util;

import java.util.Arrays;

/**
 * The dimensions class represents the dimensions of a possible shape, with added flexibility to
 * specify n number of dimensions for more complicated shapes.
 */
public class Dimensions implements IDimensions {

  private double[] dimensions; // array of dimensions (width, height, etc.)

  /**
   * Represents the characteristics needed to define a shape.
   *
   * @param dimensions A different shapes have different dimensions. The dimension array will be
   *                   interpreted uniquely when dispatched to it's respective shape class.
   */
  public Dimensions(double... dimensions) throws IllegalArgumentException {
    if (dimensions.length < 2) {
      throw new IllegalArgumentException("dimensions should be >= 2");
    } else if (Arrays.stream(dimensions).filter(i -> i < 0).count() > 0) {
      throw new IllegalArgumentException("a dimension cannot be negative");
    } else {
      this.dimensions = dimensions;
    }
  }

  /**
   * Copy constructor for cloning Shape dimension field.
   *
   * @param dimensions The dimension object to be copied.
   */
  public Dimensions(IDimensions dimensions) {
    this.dimensions = dimensions.getDimensions();
  }

  @Override
  public double[] getDimensions() {
    return dimensions;
  }
}
