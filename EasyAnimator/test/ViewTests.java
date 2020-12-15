import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.animation.AnimationType;
import cs3500.animator.model.animation.ChangeColor;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.animation.Move;
import cs3500.animator.model.animation.Scale;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.model.util.ColorRGB;
import cs3500.animator.model.util.Dimensions;
import cs3500.animator.model.util.Duration;
import cs3500.animator.model.util.IColor;
import cs3500.animator.model.util.IDimensions;
import cs3500.animator.model.util.IDuration;
import cs3500.animator.model.util.IPosition;
import cs3500.animator.model.util.Position2D;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFactory;
import cs3500.animator.view.SVGViewImpl;
import cs3500.animator.view.TextViewImpl;
import cs3500.animator.view.ViewFactoryImpl;

import static junit.framework.TestCase.assertEquals;

/**
 * READ ME!
 * Duplicate code for setup found in each method because in the
 * Handin JavaStyle, there were errors that our private variables
 * had "Singular field access" only even though we were using them
 * in multiple test methods. To fix that, we copied the code for setup
 * (initialization of the model) in each test method using it.
 */

/**
 * Tests SVG and Text Views.
 */
public class ViewTests {
  /**
   * Initializes model.
   */
  public void setup() {
    IAnimatorModel animatorModel = new AnimatorModelImpl();
    // SHAPE 1
    String rectangleName = "R";
    ShapeType rectangleType = ShapeType.RECT;
    IColor rectangleColor = new ColorRGB(1, 0, 0);
    IPosition rectanglePosition = new Position2D(200, 200);
    IDimensions rectangleDimension = new Dimensions(50, 100);
    IDuration rectangleDuration = new Duration(1, 100);

    IShape rectangle = new Rectangle(rectangleName, rectangleType, rectangleColor,
            rectanglePosition, rectangleDimension, rectangleDuration);

    // SHAPE 2
    String ovalName = "C";
    ShapeType ovalType = ShapeType.ELLIPSE;
    IColor ovalColor = new ColorRGB(0, 0, 1);
    IPosition ovalPosition = new Position2D(500, 100);
    IDimensions ovalDimension = new Dimensions(60, 30);
    IDuration ovalDuration = new Duration(6, 100);

    IShape oval = new Oval(ovalName, ovalType, ovalColor,
            ovalPosition, ovalDimension, ovalDuration);

    // ANIMATION 1
    AnimationType animation1Type = AnimationType.MOVE;
    IPosition animation1PositionI = new Position2D(200, 200);
    IPosition animation1PositionF = new Position2D(300, 300);
    IDuration animation1Duration = new Duration(10, 50);

    IAnimation animation1 = new Move(animation1Type, rectangle, animation1Duration,
            animation1PositionI, animation1PositionF);

    // ANIMATION 2
    AnimationType animation2Type = AnimationType.MOVE;
    IDuration animation2Duration = new Duration(20, 70);
    IPosition animation2PositionI = new Position2D(500, 100);
    IPosition animation2PositionF = new Position2D(500, 400);

    IAnimation animation2 = new Move(animation2Type, oval, animation2Duration,
            animation2PositionI, animation2PositionF);

    // ANIMATION 3
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(50, 80);
    IColor animation3ColorI = new ColorRGB(0, 0, 1);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    IAnimation animation3 = new ChangeColor(animation3Type, oval,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 4
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(70, 100);
    IPosition animation4PositionI = new Position2D(300, 300);
    IPosition animation4PositionF = new Position2D(200, 200);

    IAnimation animation4 = new Move(animation4Type, rectangle, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 5
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(51, 70);
    IDimensions animation5dimensionI = new Dimensions(50, 100);
    IDimensions animation5dimensionF = new Dimensions(25, 100);

    IAnimation animation5 = new Scale(animation5Type, rectangle,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    // adding shapes
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    // adding animations
    animatorModel.addAnimation(animation1);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation4);
    animatorModel.addAnimation(animation5);
  }

  /**
   * Tests the string produced by running SVG view on an AnimatorModelImpl.
   */
  @Test
  public void testSVGGetViewStringOrdered() {
    IAnimatorModel animatorModel = new AnimatorModelImpl();
    // SHAPE 1
    String rectangleName = "R";
    ShapeType rectangleType = ShapeType.RECT;
    IColor rectangleColor = new ColorRGB(1, 0, 0);
    IPosition rectanglePosition = new Position2D(200, 200);
    IDimensions rectangleDimension = new Dimensions(50, 100);
    IDuration rectangleDuration = new Duration(1, 100);

    IShape rectangle = new Rectangle(rectangleName, rectangleType, rectangleColor,
            rectanglePosition, rectangleDimension, rectangleDuration);

    // SHAPE 2
    String ovalName = "C";
    ShapeType ovalType = ShapeType.ELLIPSE;
    IColor ovalColor = new ColorRGB(0, 0, 1);
    IPosition ovalPosition = new Position2D(500, 100);
    IDimensions ovalDimension = new Dimensions(60, 30);
    IDuration ovalDuration = new Duration(6, 100);

    IShape oval = new Oval(ovalName, ovalType, ovalColor,
            ovalPosition, ovalDimension, ovalDuration);

    // ANIMATION 1
    AnimationType animation1Type = AnimationType.MOVE;
    IPosition animation1PositionI = new Position2D(200, 200);
    IPosition animation1PositionF = new Position2D(300, 300);
    IDuration animation1Duration = new Duration(10, 50);

    IAnimation animation1 = new Move(animation1Type, rectangle, animation1Duration,
            animation1PositionI, animation1PositionF);

    // ANIMATION 2
    AnimationType animation2Type = AnimationType.MOVE;
    IDuration animation2Duration = new Duration(20, 70);
    IPosition animation2PositionI = new Position2D(500, 100);
    IPosition animation2PositionF = new Position2D(500, 400);

    IAnimation animation2 = new Move(animation2Type, oval, animation2Duration,
            animation2PositionI, animation2PositionF);

    // ANIMATION 3
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(50, 80);
    IColor animation3ColorI = new ColorRGB(0, 0, 1);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    IAnimation animation3 = new ChangeColor(animation3Type, oval,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 4
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(70, 100);
    IPosition animation4PositionI = new Position2D(300, 300);
    IPosition animation4PositionF = new Position2D(200, 200);

    IAnimation animation4 = new Move(animation4Type, rectangle, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 5
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(51, 70);
    IDimensions animation5dimensionI = new Dimensions(50, 100);
    IDimensions animation5dimensionF = new Dimensions(25, 100);

    IAnimation animation5 = new Scale(animation5Type, rectangle,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    // adding shapes
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    // adding animations
    animatorModel.addAnimation(animation1);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation4);
    animatorModel.addAnimation(animation5);
    String s = "<svg width=\"1000\" height=\"1000\" version=\"1.1\" xmlns=" +
            "\"http://www.w3.org/2000/svg\">\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"" +
            "100.0\" fill=\"rgb(255.0,0.0,0.0)\">\n" +
            "        <animate begin=\"10.0s\" dur=\"40.0s\" attributeName=\"x\" from=\"" +
            "200.0\" to=\"300.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"10.0s\" dur=\"40.0s\" attributeName=\"y\" from=\"" +
            "200.0\" to=\"300.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"51.0s\" dur=\"19.0s\" attributeName=\"width\" " +
            "from=\"50.0\" to=\"25.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"51.0s\" dur=\"19.0s\" attributeName=\"height\" " +
            "from=\"100.0\" to=\"100.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"70.0s\" dur=\"30.0s\" attributeName=\"x\" from=\"" +
            "300.0\" to=\"200.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"70.0s\" dur=\"30.0s\" attributeName=\"y\" from=\"" +
            "300.0\" to=\"200.0\" fill=\"freeze\"/>\n" +
            "    </rect>\n" +
            "    <ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" " +
            "fill=\"rgb(0.0,0.0,255.0)\">\n" +
            "        <animate begin=\"20.0s\" dur=\"50.0s\" attributeName=\"cx\" " +
            "from=\"500.0\" to=\"500.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"20.0s\" dur=\"50.0s\" attributeName=\"cy\"" +
            " from=\"100.0\" to=\"400.0\" fill=\"freeze\"/>\n" +
            "        <animate begin=\"50.0s\" dur=\"30.0s\" attributeName=\"fill\" " +
            "from=\"rgb(0.0,0.0,255.0)\" to=\"rgb(0.0,255.0,0.0)\" fill=\"freeze\"/>\n" +
            "    </ellipse>\n" +
            "</svg>";
    IView view = new SVGViewImpl(animatorModel, 1, "out");
    assertEquals(s, view.getViewString());
  }

  /**
   * Tests the string produced by running Text view on an AnimatorModelImpl.
   */
  @Test
  public void testTextView() {
    IAnimatorModel animatorModel = new AnimatorModelImpl();
    // SHAPE 1
    String rectangleName = "R";
    ShapeType rectangleType = ShapeType.RECT;
    IColor rectangleColor = new ColorRGB(1, 0, 0);
    IPosition rectanglePosition = new Position2D(200, 200);
    IDimensions rectangleDimension = new Dimensions(50, 100);
    IDuration rectangleDuration = new Duration(1, 100);

    IShape rectangle = new Rectangle(rectangleName, rectangleType, rectangleColor,
            rectanglePosition, rectangleDimension, rectangleDuration);

    // SHAPE 2
    String ovalName = "C";
    ShapeType ovalType = ShapeType.ELLIPSE;
    IColor ovalColor = new ColorRGB(0, 0, 1);
    IPosition ovalPosition = new Position2D(500, 100);
    IDimensions ovalDimension = new Dimensions(60, 30);
    IDuration ovalDuration = new Duration(6, 100);

    IShape oval = new Oval(ovalName, ovalType, ovalColor,
            ovalPosition, ovalDimension, ovalDuration);

    // ANIMATION 1
    AnimationType animation1Type = AnimationType.MOVE;
    IPosition animation1PositionI = new Position2D(200, 200);
    IPosition animation1PositionF = new Position2D(300, 300);
    IDuration animation1Duration = new Duration(10, 50);

    IAnimation animation1 = new Move(animation1Type, rectangle, animation1Duration,
            animation1PositionI, animation1PositionF);

    // ANIMATION 2
    AnimationType animation2Type = AnimationType.MOVE;
    IDuration animation2Duration = new Duration(20, 70);
    IPosition animation2PositionI = new Position2D(500, 100);
    IPosition animation2PositionF = new Position2D(500, 400);

    IAnimation animation2 = new Move(animation2Type, oval, animation2Duration,
            animation2PositionI, animation2PositionF);

    // ANIMATION 3
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(50, 80);
    IColor animation3ColorI = new ColorRGB(0, 0, 1);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    IAnimation animation3 = new ChangeColor(animation3Type, oval,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 4
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(70, 100);
    IPosition animation4PositionI = new Position2D(300, 300);
    IPosition animation4PositionF = new Position2D(200, 200);

    IAnimation animation4 = new Move(animation4Type, rectangle, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 5
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(51, 70);
    IDimensions animation5dimensionI = new Dimensions(50, 100);
    IDimensions animation5dimensionF = new Dimensions(25, 100);

    IAnimation animation5 = new Scale(animation5Type, rectangle,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    // adding shapes
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    // adding animations
    animatorModel.addAnimation(animation1);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation4);
    animatorModel.addAnimation(animation5);

    String s = "Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (255.0,0.0,0.0)\n" +
            "Appears at t=1.0\n" +
            "Disappears at t=100.0\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,255.0)\n" +
            "Appears at t=6.0\n" +
            "Disappears at t=100.0\n" +
            "\n" +
            "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10.0 to t=50.0\n" +
            "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20.0 to t=70.0\n" +
            "Shape C changes color from (0.0,0.0,255.0) to (0.0,255.0,0.0) " +
            "from t=50.0 to t=80.0\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, " +
            "Height: 100.0 from t=51.0 to t=70.0\n" +
            "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70.0 to t=100.0";

    IView view = new TextViewImpl(animatorModel, 1, "out");

    assertEquals(s, view.getViewString());
  }

  /**
   * Tests -o "filename.svg" configuration of view where running the SVG view writes to a file. Uses
   * buffered reader to test output to file.
   */
  @Test
  public void testSVGRunFileOutput() {
    IAnimatorModel animatorModel = new AnimatorModelImpl();
    // SHAPE 1
    String rectangleName = "R";
    ShapeType rectangleType = ShapeType.RECT;
    IColor rectangleColor = new ColorRGB(1, 0, 0);
    IPosition rectanglePosition = new Position2D(200, 200);
    IDimensions rectangleDimension = new Dimensions(50, 100);
    IDuration rectangleDuration = new Duration(1, 100);

    IShape rectangle = new Rectangle(rectangleName, rectangleType, rectangleColor,
            rectanglePosition, rectangleDimension, rectangleDuration);

    // SHAPE 2
    String ovalName = "C";
    ShapeType ovalType = ShapeType.ELLIPSE;
    IColor ovalColor = new ColorRGB(0, 0, 1);
    IPosition ovalPosition = new Position2D(500, 100);
    IDimensions ovalDimension = new Dimensions(60, 30);
    IDuration ovalDuration = new Duration(6, 100);

    IShape oval = new Oval(ovalName, ovalType, ovalColor,
            ovalPosition, ovalDimension, ovalDuration);

    // ANIMATION 1
    AnimationType animation1Type = AnimationType.MOVE;
    IPosition animation1PositionI = new Position2D(200, 200);
    IPosition animation1PositionF = new Position2D(300, 300);
    IDuration animation1Duration = new Duration(10, 50);

    IAnimation animation1 = new Move(animation1Type, rectangle, animation1Duration,
            animation1PositionI, animation1PositionF);

    // ANIMATION 2
    AnimationType animation2Type = AnimationType.MOVE;
    IDuration animation2Duration = new Duration(20, 70);
    IPosition animation2PositionI = new Position2D(500, 100);
    IPosition animation2PositionF = new Position2D(500, 400);

    IAnimation animation2 = new Move(animation2Type, oval, animation2Duration,
            animation2PositionI, animation2PositionF);

    // ANIMATION 3
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(50, 80);
    IColor animation3ColorI = new ColorRGB(0, 0, 1);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    IAnimation animation3 = new ChangeColor(animation3Type, oval,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 4
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(70, 100);
    IPosition animation4PositionI = new Position2D(300, 300);
    IPosition animation4PositionF = new Position2D(200, 200);

    IAnimation animation4 = new Move(animation4Type, rectangle, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 5
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(51, 70);
    IDimensions animation5dimensionI = new Dimensions(50, 100);
    IDimensions animation5dimensionF = new Dimensions(25, 100);

    IAnimation animation5 = new Scale(animation5Type, rectangle,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    // adding shapes
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    // adding animations
    animatorModel.addAnimation(animation1);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation4);
    animatorModel.addAnimation(animation5);
    String outputFile = "testOutput.svg";
    SVGViewImpl svg = new SVGViewImpl(animatorModel, 1, outputFile);
    svg.run();
    try {
      BufferedReader br = new BufferedReader(new FileReader(outputFile));
      String svgOutput = br.lines().collect(Collectors.joining());

      // raw file output; Buffered Reader does not process the \n character
      assertEquals(svgOutput, "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
              "xmlns=\"http://www.w3.org/2000/svg\">  " +
              "  <rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" " +
              "height=\"100.0\" fill=\"rgb(255.0,0.0,0.0)\">        " +
              "<animate begin=\"10.0s\" dur=\"40.0s\" attributeName=\"x\"" +
              " from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>       " +
              " <animate begin=\"10.0s\" dur=\"40.0s\" attributeName=\"y\" " +
              "from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>       " +
              " <animate begin=\"51.0s\" dur=\"19.0s\" " +
              "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\"/>     " +
              "   <animate begin=\"51.0s\" dur=\"19.0s\"" +
              " attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\"/>  " +
              "      <animate begin=\"70.0s\" dur=\"30.0s\" attributeName=\"x\" " +
              "from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>    " +
              "    <animate begin=\"70.0s\" dur=\"30.0s\" attributeName=\"y\"" +
              " from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>    </rect>  " +
              "  <ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" " +
              "fill=\"rgb(0.0,0.0,255.0)\">       " +
              " <animate begin=\"20.0s\" dur=\"50.0s\" attributeName=\"cx\" " +
              "from=\"500.0\" to=\"500.0\" fill=\"freeze\"/>       " +
              " <animate begin=\"20.0s\" dur=\"50.0s\" attributeName=\"cy\" " +
              "from=\"100.0\" to=\"400.0\" fill=\"freeze\"/>       " +
              " <animate begin=\"50.0s\" dur=\"30.0s\" attributeName=\"fill\" " +
              "from=\"rgb(0.0,0.0,255.0)\" to=\"rgb(0.0,255.0,0.0)\" fill=\"freeze\"/>   " +
              " </ellipse></svg>");

      // This test adds new lines at each > tag as file reader does not process \n characters
      assertEquals(svg.getViewString(), "<svg width=\"1000\" height=\"1000\" version=\"1.1\" " +
              "xmlns=\"http://www.w3.org/2000/svg\">\n  " +
              "  <rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" " +
              "height=\"100.0\" fill=\"rgb(255.0,0.0,0.0)\">\n        " +
              "<animate begin=\"10.0s\" dur=\"40.0s\" attributeName=\"x\"" +
              " from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>\n       " +
              " <animate begin=\"10.0s\" dur=\"40.0s\" attributeName=\"y\" " +
              "from=\"200.0\" to=\"300.0\" fill=\"freeze\"/>\n       " +
              " <animate begin=\"51.0s\" dur=\"19.0s\" " +
              "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\"/>\n     " +
              "   <animate begin=\"51.0s\" dur=\"19.0s\"" +
              " attributeName=\"height\" from=\"100.0\" to=\"100.0\" fill=\"freeze\"/>\n  " +
              "      <animate begin=\"70.0s\" dur=\"30.0s\" attributeName=\"x\" " +
              "from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>\n    " +
              "    <animate begin=\"70.0s\" dur=\"30.0s\" attributeName=\"y\"" +
              " from=\"300.0\" to=\"200.0\" fill=\"freeze\"/>\n    </rect>\n  " +
              "  <ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" " +
              "fill=\"rgb(0.0,0.0,255.0)\">\n       " +
              " <animate begin=\"20.0s\" dur=\"50.0s\" attributeName=\"cx\" " +
              "from=\"500.0\" to=\"500.0\" fill=\"freeze\"/>\n       " +
              " <animate begin=\"20.0s\" dur=\"50.0s\" attributeName=\"cy\" " +
              "from=\"100.0\" to=\"400.0\" fill=\"freeze\"/>\n       " +
              " <animate begin=\"50.0s\" dur=\"30.0s\" attributeName=\"fill\" " +
              "from=\"rgb(0.0,0.0,255.0)\" to=\"rgb(0.0,255.0,0.0)\" fill=\"freeze\"/>\n   " +
              " </ellipse>\n</svg>");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests -o "filename.txt" configuration of view where running the text view writes to a file.
   * Uses buffered reader to test output to file.
   */
  @Test
  public void testTextRunFileOutput() {
    IAnimatorModel animatorModel = new AnimatorModelImpl();
    // SHAPE 1
    String rectangleName = "R";
    ShapeType rectangleType = ShapeType.RECT;
    IColor rectangleColor = new ColorRGB(1, 0, 0);
    IPosition rectanglePosition = new Position2D(200, 200);
    IDimensions rectangleDimension = new Dimensions(50, 100);
    IDuration rectangleDuration = new Duration(1, 100);

    IShape rectangle = new Rectangle(rectangleName, rectangleType, rectangleColor,
            rectanglePosition, rectangleDimension, rectangleDuration);

    // SHAPE 2
    String ovalName = "C";
    ShapeType ovalType = ShapeType.ELLIPSE;
    IColor ovalColor = new ColorRGB(0, 0, 1);
    IPosition ovalPosition = new Position2D(500, 100);
    IDimensions ovalDimension = new Dimensions(60, 30);
    IDuration ovalDuration = new Duration(6, 100);

    IShape oval = new Oval(ovalName, ovalType, ovalColor,
            ovalPosition, ovalDimension, ovalDuration);

    // ANIMATION 1
    AnimationType animation1Type = AnimationType.MOVE;
    IPosition animation1PositionI = new Position2D(200, 200);
    IPosition animation1PositionF = new Position2D(300, 300);
    IDuration animation1Duration = new Duration(10, 50);

    IAnimation animation1 = new Move(animation1Type, rectangle, animation1Duration,
            animation1PositionI, animation1PositionF);

    // ANIMATION 2
    AnimationType animation2Type = AnimationType.MOVE;
    IDuration animation2Duration = new Duration(20, 70);
    IPosition animation2PositionI = new Position2D(500, 100);
    IPosition animation2PositionF = new Position2D(500, 400);

    IAnimation animation2 = new Move(animation2Type, oval, animation2Duration,
            animation2PositionI, animation2PositionF);

    // ANIMATION 3
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(50, 80);
    IColor animation3ColorI = new ColorRGB(0, 0, 1);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    IAnimation animation3 = new ChangeColor(animation3Type, oval,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 4
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(70, 100);
    IPosition animation4PositionI = new Position2D(300, 300);
    IPosition animation4PositionF = new Position2D(200, 200);

    IAnimation animation4 = new Move(animation4Type, rectangle, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 5
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(51, 70);
    IDimensions animation5dimensionI = new Dimensions(50, 100);
    IDimensions animation5dimensionF = new Dimensions(25, 100);

    IAnimation animation5 = new Scale(animation5Type, rectangle,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    // adding shapes
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    // adding animations
    animatorModel.addAnimation(animation1);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation4);
    animatorModel.addAnimation(animation5);
    String outputFile = "testOutput.txt";
    TextViewImpl tv = new TextViewImpl(animatorModel, 1, outputFile);
    tv.run();
    try {
      BufferedReader br = new BufferedReader(new FileReader(outputFile));
      String textOutput = br.lines().collect(Collectors.joining());

      // raw file output; Buffered Reader does not process the \n character
      assertEquals(textOutput, "Shapes:" +
              "Name: R" +
              "Type: rectangle" +
              "Corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (255.0,0.0,0.0)" +
              "Appears at t=1.0" +
              "Disappears at t=100.0" +
              "Name: C" +
              "Type: oval" +
              "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,255.0)" +
              "Appears at t=6.0" +
              "Disappears at t=100.0" +
              "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10.0 to t=50.0" +
              "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20.0 to t=70.0" +
              "Shape C changes color from (0.0,0.0,255.0) to (0.0,255.0,0.0) " +
              "from t=50.0 to t=80.0" +
              "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0," +
              " Height: 100.0 from t=51.0 to t=70.0" +
              "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70.0 to t=100.0");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests calling makeVisible(), a method that is not supported (yet inherited) by SVGView.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testMakeVisibleCallSVG() {
    SVGViewImpl svg = new SVGViewImpl(new AnimatorModelImpl(), 1, "out");
    svg.makeVisible();
  }


  /**
   * Tests calling makeVisible(), a method that is not supported (yet inherited) by TextView.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testMakeVisibleCallText() {
    TextViewImpl t = new TextViewImpl(new AnimatorModelImpl(), 1, "out");
    t.makeVisible();
  }

  /**
   * Tests calling refresh(), a method that is not supported (yet inherited) by SVG.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testRefreshSVG() {
    SVGViewImpl svg = new SVGViewImpl(new AnimatorModelImpl(), 1, "out");
    svg.refresh();
  }

  /**
   * Tests calling refresh(), a method that is not supported (yet inherited) by SVG.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testRefreshText() {
    TextViewImpl t = new TextViewImpl(new AnimatorModelImpl(), 1, "out");
    t.refresh();
  }

  /**
   * Tests ViewFactory creation of SVG.
   */
  @Test
  public void testViewFactorySVG() {
    setup();
    IViewFactory factory = new ViewFactoryImpl(new AnimatorModelImpl(), 1, "out");
    IView view = factory.createView("svg");
    assertEquals(view.getClass().toString(), "class cs3500.animator.view.SVGViewImpl");
  }

  /**
   * Tests ViewFactory creation of Text.
   */
  @Test
  public void testViewFactoryText() {
    setup();
    IViewFactory factory = new ViewFactoryImpl(new AnimatorModelImpl(), 1, "out");
    IView view = factory.createView("text");
    assertEquals(view.getClass().toString(), "class cs3500.animator.view.TextViewImpl");
  }

  /**
   * Tests ViewFactory creation of Visual.
   */
  @Test
  public void testViewFactoryVisual() {
    setup();
    IViewFactory factory = new ViewFactoryImpl(new AnimatorModelImpl(), 1, "out");
    IView view = factory.createView("visual");
    assertEquals(view.getClass().toString(), "class cs3500.animator.view.VisualViewImpl");
  }

  /**
   * Tests ViewFactory creation of ILLEGAL view type.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testViewFactoryIllegal() {
    setup();
    IViewFactory factory = new ViewFactoryImpl(new AnimatorModelImpl(), 1, "out");
    IView view = factory.createView("nonexistent");
  }
}