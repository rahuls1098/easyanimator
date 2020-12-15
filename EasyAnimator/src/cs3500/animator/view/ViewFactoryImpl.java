package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;

/**
 * Represents the implementation of View Factory interface. Checks for view based on user-input and
 * returns an instance of that view.
 */
public class ViewFactoryImpl implements IViewFactory {

  private IView viewType; // which IView implementation
  private IAnimatorModel model; // IAnimatorModel to be used by view
  private double speed; // speed of animation
  private String output; // method of viewing

  /**
   * Constructs a ViewFactoryImpl by taking in user-provided file for model, speed, and output
   * format.
   *
   * @param model  IAnimatorModel animation
   * @param speed  of animation
   * @param output method of viewing
   */
  public ViewFactoryImpl(IAnimatorModel model, double speed, String output) {
    if (model == null) {
      throw new IllegalArgumentException("Model should not be null.");
    }
    this.output = output;
    this.model = model;
    this.speed = speed;
  }

  @Override
  public IView createView(String view) {
    switch (view) {
      case "text":
        return new TextViewImpl(this.model, this.speed, this.output);
      case "visual":
        return new VisualViewImpl(this.model, this.speed, this.output);
      case "svg":
        return new SVGViewImpl(this.model, this.speed, this.output, false);
      case "interactive":
        return new HybridViewImpl(this.model, this.speed, this.output);
      default:
        throw new IllegalArgumentException("Invalid view type.");
    }
  }
}