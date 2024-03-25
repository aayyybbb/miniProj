package member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

	private String id;
	private String pwd;
	private String name;
	private String addr;
	private String phone;
	private String gender;
}
