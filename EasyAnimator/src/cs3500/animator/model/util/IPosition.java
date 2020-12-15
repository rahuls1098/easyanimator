package cs3500.animator.model.util;

/**
 * Interface representing a position of the shape.
 */
public interface IPosition {

  /**
   * Gets the x coordinate.
   *
   * @return the x coordinate as double value
   */
  double getX();

  /**
   * Gets the y coordinate.
   *
   * @return the y coordinate as double value
   */
  double getY();

  /**
   * Textual representation of a position.
   *
   * @return a string that returns the textual representation of a position
   */
  String textView();
}
