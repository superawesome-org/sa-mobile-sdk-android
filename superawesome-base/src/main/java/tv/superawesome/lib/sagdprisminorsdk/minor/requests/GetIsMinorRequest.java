package tv.superawesome.lib.sagdprisminorsdk.minor.requests;

import android.content.Context;

import org.json.JSONObject;

import tv.superawesome.lib.sagdprisminorsdk.minor.models.GetIsMinorModel;
import tv.superawesome.lib.sagdprisminorsdk.minor.network.HTTPMethod;
import tv.superawesome.lib.sagdprisminorsdk.minor.network.Service;
import tv.superawesome.lib.sagdprisminorsdk.minor.network.ServiceResponseInterface;
import tv.superawesome.lib.sagdprisminorsdk.minor.process.GetIsMinorInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

public class GetIsMinorRequest extends Service {

  private GetIsMinorInterface listener = null;
  private String bundleId;
  private String dateOfBirth;

  public GetIsMinorRequest(String bundleId, String dateOfBirth) {
    this.bundleId = bundleId;
    this.dateOfBirth = dateOfBirth;
  }

  public GetIsMinorRequest() {

    listener = isMinorModel -> {};
  }

  @Override
  public String getEndpoint() {
    return "v1/countries/child-age";
  }

  @Override
  public JSONObject getQuery() {
    return SAJsonParser.newObject("bundleId", bundleId, "dob", dateOfBirth);
  }

  @Override
  public JSONObject getHeader() {
    return SAJsonParser.newObject();
  }

  @Override
  public HTTPMethod getMethod() {
    return HTTPMethod.GET;
  }

  @Override
  public void success(int status, String payload, boolean success) {

    if (!success) {
      listener.getIsMinorData(null);
    } else {
      if (status == 200 && payload != null) {

        GetIsMinorModel isMinorModel = new GetIsMinorModel(payload);
        listener.getIsMinorData(isMinorModel);
      } else {
        listener.getIsMinorData(null);
      }
    }
  }

  @Override
  public void execute(
      Context context, String dateOfBirth, String bundleId, ServiceResponseInterface listener) {
    this.bundleId = bundleId;
    this.dateOfBirth = dateOfBirth;
    this.listener = listener != null ? (GetIsMinorInterface) listener : this.listener;
    super.execute(context, dateOfBirth, bundleId, this.listener);
  }
}
