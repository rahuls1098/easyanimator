package cs3500.animator.view;

/**
 * Interface representing the creation of an instance of an IView based on the user-input view.
 */
public interface IViewFactory {
  /**
   * Creates a specific instance of an IView.
   *
   * @param view String specifying which view to create
   * @return one of three IView
   */
  IView createView(String view);
}
