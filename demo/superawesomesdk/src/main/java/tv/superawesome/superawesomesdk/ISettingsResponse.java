package tv.superawesome.superawesomesdk;

import java.util.List;

import tv.superawesome.superawesomesdk.model.Placement;
import tv.superawesome.superawesomesdk.model.Preroll;

public interface ISettingsResponse {
	void receivedConfiguration(List<Placement> placements, List<Preroll> prerolls);
}
