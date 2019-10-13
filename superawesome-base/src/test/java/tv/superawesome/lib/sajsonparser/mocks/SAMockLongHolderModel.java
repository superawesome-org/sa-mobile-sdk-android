package tv.superawesome.lib.sajsonparser.mocks;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 11/11/16.
 */
public class SAMockLongHolderModel extends SABaseObject {

    public int val1;
    public long val2;

    public SAMockLongHolderModel(int val1, long val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public SAMockLongHolderModel(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void readFromJson(JSONObject json) {
        val1 = SAJsonParser.getInt(json, "val1");
        val2 = SAJsonParser.getLong(json, "val2");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject("val1", val1,
                "val2", val2);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
