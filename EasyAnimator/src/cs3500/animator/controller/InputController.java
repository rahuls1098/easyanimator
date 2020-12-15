package cs3500.animator.controller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.animator.view.IHybridView;

/**
 * Represents the delegation of received action events to their respective methods in the
 * HybridView. Instantiates the map that allows such event to be recognized, and provides access to
 * view from control.
 */
public class InputController {

  private static IHybridView view; // view to act upon
  private static JPanel dialogue; // error message window
  private static JOptionPane error; // error box


  /**
   * Default constructor.
   */
  public InputController() {
    this.dialogue = new JPanel();
    this.error = new JOptionPane();
  }


  /**
   * Instantiates the view of InputController based on the command line args passed by the user.
   *
   * @param hybridView type of view
   */
  void setView(IHybridView hybridView) {
    this.view = hybridView;
    // instantiates map
    setUpButtonCommands();
  }

  /**
   * Instantiates the map of String to Runnable so that buttons that have action command set to them
   * will dispatch to the appropriate Runnable.
   */
  private void setUpButtonCommands() {
    Map<String, Runnable> buttonActionMap = new HashMap<>();
    ButtonHandler buttonHandler = new ButtonHandler();

    buttonActionMap.put("Play/Pause", new PausePlayAction());
    buttonActionMap.put("Restart", new RestartAction());
    buttonActionMap.put("Toggle loop", new ToggleLoopAction());
    buttonActionMap.put("Set", new SetAction());
    buttonActionMap.put("Export", new ExportAction());

    // instantiates map in ButtonHandler class
    buttonHandler.setButtonHandlerMap(buttonActionMap);
    // adds action listener to the buttons of the view
    this.view.addActionListener(buttonHandler);
  }

  /**
   * Represents the Action event for pausing and playing the view. Pauses if playing, resumes if
   * paused.
   */
  public static class PausePlayAction implements Runnable {

    @Override
    public void run() {
      if (view.getIsPlaying()) {
        view.pause();
      } else {
        view.resume();
      }
    }
  }

  /**
   * Represents the Action event for restarting the view. Effectively resets tick and timer and
   * shape objects.
   */
  public static class RestartAction implements Runnable {

    @Override
    public void run() {
      view.restart();
    }
  }

  /**
   * Represents the Action event for toggling on and off the looping for the view. If on, this
   * action event toggles the loop off, and vice versa.
   */
  public static class ToggleLoopAction implements Runnable {
    @Override
    public void run() {
      view.setLoop();
    }
  }

  /**
   * Represents the Action event for setting the speed of the view animation based on what the user
   * typed in the text field. Ensures the speed is valid.
   */
  public static class SetAction implements Runnable {

    @Override
    public void run() {
      try {
        view.setSpeed(view.getSpeedInput());
      } catch (NumberFormatException e) {
        error.showMessageDialog(dialogue, "Must be a number (0, 1000]",
                "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Represents the Action event for exporting an SVG file form the text field of the view. Exports
   * anything the user types, but requires the user to add the .svg extension for a proper file.
   * Empties text field after user exports file.
   */
  public static class ExportAction implements Runnable {

    @Override
    public void run() {
      view.exportSVG();
    }
  }
}
