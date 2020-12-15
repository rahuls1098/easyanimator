package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs3500.animator.model.animation.AnimationType;
import cs3500.animator.model.animation.ChangeColor;
import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.animation.Move;
import cs3500.animator.model.animation.Rotate;
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
import cs3500.animator.readbuild.TweenModelBuilder;


/**
 * Implements the IAnimatorModel Interface and represents the building of the animation. Initializes
 * data structures used to access shapes and animations and provides methods to retrieve them.
 * Ensures proper initialization by checking for conflicts in animations.
 */
public final class AnimatorModelImpl implements IAnimatorModel {

  /**
   * Implements the TweenModelBuilderIAnimatorModel interface with methods to add shapes and
   * animations and initialize the model from the AnimationFileReader.
   */
  public static final class Builder implements TweenModelBuilder<IAnimatorModel> {

    private AnimatorModelImpl model = new AnimatorModelImpl();

    @Override
    public TweenModelBuilder<IAnimatorModel> addOval(
            String name,
            float cx, float cy,
            float xRadius, float yRadius, float theta,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {

      ShapeType ovalType = ShapeType.ELLIPSE;
      IColor ovalColor = new ColorRGB((double) red, (double) green, (double) blue);
      IPosition ovalPosition = new Position2D((double) cx, (double) cy);
      IDimensions ovalDimension = new Dimensions((double) xRadius, (double) yRadius);
      IDuration ovalDuration = new Duration((double) startOfLife, (double) endOfLife);
      IShape oval = new Oval(name, ovalType, ovalColor,
              theta, ovalPosition, ovalDimension, ovalDuration);
      model.addShape(oval);

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRectangle(
            String name,
            float lx, float ly,
            float width, float height, float theta,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {

      ShapeType rectangleType = ShapeType.RECT;
      IColor rectangleColor = new ColorRGB((double) red, (double) green, (double) blue);
      IPosition rectanglePosition = new Position2D((double) lx, (double) ly);
      IDimensions rectangleDimension = new Dimensions((double) width, (double) height);
      IDuration rectangleDuration = new Duration((double) startOfLife, (double) endOfLife);
      IShape rectangle = new Rectangle(name, rectangleType, rectangleColor,
              theta, rectanglePosition, rectangleDimension, rectangleDuration);
      model.addShape(rectangle);

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addMove(
            String name,
            float moveFromX, float moveFromY, float moveToX, float moveToY,
            int startTime, int endTime) {

      AnimationType moveType = AnimationType.MOVE;
      IPosition movePositionI = new Position2D((double) moveFromX, (double) moveFromY);
      IPosition movePositionF = new Position2D((double) moveToX, (double) moveToY);
      IDuration moveDuration = new Duration(startTime, endTime);
      // extracted the shape using its name from the nameShape map
      IShape shape = model.getShapeFromName(name);
      IAnimation move = new Move(moveType, shape, moveDuration, movePositionI, movePositionF);
      model.addAnimation(move);

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addColorChange(
            String name,
            float oldR, float oldG, float oldB, float newR, float newG, float newB,
            int startTime, int endTime) {

      AnimationType changeColorType = AnimationType.CHANGECOLOR;
      IDuration changeColorDuration = new Duration(startTime, endTime);
      IColor changeColorI = new ColorRGB((double) oldR, (double) oldG, (double) oldB);
      IColor changeColorF = new ColorRGB((double) newR, (double) newG, (double) newB);
      IShape shape = model.getShapeFromName(name);
      IAnimation changeColor = new ChangeColor(changeColorType, shape, changeColorDuration,
              changeColorI, changeColorF);
      model.addAnimation(changeColor);

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addScaleToChange(String name, float fromSx, float
            fromSy, float toSx, float toSy, int startTime, int endTime) {

      AnimationType scaleType = AnimationType.SCALE;
      IDuration scaleDuration = new Duration(startTime, endTime);
      IDimensions scaleI = new Dimensions((double) fromSx, (double) fromSy);
      IDimensions scaleF = new Dimensions((double) toSx, (double) toSy);
      IShape shape = model.getShapeFromName(name);
      IAnimation scale = new Scale(scaleType, shape, scaleDuration, scaleI, scaleF);
      model.addAnimation(scale);

      return this;
    }

    @Override
    public TweenModelBuilder addRotate(String name, float fromTheta, float toTheta, int startTime, int endTime) {
      AnimationType rotateType = AnimationType.ROTATE;
      IDuration rotateDuration = new Duration(startTime, endTime);
      IShape shape = model.getShapeFromName(name);
      IAnimation rotate = new Rotate(rotateType, shape, rotateDuration, fromTheta, toTheta);
      model.addAnimation(rotate);

      return this;
    }

    @Override
    public IAnimatorModel build() {
      return model;
    }
  }

  private List<IAnimation> animationList; // list of animations
  private List<IShape> shapeList; // list of original shapes
  private List<IShape> shapeCopyList; // list of copy of original shapes

  private Map<String, IShape> nameOrig; // maps shape name to original shape
  private Map<String, IShape> nameShape; // maps shape name to copy of shape
  private Map<String, List<IAnimation>> nameAnimation; // maps shape name to animation
  private Map<Double, List<IAnimation>> startAnimation; // maps animation start time to animations
  private Map<Double, List<IAnimation>> endAnimation; // maps animation end time to animations

  /**
   * The implementation of the interface. Contains all the Data structures. (Described in the getter
   * javaDocs).
   */
  public AnimatorModelImpl() {
    this.animationList = new ArrayList<>();
    this.shapeList = new ArrayList<>();
    this.shapeCopyList = new ArrayList<>();
    this.nameOrig = new HashMap<>();
    this.nameShape = new HashMap<>();
    this.nameAnimation = new HashMap<>();
    this.startAnimation = new HashMap<>();
    this.endAnimation = new HashMap<>();
  }

  @Override
  public void addShape(IShape shape) throws IllegalArgumentException {
    String shapeName = shape.getName();

    if (nameShape.containsKey(shapeName)) {
      throw new IllegalArgumentException("name not unique");
    }

    if (shape == null) {
      throw new IllegalArgumentException("the shape entered is null");
    }

    shapeList.add(shape);
    shapeCopyList.add(shape.copy());
    nameShape.put(shapeName, shape.copy());
    nameOrig.put(shapeName, shape);
  }

  @Override
  public void addAnimation(IAnimation animation) throws IllegalArgumentException {

    if (animation == null) {
      throw new IllegalArgumentException("the animation entered is null");
    }

    if (animation.getShape() == null) {
      throw new IllegalArgumentException("The shape attached to this animation is null :(");
    }

    String shapeName = animation.getShape().getName();
    double animationBeg = animation.getDuration().getTimeI();
    double animationEnd = animation.getDuration().getTimeF();
    animationConflict(animation);

    if (!nameShape.containsKey(shapeName)) {
      throw new IllegalArgumentException("shape not added");
    }

    // adding to the list of animation.
    animationList.add(animation);
    sortAnimation(animationList);

    // adding to various maps which have Values as animation objects.
    addToMap(nameAnimation, animation, shapeName);
    addToMap(startAnimation, animation, animationBeg);
    addToMap(endAnimation, animation, animationEnd);
  }

  /**
   * Gets the shape based on its name from the shapeCopyList in order to be added as a field to the
   * animation. Crucial that it gets it from the copy list so that the original shapes are not
   * mutated.
   *
   * @param name of shape to be accessed
   * @return shape corresponding to name
   */
  private IShape getShapeFromName(String name) {
    IShape shape = null;
    for (IShape s : shapeCopyList) {
      if (s.getName().equals(name)) {
        shape = s;
      }
    }
    return shape;
  }

  @Override
  public void deleteShape(IShape shape) {
    String shapeName = shape.getName();

    if (!nameShape.containsKey(shapeName)) {
      throw new IllegalArgumentException("the shape doesn't exist");
    }

    shapeList.remove(shape);
    shapeCopyList.remove(shape);
    nameOrig.remove(shape);
    nameShape.remove(shapeName);
    nameAnimation.remove(shapeName);

    if (nameAnimation.get(shapeName) != null) {
      List<IAnimation> animationsToDel = new ArrayList<>(nameAnimation.get(shapeName));
      animationsToDel.forEach(x -> deleteAnimation(x));
    }
  }

  @Override
  public void deleteAnimation(IAnimation animation) throws IllegalArgumentException {
    String shapeName = animation.getShape().getName();
    double animationTimeI = animation.getDuration().getTimeI();
    double animationTimeF = animation.getDuration().getTimeF();


    if (!nameOrig.containsKey(shapeName)) {
      throw new IllegalArgumentException("that shape doesn't exist");
    }

    List<IAnimation> listNameAnimation = nameAnimation.get(shapeName);
    List<IAnimation> listFromStartAnimation = startAnimation.get(animationTimeI);
    List<IAnimation> listFromEndAnimation = endAnimation.get(animationTimeF);

    listNameAnimation.remove(animation);
    listFromStartAnimation.remove(animation);
    listFromEndAnimation.remove(animation);


    nameAnimation.put(shapeName, listNameAnimation);
    startAnimation.put(animationTimeI, listFromStartAnimation);
    endAnimation.put(animationTimeF, listFromEndAnimation);
    animationList.remove(animation);
  }

  @Override
  public String textView() {
    return textView(1);
  }

  @Override
  public String textView(double speed) {
    sortAnimation(animationList);
    sortShape(shapeList);

    StringBuilder builder = new StringBuilder();

    builder.append("Shapes:\n");

    builder.append(shapeList.stream()
            .map(s -> s.textView(speed) + "\n\n")
            .collect(Collectors.joining()));

    builder.append(animationList.stream()
            .map(s -> s.textView(speed))
            .collect(Collectors.joining()) + "\n\n");

    return builder.toString().substring(0, builder.length() - 3);
  }

  @Override
  public List<IAnimation> getAnimationList() {
    return animationList;
  }

  @Override
  public List<IShape> getShapeList() {
    return shapeList;
  }

  @Override
  public List<IShape> getShapeCopyList() {
    return shapeCopyList;
  }

  @Override
  public Map<String, IShape> getNameOrig() {
    return nameOrig;
  }

  @Override
  public Map<String, IShape> getNameShape() {
    return nameShape;
  }

  @Override
  public Map<String, List<IAnimation>> getNameAnimation() {
    return nameAnimation;
  }

  @Override
  public Map<Double, List<IAnimation>> getStartAnimation() {
    return startAnimation;
  }

  @Override
  public Map<Double, List<IAnimation>> getEndAnimation() {
    return endAnimation;
  }

  //// HELPER METHODS ////


  /**
   * Checks whether ANY other animation conflicts with this animaion.
   *
   * @param animation an animation object
   * @throws IllegalArgumentException if the conflict exists
   */
  private void animationConflict(IAnimation animation) throws IllegalArgumentException {

    List<IAnimation> animationsInRange = new ArrayList<>();
    double startTime = animation.getDuration().getTimeI();
    double endTime = animation.getDuration().getTimeF();

    for (double i = startTime; i <= endTime; i++) {
      addFromMap(startAnimation, animationsInRange, i);
      addFromMap(endAnimation, animationsInRange, i);
    }

    animationsInRange.forEach(a -> checkForConflict(a, animation));
  }

  /**
   * Checks for conflicts between the animations.
   *
   * @param a the first animation
   * @param b the second animation
   * @throws IllegalArgumentException if a and b conflict
   */
  private void checkForConflict(IAnimation a, IAnimation b) throws IllegalArgumentException {
    String aName = a.getShape().getName();
    AnimationType aType = a.getType();
    String bName = b.getShape().getName();
    AnimationType bType = b.getType();

    if (aName.equals(bName) && aType.equals(bType)) {
      throw new IllegalArgumentException("conflicting animations");
    }
  }

  /**
   * addToMap is a helper method used to add IAnimation objects to various maps. (note: This method
   * is a helper used for mainly abstraction reasons)
   *
   * @param map       The map to be added to.
   * @param animation The animation to be added.
   * @param key       The Key Object by which we search the input point (where animation is
   *                  inserted).
   */
  private void addToMap(Map map, IAnimation animation, Object key) {
    if (map.containsKey(key)) {
      List<IAnimation> list = (List<IAnimation>) map.get(key);
      list.add(animation);
      //sortAnimation(animationList);
      sortAnimation(list);
      map.replace(key, list);
    } else {
      List<IAnimation> list = new ArrayList<>();
      list.add(animation);
      map.put(key, list);
    }
  }

  /**
   * Add the list derived from the given key into the given list.
   *
   * @param map           The map for the list to be derived from.
   * @param listAnimation the list of animations.
   * @param key           the key to access the map.
   */
  private void addFromMap(Map map, List<IAnimation> listAnimation, Object key) {
    if (map.containsKey(key)) {
      listAnimation.addAll((Collection<? extends IAnimation>) map.get(key));
    }
  }

  /**
   * Sorts the List of animation by their times in ascending order.
   *
   * @param animationList The list of IAnimation objects.
   */
  private void sortAnimation(List<IAnimation> animationList) {

    // Sorting using the method reference to comparator (in IAnimation Interface)
    Collections.sort(animationList, IAnimation::compareTo);

  }

  /**
   * Sorts the List of shapes by their times in ascending order.
   *
   * @param shapeList list of Shape objects
   */
  private void sortShape(List<IShape> shapeList) {
    Collections.sort(shapeList, IShape::compareTo);
  }
}