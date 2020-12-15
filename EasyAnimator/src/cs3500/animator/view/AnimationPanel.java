package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSlider;

import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.ShapeType;

/**
 * Represents the panel containing the animation being rendered. Draws the shapes on the panel based
 * on their given durations, and uses copy of shapes to preserve original shapes.
 */
public class AnimationPanel extends JPanel {
  List<IShape> shapeCopies; // copy list of original shapes
  List<IShape> shapeOriginal;
  double tick; // current tick
  double speed; // current speed
  private JSlider slider; // scrubbing tool


  /**
   * Constructs the panel by initializing the copy of shapes, time, and background.
   */
  public AnimationPanel() {
    super();
    this.speed = 1;
    this.tick = 0;
    this.shapeOriginal = new ArrayList<>();
    this.shapeCopies = new ArrayList<>();
    this.setBackground(Color.WHITE);
  }

  /**
   * Sets the list of the copy of original shapes.
   *
   * @param shapes copy list
   *
   */
  void setShapeCopies(List<IShape> shapes) {
    this.shapeCopies = shapes;
  }

  /**
   * Sets the list of original shapes.
   *
   * @param shapes original list
   */
  void setShapeOriginal(List<IShape> shapes) {
    this.shapeOriginal = shapes;
  }

  /**
   * Gets the copied (from original) list of shapes for uses such as resetting shape objects.
   *
   * @return copy list of shapes
   */
  List<IShape> getShapeCopyAniPanel() {
    return this.shapeCopies;
  }

  /**
   * Resets the values of the shapes.
   */
  void resetShapeValues() {
    shapeCopies = new ArrayList<>();
    for (IShape s : shapeOriginal) {
      shapeCopies.add(s.copy());
    }
  }

  /**
   * Sets the current tick.
   *
   * @param tick current
   */
  void setTime(double tick) {
    this.tick = tick;
  }

  /**
   * Gets current tick.
   *
   * @return tick
   */
  double getTick() {
    return tick;
  }

  @Override
  protected void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    Graphics2D g2d = (Graphics2D) gr;

    for (IShape s : shapeCopies) {
      Graphics2D g22d = (Graphics2D) g2d.create();
      double timeSI = s.getDuration().getTimeI();
      double timeSF = s.getDuration().getTimeF();

      double r = s.getColor().getRed();
      double g = s.getColor().getGreen();
      double b = s.getColor().getBlue();
      double x = s.getPosition().getX();
      double y = s.getPosition().getY();
      double w = s.getDimensions().getDimensions()[0];
      double h = s.getDimensions().getDimensions()[1];
      double t = s.getTheta();

      if (timeSI <= this.getTick() && this.getTick() < timeSF) {
        if (s.getType().equals(ShapeType.RECT)) {
          g22d.rotate(Math.toRadians(t), (x + w/2), (y + h/2));
          g22d.setColor(new Color((float) r, (float) g, (float) b));
          g22d.fillRect((int) x, (int) y, (int) w, (int) h);

        }
        if (s.getType().equals(ShapeType.ELLIPSE)) {

          g22d.rotate(Math.toRadians(t),x, y);
          g22d.setColor(new Color((float) r, (float) g, (float) b));
          g22d.fillOval((int) (x - (w / 2)), (int) (y - (h / 2)), (int) w, (int) h);
        }
      }
      g22d.dispose();
    }
  }
}