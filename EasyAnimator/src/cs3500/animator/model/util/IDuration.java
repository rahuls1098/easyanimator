package cs3500.animator.model.util;

/**
 * Represents a duration of time.
 */
public interface IDuration extends Comparable<IDuration> {

  /**
   * Gets the initial time.
   *
   * @return initial time as double value.
   */
  double getTimeI();

  /**
   * Gets the final time.
   *
   * @return final time as a double value.
   */
  double getTimeF();

  /**
   * Compute the total duraton.
   *
   * @return the total duration
   */
  double getTotalDuration();
}
