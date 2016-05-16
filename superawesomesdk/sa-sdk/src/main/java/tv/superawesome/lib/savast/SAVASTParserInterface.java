package tv.superawesome.lib.savast;

import java.util.List;
import tv.superawesome.lib.savast.models.SAVASTAd;

/**
 * Public interface
 */
public interface SAVASTParserInterface {

    /**
     * Called when the parser has successfully parsed a VAST tag
     * @param ad - returns (as a callback parameter) a
     */
    void didParseVAST(SAVASTAd ad);
}
