import org.junit.Test;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.IController;
import cs3500.animator.view.ViewType;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Tests controller.
 */
public class ControllerTests {

  private IController controller;

  public void initTestData() {
    controller = new AnimatorController();
  }

  /**
   * Tests setting of variables with three args set correctly.
   */
  @Test
  public void testVariableSetting1() {
    initTestData();
    String str = "-if toh-3.txt -iv svg -speed 5 -o out";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
    assertEquals(controller.getOutputFile(), "out");
    assertEquals(controller.getSpeed(), 5.0);
    assertEquals(controller.getViewType(), ViewType.SVG);
  }

  /**
   * Tests setting of variables with two args only.
   */
  @Test
  public void testVariableSetting2() {
    initTestData();
    String str = "-if toh-3.txt -iv text";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
    assertEquals(controller.getOutputFile(), "out");
    assertEquals(controller.getSpeed(), 1.0);
    assertEquals(controller.getViewType(), ViewType.TEXT);
  }

  /**
   * Testing whether no input file throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void noFileInputted() {
    initTestData();
    String str = "-iv svg";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }

  /**
   * Testing null input.
   */
  @Test(expected = NullPointerException.class)
  public void nullInputArgs() {
    initTestData();
    String str = null;
    String[] in = str.split("\\s+");
    controller.parseCommandLine(in);
  }

  /**
   * Testing empty input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void emptyArgs() {
    initTestData();
    String str = "";
    String[] in = str.split("\\s+");
    controller.parseCommandLine(in);
  }

  /**
   * Testing whether missing view type throws as exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void noViewTypeSpecified() {
    initTestData();
    String str = "-if big-bang-big-crunch.txt";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }


  /**
   * throws exception for illegal extra argument.
   */
  @Test(expected = IllegalArgumentException.class)
  public void illegalExtraArg() {
    initTestData();
    String str = "-if big-bang-big-crunch.txt -iv svg -more notgood";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }


  /**
   * Testing whether illegal view throws as exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void illegalView() {
    initTestData();
    String str = "-if big-bang-big-crunch.txt -iv illegalView";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }


  /**
   * Testing whether speed is above limit.
   */
  @Test(expected = IllegalArgumentException.class)
  public void speedToHigh() {
    initTestData();
    String str = "-if big-bang-big-crunch.txt -iv illegalView -speed 1001";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }

  /**
   * Testing exception thrown for negative speed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void negSpeed() {
    initTestData();
    String str = "-if big-bang-big-crunch.txt -iv illegalView -speed -1";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }

  /**
   * Testing legal input into the parser. Text view.
   */
  @Test
  public void goodCommandText() {
    initTestData();

    String str = "-if smalldemo.txt -iv text";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
    assertTrue(controller.getSpeed() == 1);
  }

  /**
   * Testing legal input into the parser. Visual view.
   */
  @Test
  public void goodCommandVisual() {
    initTestData();

    String str = "-if smalldemo.txt -iv visual";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
    assertTrue(controller.getSpeed() == 1);
  }

  /**
   * Testing legal input into the parser. SVG view.
   */
  @Test
  public void goodCommandSVG() {
    initTestData();

    String str = "-if smalldemo.txt -iv svg";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
    assertTrue(controller.getSpeed() == 1.0);
  }


  /**
   * Testing exception thrown when speed is a string (illegal).
   */
  @Test(expected = IllegalArgumentException.class)
  public void speedInputString() {
    initTestData();

    String str = "-if smalldemo.txt -iv svg -speed string";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
  }

  /**
   * Test passes when all 4 inputs are valid.
   */
  @Test
  public void allGoodInput() {
    initTestData();

    String str = "-if smalldemo.txt -iv svg -speed 11 -o hello";
    String[] in = str.split("\\s+");

    controller.parseCommandLine(in);
    assertTrue(controller.getSpeed() == 11.0);
  }


}
