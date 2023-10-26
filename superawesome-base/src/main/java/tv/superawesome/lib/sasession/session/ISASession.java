package tv.superawesome.lib.sasession.session;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.publisher.PublisherConfiguration;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 10/05/2018.
 */

public interface ISASession {
    void prepareSession (SASessionInterface listener);
    String getBaseUrl ();
    boolean getTestMode ();
    int getDauId ();
    String getVersion ();
    SAConfiguration getConfiguration ();
    int getCachebuster ();
    String getPackageName ();
    String getAppName ();
    SAUtils.SAConnectionType getConnectionType ();
    String getLang ();
    String getDevice ();
    String getUserAgent ();
    SARTBInstl getInstl();
    SARTBPosition getPos();
    SARTBSkip getSkip();
    SARTBStartDelay getStartDelay();
    SARTBPlaybackMethod getPlaybackMethod();
    PublisherConfiguration getPublisherConfiguration();
    int getWidth();
    int getHeight();
}
