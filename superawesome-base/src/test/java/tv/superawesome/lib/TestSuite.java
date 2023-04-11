package tv.superawesome.lib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tv.superawesome.lib.saadloader.adloader.SAAdLoader_LoadAd_Test;
import tv.superawesome.lib.saadloader.postprocessor.SAProcessHTML_Test;
import tv.superawesome.lib.saadloader.query.SAAdLoader_GetAwesomeAdsEndpoint_Test;
import tv.superawesome.lib.saadloader.query.SAAdLoader_GetAwesomeAdsHeader_Test;
import tv.superawesome.lib.saadloader.query.SAAdLoader_GetAwesomeAdsQuery_Test;
import tv.superawesome.lib.saevents.events.setup.SAClickEventSetup_Test;
import tv.superawesome.lib.saevents.events.setup.SAImpressionEventSetup_Test;
import tv.superawesome.lib.saevents.events.setup.SAServerEventSetup_Test;
import tv.superawesome.lib.saevents.events.setup.SAURLEventSetup_Test;
import tv.superawesome.lib.saevents.events.setup.ViewableImpressionEventSetup_Test;
import tv.superawesome.lib.saevents.events.trigger.SAClickEventTrigger_Test;
import tv.superawesome.lib.saevents.events.trigger.SAImpressionEventTrigger_Test;
import tv.superawesome.lib.saevents.events.trigger.ViewableImpressionEventTrigger_Test;
import tv.superawesome.lib.saevents.modules.SAVASTModule_Test;
import tv.superawesome.lib.sagdprisminorsdk.isMinor.models.GetIsMinorMapping_Test;
import tv.superawesome.lib.sagdprisminorsdk.isMinor.requests.GetIsMinorRequest_Test;
import tv.superawesome.lib.sajsonparser.SAJsonParser_ParseArray_Test;
import tv.superawesome.lib.sajsonparser.SAJsonParser_ParseDictionary_Test;
import tv.superawesome.lib.sajsonparser.SAJsonParser_ParseObject_Test;
import tv.superawesome.lib.sajsonparser.SAJsonParser_WriteArray_Test;
import tv.superawesome.lib.sajsonparser.SAJsonParser_WriteDictionary_Test;
import tv.superawesome.lib.sajsonparser.SAJsonParser_WriteObject_Test;
import tv.superawesome.lib.samodelspace.referral.SAReferral_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_1_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_2_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_3_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_4_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_5_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_6_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_7_Test;
import tv.superawesome.lib.samodelspace.saad.SAAd_8_Test;
import tv.superawesome.lib.samodelspace.vastad.SAVAST_1_Test;
import tv.superawesome.lib.samodelspace.vastad.SAVAST_2_Test;
import tv.superawesome.lib.sanetwork.file.SAFileDownloader_Test;
import tv.superawesome.lib.sanetwork.file.SAFileItem_Test;
import tv.superawesome.lib.sanetwork.request.SANetwork_Test;
import tv.superawesome.lib.sanetwork.request.SANetworkUtils_Test;
import tv.superawesome.lib.sautils.array.SAUtils_RemoveAllButFirstElement_Test;
import tv.superawesome.lib.sautils.aux.SAUtils_GenerateUniqueKey_Test;
import tv.superawesome.lib.sautils.aux.SAUtils_MapSourceSizeIntoBoundingSize_Test;
import tv.superawesome.lib.sautils.aux.SAUtils_RandomNumberBetween_Test;
import tv.superawesome.lib.sautils.network.SAUtils_EncodeDictAsJsonDict_Test;
import tv.superawesome.lib.sautils.network.SAUtils_EncodeURL_Test;
import tv.superawesome.lib.sautils.network.SAUtils_FindBaseURLFromResourceURL_Test;
import tv.superawesome.lib.sautils.network.SAUtils_FormGetQueryFromDict_Test;
import tv.superawesome.lib.sautils.network.SAUtils_GetCachebuster_Test;
import tv.superawesome.lib.sautils.network.SAUtils_IsJSONEmpty_Test;
import tv.superawesome.lib.sautils.network.SAUtils_IsValidEmail_Test;
import tv.superawesome.lib.sautils.network.SAUtils_IsValidURL_Test;
import tv.superawesome.lib.savastparser.vastparser.SAVASTParser_Async_Test;
import tv.superawesome.lib.savastparser.vastparser.SAVASTParser_Local_1_Tests;
import tv.superawesome.lib.savastparser.vastparser.SAVASTParser_Local_2_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_1_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_2_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_3_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_4_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_5_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_6_Tests;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_7_Tests;

