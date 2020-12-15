package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Represents the action commands held by the buttons in the interactive view. Implements a map that
 * maps the name of an action to the ActionEvent to be called on.
 */
public class ButtonHandler implements ActionListener {

  private Map<String, Runnable> buttonActionMap; // action name : action event

  /**
   * Empty default constructor.
   */
  public ButtonHandler() {
    // empty
  }

  /**
   * Instantiates the map representing Action Commands in string format mapped to their runnable
   * actions.
   *
   * @param actionMap runnables
   */
  void setButtonHandlerMap(Map<String, Runnable> actionMap) {
    buttonActionMap = actionMap;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonActionMap.containsKey(e.getActionCommand())) {
      buttonActionMap.get(e.getActionCommand()).run();
    }
  }
}
