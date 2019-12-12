package tv.superawesome.lib.sagdprisminorsdk.isMinor.models;

import org.junit.Assert;
import org.junit.Test;

import tv.superawesome.lib.ResourceReader;
import tv.superawesome.lib.sagdprisminorsdk.minor.models.GetIsMinorModel;

public class TestGetIsMinorMapping {

    @Test
    public void test_GetIsMinor_Mapping_Success_Response() {

        String json = ResourceReader.readResource("mock_get_is_minor_success_response.json");

        GetIsMinorModel isMinorModel = new GetIsMinorModel(json);

        Assert.assertNotNull(isMinorModel);
        Assert.assertEquals(isMinorModel.getCountry(), "gb");
        Assert.assertEquals(isMinorModel.getConsentAgeForCountry(), 13);
        Assert.assertEquals(isMinorModel.getAge(), 6);
    }

    @Test
    public void test_GetIsMinor_With_Flag_Mapping_Success_Response() {

        String json = ResourceReader.readResource("mock_get_is_minor_with_flag_success_response.json");

        GetIsMinorModel isMinorModel = new GetIsMinorModel(json);

        Assert.assertNotNull(isMinorModel);
        Assert.assertEquals(isMinorModel.getCountry(), "gb");
        Assert.assertEquals(isMinorModel.getConsentAgeForCountry(), 13);
        Assert.assertEquals(isMinorModel.getAge(), 6);
        Assert.assertEquals(isMinorModel.isMinor(), true);
    }
}
