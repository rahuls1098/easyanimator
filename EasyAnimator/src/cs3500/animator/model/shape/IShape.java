package cs3500.animator.model.shape;

import cs3500.animator.model.util.IColor;
import cs3500.animator.model.util.IDimensions;
import cs3500.animator.model.util.IDuration;
import cs3500.animator.model.util.IPosition;

/**
 * Represents the the operations that involve shape objects including rendering each view through
 * field access, or delegating to shape objects to retrieve their information.
 */
public interface IShape extends Comparable<IShape> {

  /**
   * The text view of this shape.
   *
   * @return A String formatted as a textual summary of this shape's details.
   */
  String textView();

  /**
   * The text view of this shape with times modified according to the speed provided.
   *
   * @param speed number of ticks per second
   * @return the string representing the rtext view with modified times.
   */
  String textView(double speed);

  /**
   * Wraps svg code relating to shape in quotation literals.
   *
   * @param str string to be quoted
   * @return str in quotes
   */
  String quote(String str);

  /**
   * Getter for the name of this shape.
   *
   * @return A string representing the name of this shape.
   */
  String getName();

  /**
   * Gets degrees.
   * @return degrees
   */
  double getTheta();

  /**
   * Getter for the type of this shape.
   *
   * @return a ShapeType (Enum) representing the type of this shape.
   */
  ShapeType getType();

  /**
   * Getter for the Color of this Shape.
   *
   * @return A Color object that represents the color of this shape.
   */
  IColor getColor();

  /**
   * Getter for the Position of this Shape.
   *
   * @return A Position object representing the position of this shape.
   */
  IPosition getPosition();

  /**
   * Getter for the Dimension of this Shape.
   *
   * @return a Dimesion of this object representing the position of this shape.
   */
  IDimensions getDimensions();

  /**
   * Getter for the Duration of this Shape.
   *
   * @return a duration object representing the time interval of this shape.
   */
  IDuration getDuration();

  /**
   * Sets the position of the shape.
   *
   * @param position final pos
   */
  void setPosition(IPosition position);

  /**
   * Sets the orientation of the shape.
   *
   * @param theta degrees
   */
  void setOrientation(double theta);

  /**
   * Sets the color of the shape.
   *
   * @param color final color
   */
  void setColor(IColor color);

  /**
   * Sets the dimensions of the shape.
   *
   * @param dimensions final dimensions
   */
  void setDimensions(IDimensions dimensions);

  /**
   * Copies the shape object. (DEEP copy).
   *
   * @return the IShape object's copy.
   */
  IShape copy();

  /**
   * The SVG view of the shape.
   *
   * @return String representing the SVG view of the shape.
   */
  String svgView(double speed);

  /**
   * Gets the base end tags for looping.
   * @param speed speed of the animation
   * @return a string representing the base end tags to loop the shape's animations.
   */
  String getBaseEndTags(double speed);
}
