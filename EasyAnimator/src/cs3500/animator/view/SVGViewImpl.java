package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IShape;

/**
 * Represents the concrete implementation of the SVG view. Contains methods to run view in order to
 * print or generate output for the view, as well as the ability to generate the code.
 */
public class SVGViewImpl extends AView {

  /**
   * Constructs an SVG view with looping disabled by default.
   *
   * @param model  basis of view
   * @param speed  of animation
   * @param output output method of view
   */
  public SVGViewImpl(IAnimatorModel model, double speed, String output) {
    super(model, speed, output);
    this.viewType = ViewType.SVG;
    this.isLoop = false;
  }

  /**
   * Constructs an SVG view with looping set to what the user opted upon creation.
   *
   * @param model  basis of view
   * @param speed  of animation
   * @param output method of view
   * @param isLoop looping or not
   */
  public SVGViewImpl(IAnimatorModel model, double speed, String output, boolean isLoop) {
    super(model, speed, output);
    this.viewType = ViewType.SVG;
    this.isLoop = isLoop;
  }


  @Override
  public void run() {
    if (this.output.equals("out") || this.output.equals("")) {
      System.out.println(this.getViewString());
    } else {
      PrintStream ps = null;
      try {
        ps = new PrintStream(new FileOutputStream(output));
        ps.println(getViewString());
      } catch (IOException e) {
        e.printStackTrace();
      }
      ps.flush();
      ps.close();
    }
  }

  /**
   * Computes the latest ending time of all animations in the list in s.
   *
   * @return max end time in s plus a margin
   */
  private String getLatestEnding() {
    double max = 0.0;
    for (IAnimation a : model.getAnimationList()) {
      if (a.getDuration().getTimeF() > max) {
        max = a.getDuration().getTimeF();
      }
    }
    return Double.toString(max / speed);
  }

  @Override
  public String getViewString() {
    List<IShape> shapeList = model.getShapeList();
    Map<String, List<IAnimation>> nameAnimation = model.getNameAnimation();
    StringBuilder sb = new StringBuilder();
    sb.append("<svg width=\"1000\" height=\"1000\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");

    String maxDur = this.getLatestEnding();
    if (isLoop) {
      sb.append("    <rect>\n"
              + "        <animate id=\"base\" begin=\"0;base.end\" dur=\"" + maxDur
              + "s\" attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n" +
              "    </rect>\n\n");
    }

    for (IShape shape : shapeList) {
      // extracting the name of the current shape
      String shapeName = shape.getName();

      // getting the shape information as svg
      // adding 4 spaces to pretty print the svg
      sb.append("    " + shape.svgView(speed) + "\n\n");

      // only accesses the map if the shape has any animations
      if (nameAnimation.get(shapeName) != null) {
        // going through the animation list
        for (IAnimation animation : nameAnimation.get(shapeName)) {
          // ... and appending each animation svg
          sb.append(animation.svgView(speed, isLoop));
        }
      }

      sb.append("\n" + shape.getBaseEndTags(speed) + "\n\n");

      // the closing tag for the shape
      sb.append(String.format("    </%s>\n\n", shape.getType().toString().toLowerCase()));
    }
    // the closing tag for the svg
    sb.append("</svg>");

    return sb.toString();
  }

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("This method is suppressed by SVG.");
  }

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("This method is suppressed by SVG.");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    throw new UnsupportedOperationException("This method is suppressed by SVG.");
  }
}