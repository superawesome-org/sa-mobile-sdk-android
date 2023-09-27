package tv.superawesome.lib.sametrics.models;

import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.sautils.SAUtils;

public class SAPerformanceMetricTags {
  public final int placementId;
  public final int lineItemId;
  public final int creativeId;
  public final SACreativeFormat format;
  public final String sdkVersion;
  public final SAUtils.SAConnectionType connectionType;

  public SAPerformanceMetricTags(int placementId,
                                 int lineItemId,
                                 int creativeId,
                                 SACreativeFormat format,
                                 String sdkVersion,
                                 SAUtils.SAConnectionType connectionType) {
    this.placementId = placementId;
    this.lineItemId = lineItemId;
    this.creativeId = creativeId;
    this.format = format;
    this.sdkVersion = sdkVersion;
    this.connectionType = connectionType;
  }
}
