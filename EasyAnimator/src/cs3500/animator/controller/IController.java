package cs3500.animator.controller;

import cs3500.animator.view.IView;
import cs3500.animator.view.ViewType;

/**
 * This interface represents the initialization of the the view.
 */
public interface IController {
  /**
   * Calls method to run the rendering of the view based on input command args.
   */
  void start();

  /**
   * Parses command line arguments to create a model and view based on specifications.
   *
   * @param args input file, speed, output file, view type
   */
  void parseCommandLine(String[] args);

  /**
   * Gets view type.
   * @return view type
   */
  ViewType getViewType();

  /**
   * Gets view.
   * @return view
   */
  IView getView();

  /**
   * Gets speed.
   * @return speed
   */
  double getSpeed();

  /**
   * Gets output file.
   * @return output file
   */
  String getOutputFile();
}
