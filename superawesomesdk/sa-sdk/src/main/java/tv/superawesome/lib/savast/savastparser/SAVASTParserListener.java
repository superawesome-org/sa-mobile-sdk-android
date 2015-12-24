package tv.superawesome.lib.savast.savastparser;

import java.util.List;

import tv.superawesome.lib.savast.savastparser.models.SAVASTAd;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public interface SAVASTParserListener {

    void didParseVASTAndHasResponse(List<SAVASTAd> ads);
    void didNotFindAnyValidAds();
    void didFindInvalidVASTResponse();
}
