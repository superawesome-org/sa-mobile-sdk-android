package tv.superawesome.lib.sajsonparser.mocks;

import org.json.JSONObject;

import java.util.List;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.sajsonparser.SAListToJson;

/**
 * Created by gabriel.coman on 18/10/16.
 */
public class SAMockEmployeeModel extends SABaseObject {
    public String name;
    public int age;
    public boolean isActive;
    public SAMockPositionModel position;
    public List<SAMockPositionModel> previous;

    public SAMockEmployeeModel(String name, int age, boolean isActive, SAMockPositionModel position, List<SAMockPositionModel> previous) {
        this.name = name;
        this.age = age;
        this.isActive = isActive;
        this.position = position;
        this.previous = previous;
    }

    public SAMockEmployeeModel(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void readFromJson(JSONObject json) {
        name = SAJsonParser.getString(json, "name");
        age = SAJsonParser.getInt(json, "age");
        isActive = SAJsonParser.getBoolean(json, "isActive");
        position = new SAMockPositionModel(SAJsonParser.getJsonObject(json, "position"));
        previous = SAJsonParser.getListFromJsonArray(json, "previous", new SAJsonToList<SAMockPositionModel, JSONObject>() {
            @Override
            public SAMockPositionModel traverseItem(JSONObject param) {
                return new SAMockPositionModel(param);
            }
        });
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject("name", name,
                "age", age,
                "isActive", isActive,
                "position", position,
                "previous", SAJsonParser.getJsonArrayFromList(previous, new SAListToJson<JSONObject, SAMockPositionModel>() {
                            @Override
                                public JSONObject traverseItem(SAMockPositionModel param) {
                                    return param.writeToJson();
                                }
                            }));
    }

    @Override
    public boolean isValid() {
        return name != null;
    }
}
