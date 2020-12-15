import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import static junit.framework.TestCase.assertEquals;

/**
 * Test suite for the IAnimatorModel.
 */
public class IAnimatorModelTest {


  private IAnimatorModel animatorModel;
  private IShape square;

  private IShape rectangle;
  private IShape oval;

  private IAnimation animation1;
  private IAnimation animation2;
  private IAnimation animation3;
  private IAnimation animation4;
  private IAnimation animation5;


  /**
   * sets up an initial shape and animation for tests.
   */
  @Before
  public void initSetup() {
    this.animatorModel = new AnimatorModelImpl();

    // Initializing a Shape
    String squareName = "S";
    ShapeType squareType = ShapeType.RECT;
    IColor squareColor = new ColorRGB(1, 0, 0);
    IPosition squarePosition = new Position2D(200, 200);
    IDimensions squareDimension = new Dimensions(50, 50);
    IDuration squareDuration = new Duration(1, 100);

    this.square = new Rectangle(squareName, squareType, squareColor,
            squarePosition, squareDimension, squareDuration);

    // Initializing an Animation
    AnimationType animationType = AnimationType.MOVE;
    IPosition animationPositionI = new Position2D(200, 200);
    IPosition animationPositionF = new Position2D(300, 300);
    IDuration animationDuration = new Duration(10, 50);

    IAnimation animationMove = new Move(animationType, square, animationDuration,
            animationPositionI, animationPositionF);
  }

