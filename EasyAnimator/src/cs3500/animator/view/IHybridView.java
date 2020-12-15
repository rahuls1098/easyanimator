package cs3500.animator.view;

import java.awt.event.ActionListener;

/**
 * Represents the functionality of an interactive view. Consists of methods to manipulate the
 * animation being played, as well as getters and setters to retrieve current state as needed.
 * Allows for initializing action listeners to GUI objects.
 */
public interface IHybridView {

  /**
   * restarts the animation.
   */
  void restart();

  /**
   * pause the animation.
   */
  void pause();

  /**
   * resume the animation.
   */
  void resume();

  /**
   * set the loop to on or off.
   */
  void setLoop();

  /**
   * set the speed of the animation to a new speed.
   *
   * @param newSpeed the new speed for the animation.
   */
  void setSpeed(double newSpeed);

  /**
   * export SVG of the animation to the file specified.
   */
  void exportSVG();

  /**
   * Gets the speed input by the user.
   */
  double getSpeedInput();

  /**
   * Gets the name of the SVG file input by the user.
   */
  String getSVGFileName();

  /**
   * Gets the current tick.
   */
  double getTick();

  /**
   * Gets whether view is looping or not.
   *
   * @return is loop?
   */
  boolean getIsLoop();

  /**
   * Sets the speed input of the speed text field.
   *
   * @param speed input by user
   */
  void setSpeedInput(String speed);

  /**
   * Sets the file name for the SVG file text field.
   *
   * @param fileName svg file name
   */
  void setSVGFileName(String fileName);

  /**
   * Determine whether animation is currently paused or playing.
   *
   * @return animation play status
   */
  boolean getIsPlaying();

  /**
   * Adds action listeners to JButtons to perform actions upon them being clicked.
   *
   * @param listener listens for user click
   */
  void addActionListener(ActionListener listener);
}
