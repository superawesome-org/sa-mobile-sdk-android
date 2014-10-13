package tv.superawesome.mobile;

import java.util.List;

public interface ISettingsResponse {
	void receivedConfiguration(List<Placement> placements, List<Preroll> prerolls);
}
