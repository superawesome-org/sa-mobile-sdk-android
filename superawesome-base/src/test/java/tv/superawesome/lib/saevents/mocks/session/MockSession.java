package tv.superawesome.lib.saevents.mocks.session;

import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.publisher.PublisherConfiguration;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sasession.session.SASessionInterface;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 10/05/2018.
 */

public class MockSession implements ISASession {

    private final String serverUrl;

    public MockSession (String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public void prepareSession(SASessionInterface saSessionInterface) {
        if (saSessionInterface != null) {
            saSessionInterface.didFindSessionReady();
        }
    }

    @Override
    public String getBaseUrl() {
        return serverUrl;
    }

    @Override
    public boolean getTestMode() {
        return true;
    }

    @Override
    public int getDauId() {
        return 654321;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public SAConfiguration getConfiguration() {
        return SAConfiguration.PRODUCTION;
    }

    @Override
    public int getCachebuster() {
        return 123456;
    }

    @Override
    public String getPackageName() {
        return "superawesome.tv.saadloaderdemo";
    }

    @Override
    public String getAppName() {
        return "SAAdLoaderDemo";
    }

    @Override
    public SAUtils.SAConnectionType getConnectionType() {
        return SAUtils.SAConnectionType.wifi;
    }

    @Override
    public String getLang() {
        return "en_GB";
    }

    @Override
    public String getDevice() {
        return "phone";
    }

    @Override
    public String getUserAgent() {
        return "some-user-agent";
    }

    @Override
    public SARTBInstl getInstl() {
        return SARTBInstl.FULLSCREEN;
    }

    @Override
    public SARTBPosition getPos() {
        return SARTBPosition.FULLSCREEN;
    }

    @Override
    public SARTBSkip getSkip() {
        return SARTBSkip.NO_SKIP;
    }

    @Override
    public SARTBStartDelay getStartDelay() {
        return SARTBStartDelay.PRE_ROLL;
    }

    @Override
    public SARTBPlaybackMethod getPlaybackMethod() {
        return SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN;
    }

    @Override
    public PublisherConfiguration getPublisherConfiguration() {
        return new PublisherConfiguration(false, false, false, 0, false, false, false, 0, 0);
    }

    @Override
    public int getWidth() {
        return 320;
    }

    @Override
    public int getHeight() {
        return 50;
    }
}
