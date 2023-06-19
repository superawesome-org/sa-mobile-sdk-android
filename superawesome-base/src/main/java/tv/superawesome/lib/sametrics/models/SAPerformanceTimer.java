package tv.superawesome.lib.sametrics.models;

public class SAPerformanceTimer {
  private long startTime = 0L;

  public long getStartTime() {
    return startTime;
  }

  public void start(Long startTime) {
    this.startTime = startTime;
  }

  public Long delta(Long currentTime) {
    long delta = currentTime - startTime;
    return delta > 0L ? delta : 0L;
  }
}
