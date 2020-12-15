package cs3500.animator;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.IController;

/**
 * Represents the EasyAnimator program. Initializes constructor and parsing of arguments.
 */
public final class EasyAnimator {


  /**
   * Runs the program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    //Initializes and starts controller
    IController controller = new AnimatorController();
    controller.parseCommandLine(args);
    controller.start();
  }
}

