import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.InputController;
import cs3500.animator.view.HybridViewImpl;
import cs3500.animator.view.IHybridView;
import cs3500.animator.view.ViewType;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Tests input controller used to dispatch actions received in the interactive view.
 */
public class InteractiveTests {

  /**
   * Tests that Hybrid View is instantiated properly from animator controller.
   */
  @Test
  public void testHybridViewSetUp() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    assertEquals(acontroller.getViewType(), ViewType.INTERACTIVE);

    // tests speed of view is correct
    assertEquals(hybridView.getSpeedInput(), 20.0);
    // tests that default output file is correct
    assertEquals(hybridView.getSVGFileName(), "fileName.svg");
  }

  /**
   * Tests that the input controller correctly sets up the button action map.
   */
  @Test
  public void testButtonActionMapSetUp() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // replicating setUpButtonCommands method called in InputController
    // checks that map of Runnable is fully instantiated
    Map<String, Runnable> map = new HashMap<>();

    map.put("Play/Pause", new InputController.PausePlayAction());
    map.put("Restart", new InputController.RestartAction());
    map.put("Toggle loop", new InputController.ToggleLoopAction());
    map.put("Set", new InputController.SetAction());
    map.put("Export", new InputController.ExportAction());

    assertTrue(map.containsKey("Play/Pause"));
    assertTrue(map.containsKey("Restart"));
    assertTrue(map.containsKey("Toggle loop"));
    assertTrue(map.containsKey("Set"));
    assertTrue(map.containsKey("Export"));
    assertEquals(map.size(), 5);
  }

  /**
   * Tests pause and resume methods of HybridView as called through Input Controller.
   */
  @Test
  public void testPausePlayAction() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // Pause Play Action from Input Controller
    InputController.PausePlayAction pausePlay = new InputController.PausePlayAction();

    // tests that Hybrid View is playing by default
    assertTrue(hybridView.getIsPlaying());

    // tests that view gets paused with PausePlayAction
    pausePlay.run();
    assertFalse(hybridView.getIsPlaying());
    // tests that view gets resumed with PausePlayAction
    pausePlay.run();
    assertTrue(hybridView.getIsPlaying());
  }

  /**
   * Tests restart method of HybridView as called through Input Controller.
   */
  @Test
  public void testRestartAction() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // Pause Play Action from Input Controller
    InputController.RestartAction restart = new InputController.RestartAction();

    // tests that tick is reset to 0 after restart
    restart.run();
    assertEquals(hybridView.getTick(), 0.0);
    hybridView.pause();
    assertFalse(hybridView.getIsPlaying());
    restart.run();
    // tests that restart resumes the view
    assertTrue(hybridView.getIsPlaying());
  }

  /**
   * Tests toggle loop method of HybridView as called through Input Controller.
   */
  @Test
  public void testToggleLoopAction() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // Toggle Loop Action from Input Controller
    InputController.ToggleLoopAction toggleLoop = new InputController.ToggleLoopAction();

    // Ensures that isLoop is false by default
    assertFalse(hybridView.getIsLoop());
    // toggles loop on
    toggleLoop.run();
    assertTrue(hybridView.getIsLoop());
    // toggles loop off
    toggleLoop.run();
    assertFalse(hybridView.getIsLoop());
  }

  /**
   * Tests set speed method of HybridView as called through Input Controller.
   */
  @Test
  public void testSetAction() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // Set Action from Input Controller
    InputController.SetAction set = new InputController.SetAction();

    // Checks default speed of view
    assertEquals(hybridView.getSpeedInput(), 20.0);
    // user types in 30 in speed input text field
    hybridView.setSpeedInput("30");
    // checks that speed of view has NOT been changed yet
    assertEquals(hybridView.getSpeedInput(), 30.0);
    // tests updating of speed through action listener
    set.run();
    assertEquals(hybridView.getSpeedInput(), 30.0);
  }

  /**
   * Tests export action method of HybridView as called through Input Controller without looping.
   */
  @Test
  public void testExportActionWithoutLoop() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // Export Action from Input Controller
    InputController.ExportAction export = new InputController.ExportAction();

    // ensures looping is off
    assertFalse(hybridView.getIsLoop());

    // checking for current file name
    assertEquals(hybridView.getSVGFileName(), "fileName.svg");

    // checks for update file name
    hybridView.setSVGFileName("newFileName.svg");
    assertEquals(hybridView.getSVGFileName(), "newFileName.svg");
    export.run();

    // tests that the export action exported (smalldemo) to the given file
    try {
      BufferedReader br = new BufferedReader(new FileReader("newFileName.svg"));
      String svgOutput = br.lines().collect(Collectors.joining());

      assertEquals(svgOutput, "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
              "xmlns=\"http://www.w3.org/2000/svg\">  " +
              "  <rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" " +
              "height=\"100.0\" fill=\"rgb(255.0,0.0,0.0)\" visibility=\"visible\" >        " +
              "<animate attributeType=\"xml\" begin=\"0.5s\" dur=\"2.0s\" attributeName=\"x\"" +
              " from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>       " +
              " <animate attributeType=\"xml\" begin=\"0.5s\" dur=\"2.0s\" attributeName=\"y\" " +
              "from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>       " +
              " <animate attributeType=\"xml\" begin=\"2.55s\" dur=\"0.95s\" " +
              "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\"/>     " +
              "   <animate attributeType=\"xml\" begin=\"2.55s\" dur=\"0.95s\"" +
              " attributeName=\"height\" from=\"50.0\" to=\"100.0\" fill=\"freeze\"/>  " +
              "      <animate attributeType=\"xml\" begin=\"3.5s\" dur=\"1.5s\" " +
              "attributeName=\"x\" "
              +
              "from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>    " +
              "    <animate attributeType=\"xml\" begin=\"3.5s\" dur=\"1.5s\" attributeName=\"y\"" +
              " from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>    </rect>  " +
              "  <ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" " +
              "fill=\"rgb(0.0,0.0,255.0)\" visibility=\"visible\" >       " +
              " <animate attributeType=\"xml\" begin=\"1.0s\" dur=\"2.5s\" attributeName=\"cx\" " +
              "from=\"500.0\" to=\"500.0\" fill=\"freeze\"/>       " +
              " <animate attributeType=\"xml\" begin=\"1.0s\" dur=\"2.5s\" attributeName=\"cy\" " +
              "from=\"100.0\" to=\"400.0\" fill=\"freeze\"/>       " +
              " <animate attributeType=\"css\" begin=\"2.5s\" dur=\"1.5s\" attributeName=\"fill\" "
              +
              "from=\"rgb(0.0,0.0,255.0)\" to=\"rgb(0.0,255.0,0.0)\" fill=\"freeze\"/>   " +
              " </ellipse></svg>");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests export action method of HybridView as called through Input Controller with looping.
   */
  @Test
  public void testExportActionWithLoop() {
    AnimatorController acontroller = new AnimatorController();
    String str = "-if smalldemo.txt -iv interactive -speed 20.0";
    String[] in = str.split("\\s+");
    acontroller.parseCommandLine(in);
    IHybridView hybridView = (HybridViewImpl) acontroller.getView();

    // Export Action from Input Controller
    InputController.ExportAction export = new InputController.ExportAction();
    InputController.ToggleLoopAction toggleLoop = new InputController.ToggleLoopAction();

    // looping is off by default
    assertFalse(hybridView.getIsLoop());
    // turn on looping
    toggleLoop.run();
    assertTrue(hybridView.getIsLoop());

    // checking for current file name
    assertEquals(hybridView.getSVGFileName(), "fileName.svg");

    // checks for update file name
    hybridView.setSVGFileName("newLoopFileName.svg");
    assertEquals(hybridView.getSVGFileName(), "newLoopFileName.svg");
    export.run();

    // tests that the export action exported (smalldemo) to the given file
    try {
      BufferedReader br = new BufferedReader(new FileReader("newLoopFileName.svg"));
      String svgOutput = br.lines().collect(Collectors.joining());

      assertEquals(svgOutput, "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
              "xmlns=\"http://www.w3.org/2000/svg\">\n" +
              "    <rect>\n" +
              "        <animate id=\"base\" begin=\"0;base.end\" dur=\"5.0s\" " +
              "attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n" +
              "    </rect>\n" +
              "\n" +
              "    <rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" " +
              "fill=\"rgb(255.0,0.0,0.0)\" visibility=\"visible\" >\n" +
              "\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+0.5s\" dur=\"2.0s\"" +
              " attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+0.5s\" dur=\"2.0s\" " +
              "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+2.55s\" dur=\"0.95s\"" +
              " attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+2.55s\" dur=\"0.95s\"" +
              " attributeName=\"height\" from=\"50.0\" to=\"100.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+3.5s\" dur=\"1.5s\" " +
              "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+3.5s\" dur=\"1.5s\" " +
              "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>\n" +
              "\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"x\" " +
              "to=\"200.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"y\" " +
              "to=\"200.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"width\" " +
              "to=\"50.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"height\" " +
              "to=\"100.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"fill\"" +
              " to=\"(255.0,0.0,0.0)\" fill=\"freeze\" />\n" +
              "\n" +
              "    </rect>\n" +
              "\n" +
              "    <ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" " +
              "fill=\"rgb(0.0,0.0,255.0)\" visibility=\"visible\" >\n" +
              "\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+1.0s\" dur=\"2.5s\" " +
              "attributeName=\"cx\" from=\"500.0\" to=\"500.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+1.0s\" dur=\"2.5s\" " +
              "attributeName=\"cy\" from=\"100.0\" to=\"400.0\" fill=\"freeze\"/>\n" +
              "        <animate attributeType=\"xml\" begin=\"base.begin+2.5s\" dur=\"1.5s\" " +
              "attributeName=\"fill\" from=\"rgb(0.0,0.0,255.0)\" " +
              "to=\"rgb(0.0,255.0,0.0)\" fill=\"freeze\"/>\n" +
              "\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"cx\" " +
              "to=\"500.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"cy\" " +
              "to=\"100.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"rx\" " +
              "to=\"60.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"ry\" " +
              "to=\"30.0\" fill=\"freeze\" />\n" +
              "        <animate begin=\"base.end\" dur=\"1ms\" attributeName=\"fill\" " +
              "to=\"(0.0,0.0,255.0)\" fill=\"freeze\" />\n" +
              "\n" +
              "    </ellipse>\n" +
              "\n" +
              "</svg>\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}