package cs3500.animator.model.animation;


import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.util.IDuration;

/**
 * Represents an Animation operation.
 */
public interface IAnimation extends Comparable<IAnimation> {

  /**
   * Gets the shape object of the animation.
   *
   * @return the shape object of the animation.
   */
  IShape getShape();

  /**
   * Sets the shape for the animation.
   * @param s new shape
   */
  void setShape(IShape s);

  /**
   * gets the duration object of the animation.
   *
   * @return a duration object representing the duration of the animation.
   */
  IDuration getDuration();

  /**
   * Gets the type of the animation.
   *
   * @return the type of the animation.
   */
  AnimationType getType();

  /**
   * Gets the text view of the animation.
   *
   * @return String representing the textual view.
   */
  String textView();

  /**
   * Gets the text view of the animation with the times modified as per the speed.
   *
   * @param speed ticks per second.
   * @return String which holds the textual view.
   */
  String textView(double speed);

  /**
   * Returns the SVG code for the given animation operation.
   *
   * @param speed how fast the animation is
   * @param isLoop whether the animation loops or not
   * @return code for animation
   */
  String svgView(double speed, boolean isLoop);

  /**
   * Returns a String wrapped in quote literals.
   *
   * @param str string to be wrapped
   * @return str in quotes
   */
  String quote(String str);

  /**
   * Modifies the shape by calling tweening on its parameters.
   *
   * @param t current time to start in tween function
   */
  void editShape(double t);

  /**
   * Returns state of input dimensions at each time.
   *
   * @param a initial value of dimension being changed
   * @param b final value of dimension being changed
   * @param t current time during the transition
   * @return current state of dimension at given time
   */
  double tween(double a, double b, double t);
}
