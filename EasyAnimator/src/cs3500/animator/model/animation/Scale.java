package cs3500.animator.model.animation;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.util.Dimensions;
import cs3500.animator.model.util.IDimensions;
import cs3500.animator.model.util.IDuration;

/**
 * Represents a Scale Animation.
 */
public class Scale extends AAnimation {
  private IDimensions dimensionsI; // initial dimension
  private IDimensions dimensionsF; // final dimension

  /**
   * Constructs a scaling operation.
   *
   * @param type        the type of the animation
   * @param shape       the shape to be animated
   * @param duration    the duration of the animation
   * @param dimensionsI the initial dimensions
   * @param dimensionsF the final dimensions
   */
  public Scale(AnimationType type, IShape shape, IDuration duration,
               IDimensions dimensionsI, IDimensions dimensionsF) {
    super(type, shape, duration);
    this.dimensionsI = dimensionsI;
    this.dimensionsF = dimensionsF;
  }

  /**
   * Gets the original dimensions of the shape being scaled.
   *
   * @return from dimension
   */
  public IDimensions getDimensionsI() {
    return dimensionsI;
  }

  /**
   * Gets the final dimension of the shape being scaled.
   *
   * @return to dimension
   */
  public IDimensions getDimensionsF() {
    return dimensionsF;
  }

  @Override
  public String textView(double speed) {
    String shapeName = getShape().getName();
    double dimension1I = getDimensionsI().getDimensions()[0];
    double dimension2I = getDimensionsI().getDimensions()[1];
    double dimension1F = getDimensionsF().getDimensions()[0];
    double dimension2F = getDimensionsF().getDimensions()[1];
    double timeI = getDuration().getTimeI() / speed;
    double timeF = getDuration().getTimeF() / speed;

    switch (getShape().getType()) {
      case ELLIPSE:
        return String.format("Shape %s scales from X radius: %.1f, Y radius: %.1f "
                        + "to X radius: %.1f, Y radius: %.1f from t=%.1f to t=%.1f\n",
                shapeName, dimension1I, dimension2I, dimension1F, dimension2F, timeI, timeF);
      case RECT:
        return String.format("Shape %s scales from Width: %.1f, Height: %.1f "
                        + "to Width: %.1f, Height: %.1f from t=%.1f to t=%.1f\n",
                shapeName, dimension1I, dimension2I, dimension1F, dimension2F, timeI, timeF);
      default:
        return "Illegal Shape does not exist";
    }
  }

  @Override
  public String svgView(double speed, boolean isLooping) {


    String durationI = Double.toString(getDuration().getTimeI() / speed);
    String totalDuration = Double.toString(getDuration().getTotalDuration() / speed);

    String shapeName = getShape().getName();
    String dimension1I = Double.toString(getDimensionsI().getDimensions()[0]);
    String dimension2I = Double.toString(getDimensionsI().getDimensions()[1]);
    String dimension1F = Double.toString(getDimensionsF().getDimensions()[0]);
    String dimension2F = Double.toString(getDimensionsF().getDimensions()[1]);

    String attribute1;
    String attribute2;

    switch (getShape().getType()) {
      case ELLIPSE:
        attribute1 = "cx";
        attribute2 = "cy";
        break;
      case RECT:
        attribute1 = "width";
        attribute2 = "height";
        break;
      default:
        return "bad shape type";
    }

    String s1;
    String s2;

    if (isLooping) {

      s1 = setAnimateTag(durationI, totalDuration, attribute1, dimension1I, dimension1F, true);
      s2 = setAnimateTag(durationI, totalDuration, attribute2, dimension2I, dimension2F, true);

    } else {

      s1 = setAnimateTag(durationI, totalDuration, attribute1, dimension1I, dimension1F, false);
      s2 = setAnimateTag(durationI, totalDuration, attribute2, dimension2I, dimension2F, false);
    }

    return s1 + s2;
  }

  @Override
  public void editShape(double t) {
    IShape s = this.getShape();
    double wI = this.getDimensionsI().getDimensions()[0];
    double wF = this.getDimensionsF().getDimensions()[0];
    double hI = this.getDimensionsI().getDimensions()[1];
    double hF = this.getDimensionsF().getDimensions()[1];
    double wCur = this.tween(wI, wF, t);
    double hCur = this.tween(hI, hF, t);
    double[] d = {wCur, hCur};
    s.setDimensions(new Dimensions(d));
  }
}