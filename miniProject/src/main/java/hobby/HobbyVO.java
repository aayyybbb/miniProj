package hobby;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HobbyVO {
	public HobbyVO(String hobby, String hobbyId) {
		this.hobby = hobby;
		this.hobbyId = hobbyId;
	}

	private String hobby;
	private String hobbyId;

	private List<String> hobbyIdList;

}
