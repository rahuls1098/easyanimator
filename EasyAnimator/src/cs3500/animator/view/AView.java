package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IShape;

/**
 * Abstract implementation of view that constructs a View with certain parameters mutual to all
 * views available. Extends JFrame specifically for VisualViewImpl.
 */
public abstract class AView extends JFrame implements IView, ActionListener {

  protected AnimationPanel aniPanel; // panel representing animation
  protected IAnimatorModel model; // animation
  protected double speed; // ticks per second
  protected String output; // the output file
  protected int rate; // delay
  protected double tick; // current tick
  protected boolean isLoop; // is animation looping
  protected ViewType viewType; // type of view
  protected JSlider slider; // slider for scrubbing
  protected int scrubVal; // position of scrubber
  private int maxRange; // end time of animation
  /**
   * Initializes model to be used by view as well as user-input speed and output file.
   *
   * @param model  IAnimator model
   * @param speed  of animation
   * @param output file
   */
  protected AView(IAnimatorModel model, double speed, String output) {
    this.model = model;
    this.speed = speed;
    this.output = output;

    // scrubbing
    this.maxRange = this.getRangeOfAnimation();
    this.scrubVal = 1;
    this.slider = new JSlider(JSlider.HORIZONTAL, 1, 1000, 1);
    this.slider.addChangeListener(e -> {
      scrubVal = ((JSlider)e.getSource()).getValue();
      int currentTick = ((scrubVal * maxRange) / 1000);
      //aniPanel.resetShapeValues();
      //this.resetAnimationShapes(currentTick);
      //aniPanel.setTime(currentTick);
      System.out.println(scrubVal);
    });
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Gets the end time of the animation.
   * @return last tick
   */
  private int getRangeOfAnimation() {
    double max = 0;
    for(IShape s : model.getShapeList()) {
      if(s.getDuration().getTimeF() > max) {
        max = s.getDuration().getTimeF();
      }
    }
    return (int) max;
  }
  /**
   * Sets the copy list of original shapes needed to be rendered and mutated to show animation.
   */
  protected void setShapeCopies() {
    aniPanel.setShapeCopies(model.getShapeCopyList());
  }

  /**
   * Sets the original list of original shapes needed for restarting or looping the animation.
   */
  protected void setShapeOriginal() {
    aniPanel.setShapeOriginal(model.getShapeList());
  }

  /**
   * Resets the shapes of the animation objects to their default values so that looping and
   * restarting methods have the original shapes.
   * @param tick at which to reset shape state to
   */
  protected void resetAnimationShapes(int tick) {
    List<IShape> shapes = aniPanel.getShapeCopyAniPanel();
    for (IAnimation a : model.getAnimationList()) {
      for (IShape s : shapes) {
        if (s.getName().equals(a.getShape().getName())) {
          a.setShape(s);
          //a.editShape(tick);
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // tracks ticks
    tick += 1;
    // sets tick in AnimationPanel
    aniPanel.setTime(tick);
    // updates scrubber value to move with animation
    slider.setValue((1000 / maxRange) * (int) tick);
    // refreshes view
    this.refresh();
    // performs tweening
    this.executeAnimations(tick);
    // loops if set to looping
    IAnimation lastAnimation = model.getAnimationList().get(model.getAnimationList().size() - 1);
    if (this.isLoop && this.tick == lastAnimation.getDuration().getTimeF()
            && this.viewType.equals(ViewType.INTERACTIVE)) {
      HybridViewImpl hv = (HybridViewImpl) this;
      hv.restart();
    }
  }

  /**
   * On each tick, this method executes the editing of the shape based on the current animation to
   * be rendered at the current time.
   *
   * @param tick current
   */
  protected void executeAnimations(double tick) {
    for (IAnimation a : model.getAnimationList()) {
      if (a.getDuration().getTimeI() <= tick && tick <= a.getDuration().getTimeF()) {
        a.editShape(tick);
      }
    }
  }
}
