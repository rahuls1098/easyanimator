package cs3500.animator.model.shape;

import cs3500.animator.model.util.ColorRGB;
import cs3500.animator.model.util.Dimensions;
import cs3500.animator.model.util.Duration;
import cs3500.animator.model.util.IColor;
import cs3500.animator.model.util.IDimensions;
import cs3500.animator.model.util.IDuration;
import cs3500.animator.model.util.IPosition;
import cs3500.animator.model.util.Position2D;

/**
 * Represents a Rectangle Shape.
 */
public class Rectangle extends AShape {

  /**
   * Constructs a Rectangle with user specified orientation.
   *
   * @param name       name of the shape
   * @param type       type of the shape
   * @param color      color of the shape
   * @param position   position of the shape
   * @param dimensions dimension of the shape
   * @param duration   duration fo the existence
   */
  public Rectangle(String name, ShapeType type, IColor color, double theta,
                   IPosition position, IDimensions dimensions, IDuration duration) {
    super(name, type, color, theta, position, dimensions, duration);
  }

  /**
   * Constructs a rectangle as above but with default orientation of 90 degrees.
   */
  public Rectangle(String name, ShapeType type, IColor color,
                   IPosition position, IDimensions dimensions, IDuration duration) {
    super(name, type, color, 0, position, dimensions, duration);
  }

  @Override
  public String textView() {
    return textView(1);
  }

  @Override
  public String textView(double speed) {

    String name = getName();
    String type = "rectangle";
    String position = getPosition().textView();
    double width = getDimensions().getDimensions()[0];
    double height = getDimensions().getDimensions()[1];
    String color = getColor().textView();
    double appears = getDuration().getTimeI() / speed;
    double disappears = getDuration().getTimeF() / speed;

    return String.format("Name: %s\n"
                    + "Type: %s\n"
                    + "Corner: %s, Width: %.1f, Height: %.1f, Color: %s\n"
                    + "Appears at t=%.1f\n"
                    + "Disappears at t=%.1f",
            name, type, position, width, height, color, appears, disappears);
  }

  @Override
  public IShape copy() {
    return new Rectangle(this.getName(),
            this.getType(),
            new ColorRGB(this.getColor()), this.getTheta(),
            new Position2D(this.getPosition().getX(), this.getPosition().getY()),
            new Dimensions(this.getDimensions().getDimensions()[0],
                    this.getDimensions().getDimensions()[1]),
            new Duration(this.getDuration().getTimeI(), this.getDuration().getTimeF()));
  }

  @Override
  public String svgView(double speed) {
    String name = getName();
    String positionX = Double.toString(getPosition().getX());
    String positionY = Double.toString(getPosition().getY());
    String width = String.format("%.1f", getDimensions().getDimensions()[0]);
    String height = String.format("%.1f", getDimensions().getDimensions()[1]);
    String color = getColor().textView();

    return String.format("<rect id=%s x=%s y=%s width=%s height=%s fill=%s "
                    + "visibility=\"visible\" >",
            quote(name), quote(positionX), quote(positionY),
            quote(width), quote(height),
            quote("rgb" + color));
  }

  @Override
  public String getBaseEndTags(double speed) {

    String positionX = Double.toString(getPosition().getX());
    String positionY = Double.toString(getPosition().getY());
    String width = String.format("%.1f", getDimensions().getDimensions()[0]);
    String height = String.format("%.1f", getDimensions().getDimensions()[1]);
    String color = getColor().textView();
    String sp = "        ";
    String initX = sp + "<animate begin=\"base.end\" dur=\"1ms\" attributeName=\"x\" "
            + "to=\"" + positionX + "\" fill=\"freeze\" />";
    String initY = sp + "<animate begin=\"base.end\" dur=\"1ms\" attributeName=\"y\" "
            + "to=\"" + positionY + "\" fill=\"freeze\" />";
    String initWidth = sp + "<animate begin=\"base.end\" dur=\"1ms\" attributeName=\"width\" "
            + "to=\"" + width + "\" fill=\"freeze\" />";
    String initHeight = sp + "<animate begin=\"base.end\" dur=\"1ms\" attributeName=\"height\" "
            + "to=\"" + height + "\" fill=\"freeze\" />";
    String initColor = sp + "<animate begin=\"base.end\" dur=\"1ms\" attributeName=\"fill\" "
            + "to=\"" + color + "\" fill=\"freeze\" />";

    String s = initX + "\n" + initY + "\n" + initWidth + "\n" + initHeight + "\n" + initColor;
    return s;
  }
}
