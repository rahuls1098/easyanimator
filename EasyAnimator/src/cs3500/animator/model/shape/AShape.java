package cs3500.animator.model.shape;

import cs3500.animator.model.util.IColor;
import cs3500.animator.model.util.IDimensions;
import cs3500.animator.model.util.IDuration;
import cs3500.animator.model.util.IPosition;

/**
 * Represents a Shape abstract class that implements some common functionality of shapes and
 * delegates the rest to concrete shape implementations.
 */
abstract class AShape implements IShape {
  String name; // name of shape
  ShapeType type; // enum type of shape
  IColor color; // color of shape
  double theta; // degrees orientation
  IPosition position; // position of shape
  IDimensions dimensions; // width, height, etc. of shape
  IDuration duration; // appear and disappear time of shape

  /**
   * Constructs and initializes a shape object.
   *
   * @param name       the name of the shape.
   * @param type       the type of the shape.
   * @param color      the color of the shape.
   * @param theta      the orientation of the shape.
   * @param position   the position of the shape.
   * @param dimensions the dimension of the shape.
   * @param duration   the  duration of the shape.
   */
  AShape(String name, ShapeType type, IColor color, double theta,
         IPosition position, IDimensions dimensions, IDuration duration) {
    this.name = name;
    this.type = type;
    this.color = color;
    this.theta = theta;
    this.position = position;
    this.dimensions = dimensions;
    this.duration = duration;
  }

  @Override
  public String quote(String str) {
    return "\"" + str + "\"";
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ShapeType getType() {
    return this.type;
  }

  @Override
  public IColor getColor() {
    return this.color;
  }

  @Override
  public IPosition getPosition() {
    return this.position;
  }

  @Override
  public double getTheta() {
    return this.theta;
  }

  @Override
  public IDimensions getDimensions() {
    return this.dimensions;
  }

  @Override
  public IDuration getDuration() {
    return this.duration;
  }

  @Override
  public int compareTo(IShape o) {
    return this.duration.compareTo(o.getDuration());
  }

  @Override
  public void setColor(IColor color) {
    this.color = color;
  }

  @Override
  public void setPosition(IPosition position) {
    this.position = position;
  }

  @Override
  public void setOrientation(double theta) {
    this.theta = theta;
  }

  @Override
  public void setDimensions(IDimensions dimensions) {
    this.dimensions = dimensions;
  }
}