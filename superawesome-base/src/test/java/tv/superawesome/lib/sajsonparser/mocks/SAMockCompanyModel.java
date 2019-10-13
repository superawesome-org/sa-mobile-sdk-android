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
public class SAMockCompanyModel extends SABaseObject {
    public String name;
    public List<SAMockEmployeeModel> employees;

    public SAMockCompanyModel(String name, List<SAMockEmployeeModel> employees) {
        this.name = name;
        this.employees = employees;
    }

    public SAMockCompanyModel(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void readFromJson(JSONObject json) {
        name = SAJsonParser.getString(json, "name");
        employees = SAJsonParser.getListFromJsonArray(json, "employees", new SAJsonToList<SAMockEmployeeModel, JSONObject>() {
            @Override
            public SAMockEmployeeModel traverseItem(JSONObject param) {
                return new SAMockEmployeeModel(param);
            }
        });
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject("name", name,
                "employees", SAJsonParser.getJsonArrayFromList(employees, new SAListToJson<JSONObject, SAMockEmployeeModel>() {
                                @Override
                                public JSONObject traverseItem(SAMockEmployeeModel param) {
                                    return param.writeToJson();
                                }
                            }));
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
