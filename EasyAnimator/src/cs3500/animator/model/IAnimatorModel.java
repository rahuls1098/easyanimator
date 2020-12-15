package cs3500.animator.model;

import java.util.List;
import java.util.Map;

import cs3500.animator.model.animation.IAnimation;
import cs3500.animator.model.shape.IShape;


/**
 * The main interface of the Model of the EasyAnimator. Contains  the methods intended for the use
 * of this animator in terms of model construction, getting shapes and animations, etc.
 */
public interface IAnimatorModel {

  /**
   * Adds a shape to the animator.
   *
   * @param shape The shape to be added.
   */
  void addShape(IShape shape);

  /**
   * Adds an animation (or operation) to the animator.
   *
   * @param animation Animation to be added.
   */
  void addAnimation(IAnimation animation);

  /**
   * Deletes the given IShape from the Animator.
   *
   * @param shape The IShape object to be deleted.
   */
  void deleteShape(IShape shape);

  /**
   * Deletes the given IAnimation from the Animator.
   *
   * @param animation The IAnimation object to be deleted.
   */
  void deleteAnimation(IAnimation animation);


  // A TEXTUAL REPRESENTATION

  /**
   * Represents a textual representation of the inputted shapes and their animations. Prints the
   * shape details followed by their animation details (sorted by time).
   *
   * @return The string representing the textual representation of the animation.
   */
  String textView();

  /**
   * Like the textual view but modifies the times according to the given speed.
   *
   * @param speed the speed of the animation.
   * @return A String representing the textual view of the animation.
   */
  String textView(double speed);


  // ESSENTIALLY GETTERS

  /**
   * Gets a list of animations provided.
   *
   * @return list of IAnimation objects currently stored.
   */
  List<IAnimation> getAnimationList();

  /**
   * Gets a list of Shapes provided.
   *
   * @return list of IShape objects currently stored.
   */
  List<IShape> getShapeList();

  /**
   * Gets the copy of the list of original shapes.
   *
   * @return copy of shapes
   */
  List<IShape> getShapeCopyList();

  /**
   * Gets the original states of the IShapes provided as a map of shape names to shape objects.
   *
   * @return A map of shape names to their original IShape objects.
   */
  Map<String, IShape> getNameOrig();

  /**
   * Gets a map of shape names to current version of the IShape objects.
   *
   * @return A map of the shape names to their current IShape objects.
   */
  Map<String, IShape> getNameShape();

  /**
   * Gets a map of Shape names to their associated animations.
   *
   * @return A map of the name of the shapes and their corresponding list of animations
   */
  Map<String, List<IAnimation>> getNameAnimation();

  /**
   * Gets a map of start times to their associated animations.
   *
   * @return A map of start times (double) and their list of animation objects at the time.
   */
  Map<Double, List<IAnimation>> getStartAnimation();

  /**
   * Gets a map of end times to their associated animations.
   *
   * @return A map of end times (double) and a list of animations that end at that time.
   */
  Map<Double, List<IAnimation>> getEndAnimation();
}
