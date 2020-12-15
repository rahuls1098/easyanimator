package cs3500.animator.controller;


import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.readbuild.AnimationFileReader;
import cs3500.animator.view.IHybridView;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFactory;
import cs3500.animator.view.ViewFactoryImpl;
import cs3500.animator.view.ViewType;

/**
 * Implementation of the IController interface that parses command line input and creates a model
 * and view based on the specifications.
 */
public class AnimatorController implements IController {

  private JPanel dialogue; // error message window
  private IView view; // view rendered
  private ViewType viewType; // type of view rendered
  private double speed = 1.0; // speed of animation
  private String animationFile = ""; // name of input file
  private String outputFile = "out"; // method of viewing
  private JOptionPane error; // error box
  private InputController inputController;

  /**
   * Constructs view based on output of the view factory, based on input command line arg.
   */
  public AnimatorController() {
    this.dialogue = new JPanel();
    dialogue.setPreferredSize(new Dimension(50, 200));
    this.error = new JOptionPane();
    this.viewType = null;
    this.view = null;
    this.inputController = new InputController();
  }

  @Override
  public void parseCommandLine(String[] args) {

    boolean containsIf = Stream.of(args).anyMatch(x -> x.equals("-if"));
    boolean containsIv = Stream.of(args).anyMatch(x -> x.equals("-iv"));

    if (containsIf && containsIv) {
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case "-if":
            animationFile = args[i + 1];
            i = i + 1;
            break;
          case "-iv":
            try {
              viewType = ViewType.valueOf(args[i + 1].toUpperCase());
            } catch (IllegalArgumentException e) {
              error.showMessageDialog(dialogue, "View type does not exist",
                      "Error", JOptionPane.ERROR_MESSAGE);
              throw new IllegalArgumentException("view type does not exist");
            }
            i = i + 1;
            break;
          case "-o":
            if (args[args.length - 1].equals("-o")) {
              break;
            }
            outputFile = args[i + 1];
            i = i + 1;
            break;
          case "-speed":
            try {
              if (args[args.length - 1].equals("-speed")) {
                break;
              }
              speed = Double.parseDouble(args[i + 1]);
            } catch (NumberFormatException e) {
              error.showMessageDialog(dialogue, "Valid range for speed: (0, 1000]",
                      "Error", JOptionPane.ERROR_MESSAGE);
              throw new IllegalArgumentException("speed not a number");
            }
            if (speed <= 0 || speed > 1000) {
              error.showMessageDialog(dialogue, "Valid range for speed: (0, 1000]",
                      "Error", JOptionPane.ERROR_MESSAGE);
              throw new IllegalArgumentException("Valid range for speed: (0, 1000]");
            }
            i = i + 1;
            break;
          default:
            error.showMessageDialog(dialogue, "Incorrect commands",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Incorrect commands");
        }
      }
    } else {
      error.showMessageDialog(dialogue, "-if or -iv doesn't exist.",
              "Error", JOptionPane.ERROR_MESSAGE);
      throw new IllegalArgumentException("-if or -iv doesn't exist");
    }

    // Initializes File Reading and model builder
    AnimationFileReader reader = new AnimationFileReader();
    AnimatorModelImpl.Builder builder = new AnimatorModelImpl.Builder();

    // reads input file
    try {
      reader.readFile(animationFile, builder);
    } catch (FileNotFoundException e) {
      error.showMessageDialog(dialogue, "File not found.",
              "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Builds model
    IAnimatorModel model = builder.build();

    // Initializes IViewFactory
    IViewFactory viewFactory = new ViewFactoryImpl(model, speed, outputFile);
    // Sets view to appropriate view returned by ViewFactory and sends to InputController
    try {
      this.view = viewFactory.createView(viewType.toString().toLowerCase());
      if (viewType.equals(ViewType.INTERACTIVE)) {
        inputController.setView((IHybridView) view);
      }
    } catch (IllegalArgumentException e) {
      error.showMessageDialog(dialogue, "Invalid view type.",
              "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void start() {
    view.run();
  }

  @Override
  public ViewType getViewType() {
    return viewType;
  }

  @Override
  public IView getView() {
    return view;
  }

  @Override
  public double getSpeed() {
    return speed;
  }

  @Override
  public String getOutputFile() {
    return outputFile;
  }
}