package cs3500.animator.model.animation;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.util.Dimensions;
import cs3500.animator.model.util.IDuration;

/**
 * Represents a Rotate animation.
 */
public class Rotate extends AAnimation {

  private double fromTheta; // initial degrees
  private double toTheta; // final degrees

  /**
   * Constructs a rotate operation.
   * @param type rotate
   * @param shape shape to rotate
   * @param duration duration of rotation
   * @param fromTheta initial degrees
   * @param toTheta final degrees
   */
  public Rotate(AnimationType type, IShape shape, IDuration duration,
               float fromTheta, float toTheta) {
    super(type, shape, duration);
    this.fromTheta = fromTheta;
    this.toTheta = toTheta;
  }

  @Override
  public String textView(double speed) {
    throw new UnsupportedOperationException("Rotate for Text not supported currently.");
  }

  @Override
  public String svgView(double speed, boolean isLoop) {
    throw new UnsupportedOperationException("Rotate for SVG not supported currently.");
  }

  /**
   * Gets the initial degrees of the shape.
   * @return initial
   */
  public double getFromTheta() {
    return fromTheta;
  }

  /**
   * Gets the final degrees of the shape.
   * @return final
   */
  public double getToTheta() {
    return toTheta;
  }

  @Override
  public void editShape(double t) {
    IShape s = this.getShape();
    double dI = this.getFromTheta();
    double dF = this.getToTheta();
    double dCur = this.tween(dI, dF, t);
    s.setOrientation(dCur);
  }
}