/**
 * Created by gabriel.coman on 03/05/2018.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

        ////////////////////////////////
        // is minor
        GetIsMinorMapping_Test.class,
        GetIsMinorRequest_Test.class,

        ////////////////////////////////
        // ad loader
        SAProcessHTML_Test.class,
        SAAdLoader_GetAwesomeAdsQuery_Test.class,
        SAAdLoader_GetAwesomeAdsEndpoint_Test.class,
        SAAdLoader_GetAwesomeAdsHeader_Test.class,
        SAAdLoader_LoadAd_Test.class,

        ////////////////////////////////
        // events
        // setup
        SAClickEventSetup_Test.class,
        SAImpressionEventSetup_Test.class,
        SAServerEventSetup_Test.class,
        SAURLEventSetup_Test.class,
        ViewableImpressionEventSetup_Test.class,

        // triggers
        SAClickEventTrigger_Test.class,
        SAImpressionEventTrigger_Test.class,
        ViewableImpressionEventTrigger_Test.class,
        SAURLEventSetup_Test.class,

        // modules
        SAVASTModule_Test.class,

        ////////////////////////////////
        // json parser
        SAJsonParser_ParseArray_Test.class,
        SAJsonParser_WriteArray_Test.class,
        SAJsonParser_WriteDictionary_Test.class,
        SAJsonParser_ParseDictionary_Test.class,
        SAJsonParser_ParseObject_Test.class,
        SAJsonParser_WriteObject_Test.class,

        ////////////////////////////////
        // models
        SAReferral_Test.class,
        SAAd_1_Test.class,
        SAAd_2_Test.class,
        SAAd_3_Test.class,
        SAAd_4_Test.class,
        SAAd_5_Test.class,
        SAAd_6_Test.class,
        SAAd_7_Test.class,
        SAAd_8_Test.class,
        SAVAST_1_Test.class,
        SAVAST_2_Test.class,

        ////////////////////////////////
        // networking
        SAFileItem_Test.class,
        SANetwork_Test.class,
        SAFileDownloader_Test.class,
        SANetworkUtils_Test.class,

        ////////////////////////////////
        // sautils
        SAUtils_RemoveAllButFirstElement_Test.class,
        SAUtils_GenerateUniqueKey_Test.class,
        SAUtils_MapSourceSizeIntoBoundingSize_Test.class,
        SAUtils_RandomNumberBetween_Test.class,
        SAUtils_EncodeDictAsJsonDict_Test.class,
        SAUtils_EncodeURL_Test.class,
        SAUtils_FindBaseURLFromResourceURL_Test.class,
        SAUtils_FormGetQueryFromDict_Test.class,
        SAUtils_GetCachebuster_Test.class,
        SAUtils_IsJSONEmpty_Test.class,
        SAUtils_IsValidEmail_Test.class,
        SAUtils_IsValidURL_Test.class,

        ////////////////////////////////
        // xml parser
        SAXMLParser_1_Tests.class,
        SAXMLParser_2_Tests.class,
        SAXMLParser_3_Tests.class,
        SAXMLParser_4_Tests.class,
        SAXMLParser_5_Tests.class,
        SAXMLParser_6_Tests.class,
        SAXMLParser_7_Tests.class,
        SAVASTParser_Local_1_Tests.class,
        SAVASTParser_Local_2_Tests.class,
        SAVASTParser_Async_Test.class
})
public class TestSuite {
}
