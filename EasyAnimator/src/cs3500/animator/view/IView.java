package cs3500.animator.view;

/**
 * The View interface that is implemented by the multiple representations of the animation. Contains
 * method to run the animation.
 */
public interface IView {
  /**
   * Runs the animation program.
   */
  void run();

  /**
   * Make the view visible, called after view is constructed.
   */
  void makeVisible();

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Returns String representing the input file as SVG code.
   */
  String getViewString();
}
