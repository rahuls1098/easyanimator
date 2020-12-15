package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import cs3500.animator.model.IAnimatorModel;

/**
 * Represents the Visual View of the animation. Outputs animation to the Java Swing GUI. Consists of
 * specifications for format of rendering as well as implementation of timer for the animation. Sets
 * the objects needed for rendering. Implements ActionListener for use by the Timer.
 */
public class VisualViewImpl extends AView {

  final int WIDTH = 750;
  final int HEIGHT = 750;

  /**
   * Constructs VisualViewImpl by setting the size and position of Frame, panel, scroll, etc. Sets
   * the list of shapes needed for AnimationPanel.
   *
   * @param model  IAnimatorModel used by view
   * @param speed  of animation
   * @param output method of viewing
   */
  public VisualViewImpl(IAnimatorModel model, double speed, String output) {
    super(model, speed, output);
    this.viewType = ViewType.VISUAL;
    this.tick = 0;
    this.speed = speed;
    this.rate = (int) (1000.0 / speed);
    Timer timer = new Timer(1, this);
    timer.start();
    // formats frame
    this.setTitle("Animator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // border layout with drawing panel
    this.setLayout(new BorderLayout());
    this.aniPanel = new AnimationPanel();
    aniPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.add(aniPanel, BorderLayout.CENTER);

    // add scrollbar
    JScrollPane scroller = new JScrollPane(aniPanel);
    scroller.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.getContentPane().add(scroller, BorderLayout.CENTER);

    // initialize shape list
    this.setShapeCopies();
    // initialize original shape list
    this.setShapeOriginal();
    // formats content
    this.pack();
  }

  @Override
  public String getViewString() {
    throw new UnsupportedOperationException("This method is suppressed by VisualView.");
  }

  @Override
  public void run() {
    this.makeVisible();
  }
}
