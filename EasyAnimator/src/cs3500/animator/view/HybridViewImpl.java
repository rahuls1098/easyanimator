package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.model.IAnimatorModel;

/**
 * Represents the Interactive View. Adds on to the Visual View with methods to pause, play, restart,
 * loop, change speed, and export the animation. Error messages cover a flexible range of user
 * input. Makes interactive buttons active through the addition of action listeners.
 */
public class HybridViewImpl extends AView implements IHybridView {

  final int WIDTH = 600;
  final int HEIGHT = 600;
  private boolean isPlaying; // is animation playing
  final JLabel indicateSpeed = new JLabel("Speed:"); // header
  private JButton set; // sets speed
  private JButton pausePlay; // pauses/plays animation
  private JButton restart; // restarts animation
  private JCheckBox toggleLoop; // loops/unloops animation
  private JTextField speedInput; // user input area for speed
  private JTextField specifyFile; // user input area for file name
  final JLabel fileName = new JLabel("File name:"); // header
  private JButton export; // exports animation
  private Timer timer; // tracks time
  private JPanel dialogue; //  message window
  private JOptionPane pane; // message box

  /**
   * Constructs a HybridViewImpl. Sets up animation starting conditions such as no looping but
   * playing by default. Initializes window for animation and timer. Adds the buttons necessary for
   * interaction and assigns action commands representing them.
   *
   * @param model  model of animation
   * @param speed  speed of animation
   * @param output output method of view
   */
  public HybridViewImpl(IAnimatorModel model, double speed, String output) {
    super(model, speed, output);
    this.viewType = ViewType.INTERACTIVE;
    this.isPlaying = true;
    this.isLoop = false;
    this.tick = 0;
    this.speed = speed;
    this.dialogue = new JPanel();
    this.pane = new JOptionPane();
    this.rate = (int) (1000.0 / speed);
    timer = new Timer(rate, this);
    timer.start();
    this.setTitle("Interactive Animator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    // border layout with drawing panel
    this.setLayout(new BorderLayout());
    aniPanel = new AnimationPanel();
    aniPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.add(aniPanel, BorderLayout.CENTER);

    // button panel
    JPanel buttonPanelTop = new JPanel();
    JPanel buttonPanelBottom = new JPanel();
    buttonPanelTop.setLayout(new FlowLayout());
    buttonPanelBottom.setLayout(new BorderLayout());
    this.add(buttonPanelTop, BorderLayout.NORTH);
    this.add(buttonPanelBottom, BorderLayout.SOUTH);


    // speed
    this.speedInput = new JTextField(Double.toString(this.speed), 3);
    this.set = new JButton("Set");
    set.setActionCommand("Set");
    buttonPanelTop.add(indicateSpeed);
    buttonPanelTop.add(speedInput);
    buttonPanelTop.add(set);

    // pause, play, restart, loop
    this.pausePlay = new JButton("Pause");
    pausePlay.setActionCommand("Play/Pause");
    this.restart = new JButton("Restart");
    restart.setActionCommand("Restart");
    this.toggleLoop = new JCheckBox("Loop");
    toggleLoop.setActionCommand("Toggle loop");
    buttonPanelTop.add(pausePlay);
    buttonPanelTop.add(restart);
    buttonPanelTop.add(toggleLoop);

    // export to SVG
    this.specifyFile = new JTextField("fileName.svg", 8);
    this.export = new JButton("Export");
    export.setActionCommand("Export");
    buttonPanelTop.add(fileName);
    buttonPanelTop.add(specifyFile);
    buttonPanelTop.add(export);

    // scrubbing
    buttonPanelBottom.add(slider);

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
  public void addActionListener(ActionListener listener) {
    pausePlay.addActionListener(listener);
    restart.addActionListener(listener);
    toggleLoop.addActionListener(listener);
    set.addActionListener(listener);
    export.addActionListener(listener);
  }

  @Override
  public void restart() {
    aniPanel.resetShapeValues();
    this.resetAnimationShapes(0);
    this.tick = 0;
    timer.restart();
    if (!isPlaying) {
      this.resume();
    }
  }

  @Override
  public void pause() {
    timer.stop();
    this.isPlaying = false;
    this.pausePlay.setText("Resume");
  }

  @Override
  public void resume() {
    timer.start();
    this.isPlaying = true;
    this.pausePlay.setText("Pause");
  }

  @Override
  public void setLoop() {
    isLoop = !isLoop;
  }

  @Override
  public void setSpeed(double newSpeed) {
    if (newSpeed < 0 || newSpeed > 1000) {
      pane.showMessageDialog(dialogue, "Valid range for speed: (0, 1000]",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
    this.speed = newSpeed;
    rate = (int) (1000.0 / speed);
    timer.setDelay(rate);
  }

  @Override
  public void setSVGFileName(String fileName) {
    this.specifyFile.setText(fileName);
  }

  @Override
  public void exportSVG() {
    // modify looping properties of the SVG
    String fileName = this.getSVGFileName();
    pane.showMessageDialog(dialogue, fileName + " was exported successfully.",
            "Export", JOptionPane.INFORMATION_MESSAGE);
    SVGViewImpl s = new SVGViewImpl(model, speed, fileName, isLoop);
    s.run();
  }

  @Override
  public double getSpeedInput() {
    return Double.parseDouble(speedInput.getText());
  }

  @Override
  public String getSVGFileName() {
    String file = specifyFile.getText();
    specifyFile.setText("");
    return file;
  }

  @Override
  public double getTick() {
    return tick;
  }

  @Override
  public boolean getIsLoop() {
    return isLoop;
  }

  @Override
  public void setSpeedInput(String speed) {
    this.speedInput.setText(speed);
  }

  @Override
  public boolean getIsPlaying() {
    return isPlaying;
  }

  @Override
  public void run() {
    this.makeVisible();
  }

  @Override
  public String getViewString() {
    throw new UnsupportedOperationException("This method is suppressed by InteractiveView.");
  }
}

