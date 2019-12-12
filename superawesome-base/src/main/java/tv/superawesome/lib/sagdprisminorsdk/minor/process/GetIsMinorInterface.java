package tv.superawesome.lib.sagdprisminorsdk.minor.process;

import tv.superawesome.lib.sagdprisminorsdk.minor.models.GetIsMinorModel;
import tv.superawesome.lib.sagdprisminorsdk.minor.network.ServiceResponseInterface;

public interface GetIsMinorInterface extends ServiceResponseInterface {
    void getIsMinorData(GetIsMinorModel isMinorModel);
}
