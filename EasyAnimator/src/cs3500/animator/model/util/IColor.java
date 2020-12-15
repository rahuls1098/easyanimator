package cs3500.animator.model.util;

/**
 * Interface representing a color.
 */
public interface IColor {

  /**
   * gets the red color value.
   *
   * @return double value representing the red color
   */
  double getRed();

  /**
   * gets the green color value.
   *
   * @return double value representing the green color
   */
  double getGreen();

  /**
   * gets the blue color value.
   *
   * @return double value representing the bluecolor
   */
  double getBlue();

  /**
   * Textual representation of the color.
   *
   * @return string as a text representation of a color value
   */
  String textView();
}
