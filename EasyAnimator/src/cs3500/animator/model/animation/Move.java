package cs3500.animator.model.animation;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.util.IDuration;
import cs3500.animator.model.util.IPosition;
import cs3500.animator.model.util.Position2D;

/**
 * Represents a Move Animation.
 */
public class Move extends AAnimation {
  private IPosition positionI; // initial position
  private IPosition positionF; // final position

  /**
   * Constructs the Move Animation.
   *
   * @param type      the type of animation
   * @param shape     the shape of animation
   * @param duration  the duration
   * @param positionI the initial position
   * @param positionF the final position
   */
  public Move(AnimationType type, IShape shape, IDuration duration,
              IPosition positionI, IPosition positionF) {
    super(type, shape, duration);
    this.positionI = positionI;
    this.positionF = positionF;
  }

  /**
   * Gets from position of shape being moved.
   *
   * @return initial pos
   */
  public IPosition getPositionI() {
    return positionI;
  }

  /**
   * Gets to position of shape being moved.
   *
   * @return final pos
   */
  public IPosition getPositionF() {
    return positionF;
  }


  @Override
  public String textView(double speed) {

    String shapeName = getShape().getName();
    String positionI = getPositionI().textView();
    String positionF = getPositionF().textView();
    double durationI = getDuration().getTimeI() / speed;
    double durationF = getDuration().getTimeF() / speed;

    return String.format("Shape %s moves from %s to %s from t=%.1f to t=%.1f\n",
            shapeName, positionI, positionF, durationI, durationF);
  }

  @Override
  public String svgView(double speed, boolean isLooping) {

    String positionXI = Double.toString(getPositionI().getX());
    String positionXF = Double.toString(getPositionF().getX());
    String positionYI = Double.toString(getPositionI().getY());
    String positionYF = Double.toString(getPositionF().getY());
    String durationI = Double.toString(getDuration().getTimeI() / speed);
    String totalDuration = Double.toString(getDuration().getTotalDuration() / speed);

    String attribute1;
    String attribute2;


    switch (getShape().getType()) {
      case ELLIPSE:
        attribute1 = "cx";
        attribute2 = "cy";
        break;
      case RECT:
        attribute1 = "x";
        attribute2 = "y";
        break;
      default:
        return "bad shape type";
    }

    String s1;
    String s2;

    if (isLooping) {

      s1 = setAnimateTag(durationI, totalDuration, attribute1, positionXI, positionXF, true);
      s2 = setAnimateTag(durationI, totalDuration, attribute2, positionYI, positionYF, true);

    } else {

      s1 = setAnimateTag(durationI, totalDuration, attribute1, positionXI, positionXF, false);
      s2 = setAnimateTag(durationI, totalDuration, attribute2, positionYI, positionYF, false);
    }

    return s1 + s2;
  }

  @Override
  public void editShape(double t) {
    IShape s = this.getShape();
    double xI = this.getPositionI().getX();
    double xF = this.getPositionF().getX();
    double yI = this.getPositionI().getY();
    double yF = this.getPositionF().getY();
    double xCur = this.tween(xI, xF, t);
    double yCur = this.tween(yI, yF, t);
    s.setPosition(new Position2D(xCur, yCur));
  }
}