package cs3500.animator.model.util;

import java.util.function.Function;

/**
 * Represents a ColorRGB value in the [0, 1] range, and displays them in the [0, 255] range.
 */
public class ColorRGB implements IColor {
  private double red; // red value
  private double green; // green value
  private double blue; // blue value

  /**
   * Constructs a ColorRGB defined by its red, green and blue values.
   *
   * @param red   Red Value.
   * @param green Green Value.
   * @param blue  Blue Value.
   */
  public ColorRGB(double red, double green, double blue) throws IllegalArgumentException {
    validateColor(red, green, blue);
    this.red = red;
    this.blue = blue;
    this.green = green;
  }

  /**
   * Copy constructor necessary to clone shape fields.
   *
   * @param color The IColor object to be copied.
   */
  public ColorRGB(IColor color) {
    this.red = color.getRed();
    this.blue = color.getBlue();
    this.green = color.getGreen();
  }

  @Override
  public double getRed() {
    return red;
  }

  /**
   * Returns R value as a String in the [0, 255] format.
   *
   * @return R as String
   */
  private String redAsString() {
    return Double.toString(getRed() * 255);
  }

  @Override
  public double getGreen() {
    return green;
  }

  /**
   * Returns G value as a String in the [0, 255] format.
   *
   * @return G as String
   */
  private String greenAsString() {
    return Double.toString(getGreen() * 255);
  }

  @Override
  public double getBlue() {
    return blue;
  }

  /**
   * Returns B value as a String in the [0, 255] format.
   *
   * @return B as String
   */
  private String blueAsString() {
    return Double.toString(getBlue() * 255);
  }

  @Override
  public String textView() {
    return String.format("(%s,%s,%s)", redAsString(), greenAsString(), blueAsString());
  }

  /**
   * Ensures that the RGB values of the shapes being added are within the [0, 1] range.
   *
   * @param red   R value
   * @param green G value
   * @param blue  B value
   */
  private void validateColor(double red, double green, double blue)
          throws IllegalArgumentException {
    Function<Double, Boolean> checkRange = x -> (x >= 0 && x <= 1);

    if (!checkRange.apply(red) || !checkRange.apply(green) || !checkRange.apply(blue)) {
      throw new IllegalArgumentException("color needs to be in [0, 1] range");
    }
  }
}
