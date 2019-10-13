package tv.superawesome.lib.sajsonparser.mocks;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 18/10/16.
 */
public class SAMockPositionModel extends SABaseObject {
    public String name;
    public int salary;

    public SAMockPositionModel() {

    }

    public SAMockPositionModel(String name, int salary) {
        super();
        this.name = name;
        this.salary = salary;
    }

    public SAMockPositionModel(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void readFromJson(JSONObject json) {
        name = SAJsonParser.getString(json, "name");
        salary = SAJsonParser.getInt(json, "salary");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject("name", name,
                "salary", salary);
    }

    @Override
    public boolean isValid() {
        return name != null;
    }
}
