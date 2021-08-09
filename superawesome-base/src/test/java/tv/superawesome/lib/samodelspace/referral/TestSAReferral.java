package tv.superawesome.lib.samodelspace.referral;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import tv.superawesome.lib.ResourceReader;

public class TestSAReferral {

    @Test
    public void testSARefferalData1 () {

        String json = ResourceReader.readResource("mock_referral_response_1.json");

        SAReferral referralData = new SAReferral(json);
        assertNotNull(referralData);

        int expected_configuration = 1;
        int expected_campaignId = 33;
        int expected_lineItemId = 22;
        int expected_creativeId = 2041;
        int expected_placementId = 28000;

        assertEquals(expected_configuration, referralData.configuration);
        assertEquals(expected_campaignId, referralData.campaignId);
        assertEquals(expected_lineItemId, referralData.lineItemId);
        assertEquals(expected_creativeId, referralData.creativeId);
        assertEquals(expected_placementId, referralData.placementId);
        assertTrue(referralData.writeToReferralQuery().contains("utm_source%3D" + expected_configuration));
        assertTrue(referralData.writeToReferralQuery().contains("utm_campaign%3D" + expected_campaignId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_term%3D" + expected_lineItemId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_content%3D" + expected_creativeId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_medium%3D" + expected_placementId));

        assertTrue(referralData.isValid());

    }

    @Test
    public void testSARefferalData2 () {

        String json = ResourceReader.readResource("mock_referral_response_2.json");

        SAReferral referralData = new SAReferral(json);
        assertNotNull(referralData);

        int expected_configuration = 1;
        int expected_campaignId = 33;
        int expected_lineItemId = -1;
        int expected_creativeId = -1;
        int expected_placementId = 28000;

        assertEquals(expected_configuration, referralData.configuration);
        assertEquals(expected_campaignId, referralData.campaignId);
        assertEquals(expected_lineItemId, referralData.lineItemId);
        assertEquals(expected_creativeId, referralData.creativeId);
        assertEquals(expected_placementId, referralData.placementId);
        assertTrue(referralData.writeToReferralQuery().contains("utm_source%3D" + expected_configuration));
        assertTrue(referralData.writeToReferralQuery().contains("utm_campaign%3D" + expected_campaignId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_term%3D" + expected_lineItemId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_content%3D" + expected_creativeId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_medium%3D" + expected_placementId));

        assertFalse(referralData.isValid());
    }

    @Test
    public void testSARefferalData3 () {

        // source JSON
        String json = null;

        SAReferral referralData = new SAReferral(json);
        assertNotNull(referralData);

        int expected_configuration = -1;
        int expected_campaignId = -1;
        int expected_lineItemId = -1;
        int expected_creativeId = -1;
        int expected_placementId = -1;

        assertEquals(expected_configuration, referralData.configuration);
        assertEquals(expected_campaignId, referralData.campaignId);
        assertEquals(expected_lineItemId, referralData.lineItemId);
        assertEquals(expected_creativeId, referralData.creativeId);
        assertEquals(expected_placementId, referralData.placementId);
        assertTrue(referralData.writeToReferralQuery().contains("utm_source%3D" + expected_configuration));
        assertTrue(referralData.writeToReferralQuery().contains("utm_campaign%3D" + expected_campaignId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_term%3D" + expected_lineItemId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_content%3D" + expected_creativeId));
        assertTrue(referralData.writeToReferralQuery().contains("utm_medium%3D" + expected_placementId));

        assertFalse(referralData.isValid());

    }
}
