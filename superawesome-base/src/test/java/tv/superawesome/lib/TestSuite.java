package tv.superawesome.lib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tv.superawesome.lib.saadloader.adloader.TestSAAdLoader_LoadAd;
import tv.superawesome.lib.saadloader.postprocessor.TestSAProcessHTML;
import tv.superawesome.lib.saadloader.query.TestSAAdLoader_GetAwesomeAdsEndpoint;
import tv.superawesome.lib.saadloader.query.TestSAAdLoader_GetAwesomeAdsHeader;
import tv.superawesome.lib.saadloader.query.TestSAAdLoader_GetAwesomeAdsQuery;
import tv.superawesome.lib.saevents.events.setup.TestSAClickEventSetup;
import tv.superawesome.lib.saevents.events.setup.TestSAImpressionEventSetup;
import tv.superawesome.lib.saevents.events.setup.TestSAServerEventSetup;
import tv.superawesome.lib.saevents.events.setup.TestSAURLEventSetup;
import tv.superawesome.lib.saevents.events.setup.TestViewableImpressionEventSetup;
import tv.superawesome.lib.saevents.events.trigger.TestSAClickEventTrigger;
import tv.superawesome.lib.saevents.events.trigger.TestSAImpressionEventTrigger;
import tv.superawesome.lib.saevents.events.trigger.TestViewableImpressionEventTrigger;
import tv.superawesome.lib.saevents.modules.TestMoatModule;
import tv.superawesome.lib.saevents.modules.TestSAVASTModule;
import tv.superawesome.lib.sagdprisminorsdk.isMinor.models.TestGetIsMinorMapping;
import tv.superawesome.lib.sagdprisminorsdk.isMinor.requests.TestGetIsMinorRequest;
import tv.superawesome.lib.sajsonparser.TestSAJsonParser_ParseArray;
import tv.superawesome.lib.sajsonparser.TestSAJsonParser_ParseDictionary;
import tv.superawesome.lib.sajsonparser.TestSAJsonParser_ParseObject;
import tv.superawesome.lib.sajsonparser.TestSAJsonParser_WriteArray;
import tv.superawesome.lib.sajsonparser.TestSAJsonParser_WriteDictionary;
import tv.superawesome.lib.sajsonparser.TestSAJsonParser_WriteObject;
import tv.superawesome.lib.samodelspace.referral.TestSAReferral;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_1;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_2;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_3;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_4;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_5;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_6;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_7;
import tv.superawesome.lib.samodelspace.saad.TestSAAd_8;
import tv.superawesome.lib.samodelspace.vastad.TestSAVAST_1;
import tv.superawesome.lib.samodelspace.vastad.TestSAVAST_2;
import tv.superawesome.lib.sanetwork.file.TestSAFileDownloader;
import tv.superawesome.lib.sanetwork.file.TestSAFileItem;
import tv.superawesome.lib.sanetwork.request.TestSANetwork;
import tv.superawesome.lib.sanetwork.request.TestSANetworkUtils;
import tv.superawesome.lib.sautils.array.TestSAUtils_RemoveAllButFirstElement;
import tv.superawesome.lib.sautils.aux.TestSAUtils_GenerateUniqueKey;
import tv.superawesome.lib.sautils.aux.TestSAUtils_MapSourceSizeIntoBoundingSize;
import tv.superawesome.lib.sautils.aux.TestSAUtils_RandomNumberBetween;
import tv.superawesome.lib.sautils.network.TestSAUtils_EncodeDictAsJsonDict;
import tv.superawesome.lib.sautils.network.TestSAUtils_EncodeURL;
import tv.superawesome.lib.sautils.network.TestSAUtils_FindBaseURLFromResourceURL;
import tv.superawesome.lib.sautils.network.TestSAUtils_FormGetQueryFromDict;
import tv.superawesome.lib.sautils.network.TestSAUtils_GetCachebuster;
import tv.superawesome.lib.sautils.network.TestSAUtils_IsJSONEmpty;
import tv.superawesome.lib.sautils.network.TestSAUtils_IsValidEmail;
import tv.superawesome.lib.sautils.network.TestSAUtils_IsValidURL;
import tv.superawesome.lib.savastparser.vastparser.SAVASTParser_Async_Test;
import tv.superawesome.lib.savastparser.vastparser.SAVASTParser_Local_Tests1;
import tv.superawesome.lib.savastparser.vastparser.SAVASTParser_Local_Tests2;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests1;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests2;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests3;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests4;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests5;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests6;
import tv.superawesome.lib.savastparser.xmlparser.SAXMLParser_Tests7;

