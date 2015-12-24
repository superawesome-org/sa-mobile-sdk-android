package tv.superawesome.lib.savast.savastparser.models;

import java.util.Iterator;
import java.util.List;

import tv.superawesome.lib.sautils.SALog;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTAd extends SAGenericVAST {

    public SAVASTAdType type;
    public String id;
    public String sequence;
    public List<String> Errors;
    public List<SAVASTImpression> Impressions;
    public List<SAVASTCreative> Creatives;

    @Override
    public void print() {
        SALog.Log(type.toString() +  " Ad("+id+")");
        SALog.Log("Sequence: " + sequence);
        SALog.Log("Errors[" + Errors.size() + "]");
        SALog.Log("Impressions[" + Impressions.size() + "]");
        SALog.Log("Creatives[" + Creatives.size() + "]");
        for (Iterator<SAVASTCreative> i = Creatives.iterator(); i.hasNext(); ) {
            SAVASTCreative item = i.next();
            item.print();
        }
    }

    public void sumAd(SAVASTAd ad){
        this.id = ad.id;
        this.sequence = ad.sequence;

        // add errors
        for (Iterator<String> i = ad.Errors.iterator(); i.hasNext(); ){
            this.Errors.add(i.next());
        }

        // add impressions
        for (Iterator<SAVASTImpression> i = ad.Impressions.iterator(); i.hasNext(); ){
            this.Impressions.add(i.next());
        }

        // add creatives
        for (Iterator<SAVASTCreative> i1 = this.Creatives.iterator(); i1.hasNext(); ){
            for (Iterator<SAVASTCreative> i2 = ad.Creatives.iterator(); i2.hasNext(); ){
                SAVASTLinearCreative creative1 = (SAVASTLinearCreative)i1.next();
                SAVASTLinearCreative creative2 = (SAVASTLinearCreative)i2.next();
                creative1.sumCreative(creative2);
            }
        }
    }
}
