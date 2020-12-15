package cs3500.animator.model.util;

/**
 * The Duration as defined with an initial and final time value.
 */
public class Duration implements IDuration {
  private double timeI; // start time
  private double timeF; // end time

  /**
   * Duration is composed of a time-initial (timeI) and time-final(timeF).
   *
   * @param timeI Initial time.
   * @param timeF Final time.
   */
  public Duration(double timeI, double timeF) throws IllegalArgumentException {
    if (timeI < 0 || timeF < 0) {
      throw new IllegalArgumentException("-ve duration");
    }
    if (timeF < timeI) {
      throw new IllegalArgumentException("invalid time");
    }
    this.timeI = timeI;
    this.timeF = timeF;
  }

  /**
   * Copy constructor for cloning Shape duration field.
   *
   * @param duration The duration object to be copied.
   */
  public Duration(IDuration duration) {
    this.timeI = duration.getTimeI();
    this.timeF = duration.getTimeF();
  }

  @Override
  public double getTimeI() {
    return timeI;
  }

  @Override
  public double getTimeF() {
    return timeF;
  }

  @Override
  public double getTotalDuration() {
    return getTimeF() - getTimeI();
  }

  /**
   * Compare method to check whether given initial time is greater than or less than this initial
   * time.
   *
   * @param duration given duration
   */
  private int compareStartTimes(IDuration duration) {
    if (this.timeI < duration.getTimeI()) {
      return -1;
    } else if (this.timeI > duration.getTimeI()) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Compare method to check whether given end time is greater than or less than this end time.
   *
   * @param duration given duration
   */
  private int compareEndTimes(IDuration duration) {
    if (this.timeF < duration.getTimeF()) {
      return -1;
    } else if (this.timeF > duration.getTimeF()) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public int compareTo(IDuration duration) {
    int compareStartTimes = this.compareStartTimes(duration);
    // start time has a higher precedence than end time
    return compareStartTimes != 0 ? compareStartTimes : this.compareEndTimes(duration);
  }
}

