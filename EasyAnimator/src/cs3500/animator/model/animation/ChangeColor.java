package cs3500.animator.model.animation;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.util.ColorRGB;
import cs3500.animator.model.util.IColor;
import cs3500.animator.model.util.IDuration;

/**
 * Represents a color change animation.
 */
public class ChangeColor extends AAnimation {
  private IColor colorI; // initial color
  private IColor colorF; // final color

  /**
   * Constructs an animation that changes color of a shape object.
   *
   * @param type     the type of animation
   * @param shape    the shape to be animated
   * @param duration the duration of the animation
   * @param colorI   the initial color of the shape
   * @param colorF   the final color of the shape
   */
  public ChangeColor(AnimationType type, IShape shape, IDuration duration,
                     IColor colorI, IColor colorF) {
    super(type, shape, duration);
    this.colorI = colorI;
    this.colorF = colorF;
  }

  /**
   * Gets this animation's from color.
   *
   * @return initial color
   */
  public IColor getColorI() {
    return colorI;
  }

  /**
   * Gets this animation's to color.
   *
   * @return final color
   */
  public IColor getColorF() {
    return colorF;
  }

  @Override
  public String textView(double speed) {
    String shapeName = getShape().getName();
    String colorI = getColorI().textView();
    String colorF = getColorF().textView();
    double timeI = getDuration().getTimeI() / speed;
    double timeF = getDuration().getTimeF() / speed;

    return String.format("Shape %s changes color from %s to %s from t=%.1f to t=%.1f\n",
            shapeName, colorI, colorF, timeI, timeF);
  }

  @Override
  public String svgView(double speed, boolean isLoop) {

    String colorI = getColorI().textView();
    String colorF = getColorF().textView();
    String durationI = Double.toString(getDuration().getTimeI() / speed);
    String totalDuration = Double.toString(getDuration().getTotalDuration() / speed);

    String s;

    if (isLoop) {

      s = "        <animate"
              + " attributeType="
              + quote("xml")
              + " begin="
              + quote("base.begin+" + durationI + "s")
              + " dur="
              + quote(totalDuration + "s")
              + " attributeName="
              + quote("fill")
              + " from="
              + quote("rgb" + colorI)
              + " to="
              + quote("rgb" + colorF)
              + " fill="
              + quote("freeze")
              + "/>\n";


    } else {
      s = "        <animate"
              + " attributeType="
              + quote("xml")
              + " begin="
              + quote(durationI + "s")
              + " dur="
              + quote(totalDuration + "s")
              + " attributeName="
              + quote("fill")
              + " from="
              + quote("rgb" + colorI)
              + " to="
              + quote("rgb" + colorF)
              + " fill="
              + quote("freeze")
              + "/>\n";
    }

    return s;
  }

  @Override
  public void editShape(double t) {
    IShape s = this.getShape();
    double rI = this.getColorI().getRed();
    double rF = this.getColorF().getRed();
    double gI = this.getColorI().getGreen();
    double gF = this.getColorF().getGreen();
    double bI = this.getColorI().getBlue();
    double bF = this.getColorF().getBlue();
    double rCur = this.tween(rI, rF, t);
    double gCur = this.tween(gI, gF, t);
    double bCur = this.tween(bI, bF, t);
    s.setColor(new ColorRGB(rCur, gCur, bCur));
  }

}