  /**
   * initializes the animation from the problem statement.
   */
  public void initializeAnimation() {

    /* Informal description
     * --------------------
     * Create red rectangle R with corner at (200,200), width 50 and height 100
     * Create blue oval C with center at (500,100), radius 60 and 30

     * R appears at time t=1 and disappears at time t=100
     * C appears at time t=6 and disappears at time t=100

     * R moves from (200,200) to (300,300) from time t=10 to t=50
     * C moves from (500,100) to (500,400) from time t=20 to t=70
     * C changes from blue to green from time t=50 to t=80
     * R moves from (300,300) to (200,200) from time t=70 to t=100
     * R changes width from 50 to 25 from time t=51 to t=70
     *
     */

    // SHAPE 1
    String rectangleName = "R";
    ShapeType rectangleType = ShapeType.RECT;
    IColor rectangleColor = new ColorRGB(1, 0, 0);
    IPosition rectanglePosition = new Position2D(200, 200);
    IDimensions rectangleDimension = new Dimensions(50, 100);
    IDuration rectangleDuration = new Duration(1, 100);

    this.rectangle = new Rectangle(rectangleName, rectangleType, rectangleColor,
            rectanglePosition, rectangleDimension, rectangleDuration);

    // SHAPE 2
    String ovalName = "C";
    ShapeType ovalType = ShapeType.ELLIPSE;
    IColor ovalColor = new ColorRGB(0, 0, 1);
    IPosition ovalPosition = new Position2D(500, 100);
    IDimensions ovalDimension = new Dimensions(60, 30);
    IDuration ovalDuration = new Duration(6, 100);

    this.oval = new Oval(ovalName, ovalType, ovalColor,
            ovalPosition, ovalDimension, ovalDuration);

    // ANIMATION 1
    AnimationType animation1Type = AnimationType.MOVE;
    IPosition animation1PositionI = new Position2D(200, 200);
    IPosition animation1PositionF = new Position2D(300, 300);
    IDuration animation1Duration = new Duration(10, 50);

    this.animation1 = new Move(animation1Type, rectangle, animation1Duration,
            animation1PositionI, animation1PositionF);

    // ANIMATION 2
    AnimationType animation2Type = AnimationType.MOVE;
    IDuration animation2Duration = new Duration(20, 70);
    IPosition animation2PositionI = new Position2D(500, 100);
    IPosition animation2PositionF = new Position2D(500, 400);

    this.animation2 = new Move(animation2Type, oval, animation2Duration,
            animation2PositionI, animation2PositionF);

    // ANIMATION 3
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(50, 80);
    IColor animation3ColorI = new ColorRGB(0, 0, 1);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    this.animation3 = new ChangeColor(animation3Type, oval,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 4
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(70, 100);
    IPosition animation4PositionI = new Position2D(300, 300);
    IPosition animation4PositionF = new Position2D(200, 200);

    this.animation4 = new Move(animation4Type, rectangle, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 5
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(51, 70);
    IDimensions animation5dimensionI = new Dimensions(50, 100);
    IDimensions animation5dimensionF = new Dimensions(25, 100);

    this.animation5 = new Scale(animation5Type, rectangle,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    // adding shapes
    this.animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    // adding animations
    animatorModel.addAnimation(animation1);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation4);
    animatorModel.addAnimation(animation5);
  }

  /**
   * Tests textual representation of the animation from the problem statement.
   */
  @Test
  public void testFromProblemStatement() {

    String s = "Shapes:\n"
            + "Name: R\n"
            + "Type: rectangle\n"
            + "Corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (255.0,0.0,0.0)\n"
            + "Appears at t=1.0\n"
            + "Disappears at t=100.0\n\n"

            + "Name: C\n"
            + "Type: oval\n"
            + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,255.0)\n"
            + "Appears at t=6.0\n"
            + "Disappears at t=100.0\n\n"

            + "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10.0 to t=50.0\n"
            + "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20.0 to t=70.0\n"
            + "Shape C changes color from (0.0,0.0,255.0) to (0.0,255.0,0.0) " +
            "from t=50.0 to t=80.0\n"
            + "Shape R scales from Width: 50.0, Height: 100.0 to "
            + "Width: 25.0, Height: 100.0 from t=51.0 to t=70.0\n"
            + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70.0 to t=100.0";

    initializeAnimation();

    String textView = this.animatorModel.textView();

    assertEquals(s, textView);
  }

  /**
   * testing exception being thrown when the shape is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddAnimationToNullShape() {
    IShape nullShape = null;

    // Adding the Animation:
    AnimationType animationType = AnimationType.MOVE;
    IPosition animationPositionI = new Position2D(200, 200);
    IPosition animationPositionF = new Position2D(100, 100);
    IDuration animationDuration = new Duration(10, 50);

    IAnimation animationMove = new Move(animationType, nullShape, animationDuration,
            animationPositionI, animationPositionF);

    animatorModel.addAnimation(animationMove);

  }

  /**
   * testing exception being thrown when the color object provided is illegal.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBadColor() {

    String squareName = "Z";
    ShapeType squareType = ShapeType.RECT;
    IColor squareColor = new ColorRGB(-1, 300, 0); // <-- invalid colot
    IPosition squarePosition = new Position2D(200, 200);
    IDimensions squareDimension = new Dimensions(50, 50);
    IDuration squareDuration = new Duration(1, 100);

    IShape squareBadCol = new Rectangle(squareName, squareType, squareColor,
            squarePosition, squareDimension, squareDuration);

    this.animatorModel.addShape(squareBadCol);

  }

  /**
   * testing exception being thrown when you add a duplicate shape.
   */
  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateShape() {

    String squareName = "S";
    ShapeType squareType = ShapeType.RECT;
    IColor squareColor = new ColorRGB(1, 0, 0);
    IPosition squarePosition = new Position2D(200, 200);
    IDimensions squareDimension = new Dimensions(50, 50);
    IDuration squareDuration = new Duration(1, 100);

    IShape squareDuplicate = new Rectangle(squareName, squareType, squareColor,
            squarePosition, squareDimension, squareDuration);

    this.animatorModel.addShape(this.square);

    animatorModel.addShape(squareDuplicate);
  }

  /**
   * tests exception being thrown when dimensions are less than required.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badDimensionNumber() {

    String squareName = "Z";
    ShapeType squareType = ShapeType.RECT;
    IColor squareColor = new ColorRGB(1, 0, 0);
    IPosition squarePosition = new Position2D(200, 200);
    IDimensions squareDimension = new Dimensions(50);
    IDuration squareDuration = new Duration(1, 100);

    IShape squareBadDimNum = new Rectangle(squareName, squareType, squareColor,
            squarePosition, squareDimension, squareDuration);

    this.animatorModel.addShape(squareBadDimNum);
  }

  /**
   * testing exception being thrown when negative dimension is provided.
   */
  @Test(expected = IllegalArgumentException.class)
  public void negativeDimension() {

    String squareName = "Z";
    ShapeType squareType = ShapeType.RECT;
    IColor squareColor = new ColorRGB(1, 0, 0);
    IPosition squarePosition = new Position2D(200, 200);
    IDimensions squareDimension = new Dimensions(-50, -50);
    IDuration squareDuration = new Duration(1, 100);

    IShape squareNegDim = new Rectangle(squareName, squareType, squareColor,
            squarePosition, squareDimension, squareDuration);

    animatorModel.addShape(squareNegDim);
  }

  /**
   * testing exception being thrown when conflicting animations are added.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConflictingAnimations() {

    // A conflicting Animation:
    AnimationType animationType = AnimationType.MOVE;
    IPosition animationPositionI = new Position2D(200, 200);
    IPosition animationPositionF = new Position2D(100, 100);
    IDuration animationDuration = new Duration(10, 50);

    IAnimation animationMoveAgain = new Move(animationType, this.square, animationDuration,
            animationPositionI, animationPositionF);

    animatorModel.addAnimation(animationMoveAgain);

  }

  /**
   * tests the getAnimationList() method.
   */
  @Test
  public void testGetAnimationList() {
    initializeAnimation();

    List<IAnimation> list = new ArrayList<>();
    list.add(this.animation1);
    list.add(this.animation2);
    list.add(this.animation3);
    list.add(this.animation5);
    list.add(this.animation4);

    assertEquals(animatorModel.getAnimationList(), list);
  }


  /**
   * tests the getShapeList() method.
   */
  @Test
  public void testGetShapeList() {
    initializeAnimation();

    List<IShape> list = new ArrayList<>();
    list.add(this.rectangle);
    list.add(this.oval);

    assertEquals(animatorModel.getShapeList(), list);
  }

  /**
   * tests the getNameOrig() method with the first shape.
   */
  @Test
  public void testNameOrig1() {
    initializeAnimation();

    Map<String, IShape> map = this.animatorModel.getNameOrig();

    assertEquals(map.get("R").getName(), this.rectangle.getName());
  }

  /**
   * tests the getNameOrig() method with the second shape.
   */
  @Test
  public void testNameOrig2() {
    initializeAnimation();

    Map<String, IShape> map = this.animatorModel.getNameOrig();

    assertEquals(map.get("C").getName(), this.oval.getName());
  }

  /**
   * tests the getNameShape() method with the first shape name.
   */
  @Test
  public void testNameShape1() {
    initializeAnimation();

    Map<String, IShape> map = animatorModel.getNameShape();

    assertEquals(map.get("R").getName(), this.rectangle.getName());
  }

  /**
   * tests the getNameShape() method with the second shape name.
   */
  @Test
  public void testNameShape2() {
    initializeAnimation();

    Map<String, IShape> map = animatorModel.getNameShape();

    assertEquals(map.get("C").getName(), this.oval.getName());
  }

  /**
   * tests the getNameAnimation() method with the name of the first shape.
   */
  @Test
  public void testGetNameAnimation1() {
    initializeAnimation();

    Map<String, List<IAnimation>> map = animatorModel.getNameAnimation();

    List<IAnimation> list = new ArrayList<>();
    list.add(animation1);
    list.add(animation5);
    list.add(animation4);

    assertEquals(map.get("R"), list);
  }

  /**
   * tests the getNameAnimation() method with the name of the second shape.
   */
  @Test
  public void testGetNameAnimation2() {
    initializeAnimation();

    Map<String, List<IAnimation>> map = animatorModel.getNameAnimation();

    List<IAnimation> list = new ArrayList<>();
    list.add(animation2);
    list.add(animation3);

    assertEquals(map.get("C"), list);
  }

  /**
   * Tests the getStartAnimation() method.
   */
  @Test
  public void testGetStartAnimation1() {
    initializeAnimation();

    Map<Double, List<IAnimation>> map = animatorModel.getStartAnimation();

    List<IAnimation> list = new ArrayList<>();
    list.add(animation1);

    assertEquals(map.get(10.0), list);
  }

  /**
   * Test the getEndAnimation() method.
   */
  @Test
  public void testGetEndAnimation1() {
    initializeAnimation();

    Map<Double, List<IAnimation>> map = animatorModel.getEndAnimation();

    List<IAnimation> list = new ArrayList<>();
    list.add(animation1);

    assertEquals(list.size(), 1);
  }

  /**
   * Tests the addShape() method.
   */
  @Test
  public void testAddShape() {
    initializeAnimation();

    assertEquals(animatorModel.getShapeList().size(), 2);
  }

  /**
   * Tests the addAnimation() method.
   */
  @Test
  public void testAddAnimation() {
    initializeAnimation();

    assertEquals(animatorModel.getAnimationList().size(), 5);

  }

  /**
   * Tests the deleteAnimation() method.
   */
  @Test
  public void testDeleteShape() {
    initializeAnimation();

    animatorModel.deleteShape(rectangle);

    assertEquals(animatorModel.getShapeList().size(), 1);
  }

  /**
   * Tests the deleteAnimation() method.
   */
  @Test
  public void testDeleteAnimation() {
    initializeAnimation();

    animatorModel.deleteAnimation(animation1);

    assertEquals(animatorModel.getAnimationList().size(), 4);
  }

  /**
   * Tests whether the animations are sorted correctly.
   */
  @Test
  public void testSortAnimation() {

    initSetup();

    // add a shape
    // add animations to the shape at various times
    // tests

    // ANIMATION 1
    AnimationType animation3Type = AnimationType.CHANGECOLOR;
    IDuration animation3Duration = new Duration(1, 1);
    IColor animation3ColorI = new ColorRGB(1, 0, 0);
    IColor animation3ColorF = new ColorRGB(0, 1, 0);

    this.animation1 = new ChangeColor(animation3Type, this.square,
            animation3Duration, animation3ColorI, animation3ColorF);

    // ANIMATION 2
    AnimationType animation4Type = AnimationType.MOVE;
    IDuration animation4Duration = new Duration(1, 2);
    IPosition animation4PositionI = new Position2D(200, 200);
    IPosition animation4PositionF = new Position2D(300, 300);

    this.animation2 = new Move(animation4Type, square, animation4Duration,

            animation4PositionI, animation4PositionF);

    // ANIMATION 3
    AnimationType animation5Type = AnimationType.SCALE;
    IDuration animation5Duration = new Duration(1, 3);
    IDimensions animation5dimensionI = new Dimensions(50, 50);
    IDimensions animation5dimensionF = new Dimensions(100, 100);

    this.animation3 = new Scale(animation5Type, square,
            animation5Duration, animation5dimensionI, animation5dimensionF);

    animatorModel.addShape(square);
    // adding in the descending order, will check if the order is ascending after adding
    animatorModel.addAnimation(animation3);
    animatorModel.addAnimation(animation2);
    animatorModel.addAnimation(animation1);

    String real = animatorModel.getAnimationList()
            .stream()
            .map(x -> x.textView().substring(x.textView().length() - 15))
            .collect(Collectors.joining());

    // This is the sorted form of the various shape's textOutput's time substring
    String expected = "t=1.0 to t=1.0\n"
            + "t=1.0 to t=2.0\n"
            + "t=1.0 to t=3.0\n";

    assertEquals(expected, real);
  }
}

