package cs3500.animator.model.animation;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.util.IDuration;

/**
 * Represents an Animation abstract class that implements some common functionality of operations
 * and delegates the rest to concrete animation implementations.
 */
public abstract class AAnimation implements IAnimation {

  AnimationType type; // type of animation
  IShape shape; // shape object being modified
  IDuration duration; // start and end time of animation

  /**
   * Initializes an abstract animation of a shape.
   *
   * @param type     enum representing the type of the shape.
   * @param shape    the shape object.
   * @param duration the duration object representing the duration of the shape.
   */
  public AAnimation(AnimationType type, IShape shape, IDuration duration) {
    this.type = type;
    this.shape = shape;
    this.duration = duration;
  }

  @Override
  public abstract void editShape(double t);

  @Override
  public double tween(double a, double b, double t) {
    double ta = this.getDuration().getTimeI();
    double tb = this.getDuration().getTimeF();
    return (a * ((tb - t) / (tb - ta))) + (b * ((t - ta) / (tb - ta)));
  }

  /**
   * Abstractly makes an animation tag.
   * @param durationI the initial duration.
   * @param totalDuration the total duration.
   * @param attribute the attribute of the animation
   * @param shapeAttributeI the intial attribute.
   * @param shapeAttributeF the final attribute.
   * @param isLooping is it loopin?
   * @return a string represing the animation tag in the svg string.
   */
  String setAnimateTag(String durationI, String totalDuration, String attribute,
                       String shapeAttributeI, String shapeAttributeF, boolean isLooping) {

    String loopingString = durationI + "s";
    if (isLooping) {
      loopingString = "base.begin+" + loopingString;
    }

    return "        <animate"
            + " attributeType="
            + quote("xml")
            + " begin="
            + quote(loopingString)
            + " dur="
            + quote(totalDuration + "s")
            + " attributeName="
            + quote(attribute)
            + " from="
            + quote(shapeAttributeI)
            + " to="
            + quote(shapeAttributeF)
            + " fill="
            + quote("freeze")
            + "/>\n";
  }

  @Override
  public AnimationType getType() {
    return type;
  }

  @Override
  public IShape getShape() {
    return shape;
  }

  @Override
  public void setShape(IShape s) {
    this.shape = s;
  }

  @Override
  public IDuration getDuration() {
    return duration;
  }

  @Override
  public String quote(String str) {
    return "\"" + str + "\"";
  }

  @Override
  public String textView() {
    return textView(1);
  }

  @Override
  public int compareTo(IAnimation o) {
    return this.duration.compareTo(o.getDuration());
  }
}