/**
 * Created by gabriel.coman on 03/05/2018.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

        ////////////////////////////////
        // is minor
        TestGetIsMinorMapping.class,
        TestGetIsMinorRequest.class,

        ////////////////////////////////
        // ad loader
        TestSAProcessHTML.class,
        TestSAAdLoader_GetAwesomeAdsQuery.class,
        TestSAAdLoader_GetAwesomeAdsEndpoint.class,
        TestSAAdLoader_GetAwesomeAdsHeader.class,
        TestSAAdLoader_LoadAd.class,

        ////////////////////////////////
        // events
        // setup
        TestSAClickEventSetup.class,
        TestSAImpressionEventSetup.class,
        TestSAServerEventSetup.class,
        TestSAURLEventSetup.class,
        TestViewableImpressionEventSetup.class,

        // triggers
        TestSAClickEventTrigger.class,
        TestSAImpressionEventTrigger.class,
        TestViewableImpressionEventTrigger.class,
        TestSAURLEventSetup.class,

        // modules
        TestMoatModule.class,
        TestSAVASTModule.class,
        TestMoatModule.class,

        ////////////////////////////////
        // json parser
        TestSAJsonParser_ParseArray.class,
        TestSAJsonParser_WriteArray.class,
        TestSAJsonParser_WriteDictionary.class,
        TestSAJsonParser_ParseDictionary.class,
        TestSAJsonParser_ParseObject.class,
        TestSAJsonParser_WriteObject.class,

        ////////////////////////////////
        // models
        TestSAReferral.class,
        TestSAAd_1.class,
        TestSAAd_2.class,
        TestSAAd_3.class,
        TestSAAd_4.class,
        TestSAAd_5.class,
        TestSAAd_6.class,
        TestSAAd_7.class,
        TestSAAd_8.class,
        TestSAVAST_1.class,
        TestSAVAST_2.class,

        ////////////////////////////////
        // networking
        TestSAFileItem.class,
        TestSANetwork.class,
        TestSAFileDownloader.class,
        TestSANetworkUtils.class,

        ////////////////////////////////
        // sautils
        TestSAUtils_RemoveAllButFirstElement.class,
        TestSAUtils_GenerateUniqueKey.class,
        TestSAUtils_MapSourceSizeIntoBoundingSize.class,
        TestSAUtils_RandomNumberBetween.class,
        TestSAUtils_EncodeDictAsJsonDict.class,
        TestSAUtils_EncodeURL.class,
        TestSAUtils_FindBaseURLFromResourceURL.class,
        TestSAUtils_FormGetQueryFromDict.class,
        TestSAUtils_GetCachebuster.class,
        TestSAUtils_IsJSONEmpty.class,
        TestSAUtils_IsValidEmail.class,
        TestSAUtils_IsValidURL.class,

        ////////////////////////////////
        // xml parser
        SAXMLParser_Tests1.class,
        SAXMLParser_Tests2.class,
        SAXMLParser_Tests3.class,
        SAXMLParser_Tests4.class,
        SAXMLParser_Tests5.class,
        SAXMLParser_Tests6.class,
        SAXMLParser_Tests7.class,
        SAVASTParser_Local_Tests1.class,
        SAVASTParser_Local_Tests2.class,
        SAVASTParser_Async_Test.class
})
public class TestSuite {
}
