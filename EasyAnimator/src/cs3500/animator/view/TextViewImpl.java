package cs3500.animator.view;

import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import cs3500.animator.model.IAnimatorModel;

/**
 * Represents Textual View of Animation. Contains methods to output text rendering of animation to
 * console or file, as well as to generate the text.
 */
public class TextViewImpl extends AView {

  /**
   * Constructs a Text View of the animation and specifies the speed.
   *
   * @param model  animation object
   * @param speed  ticks per second
   * @param output output of animation view
   */
  public TextViewImpl(IAnimatorModel model, double speed, String output) {
    super(model, speed, output);
    this.viewType = ViewType.TEXT;
  }

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("This method is suppressed by TextView.");
  }

  @Override
  public void refresh() {
    throw new UnsupportedOperationException("This method is suppressed by TextView.");
  }

  @Override
  public void run() {
    if (this.output.equals("out")  || this.output.equals("")) {
      System.out.println(model.textView());
    } else {
      System.out.println(output);

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

  @Override
  public String getViewString() {
    return model.textView(speed);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    throw new UnsupportedOperationException("This method is suppressed by SVG.");
  }
}