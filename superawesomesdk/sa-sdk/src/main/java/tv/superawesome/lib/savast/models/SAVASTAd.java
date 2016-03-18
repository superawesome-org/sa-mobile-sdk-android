package tv.superawesome.lib.savast.models;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

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
        String printout = " \n";
        printout += type.toString() + " Ad(" + id + ")" + "\n";
        printout += "Sequence: " + sequence + "\n";
        printout += "Errors[" + Errors.size() + "]" + "\n";
        printout += "Impressions[" + Impressions.size() + "]" + "\n";
        printout += "Creatives[" + Creatives.size() + "]" + "\n";
        Log.d("SuperAwesome", printout);

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
