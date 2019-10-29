package tv.superawesome.sagdprisminorsdk.minor.process;

import tv.superawesome.sagdprisminorsdk.minor.models.GetIsMinorModel;
import tv.superawesome.sagdprisminorsdk.minor.network.ServiceResponseInterface;

public interface GetIsMinorInterface extends ServiceResponseInterface{
    void getIsMinorData(GetIsMinorModel isMinorModel);
}
