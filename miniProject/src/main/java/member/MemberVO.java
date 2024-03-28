package member;

import java.util.List;

import hobby.HobbyVO;
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
	private HobbyVO hobbyVO;
	private List<HobbyVO> hobbyVOList;
	private String action;
	private String searchKey;

	public MemberVO(String id, String pwd, String name, String addr, String phone, String gender) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.addr = addr;
		this.phone = phone;
		this.gender = gender;
	}

	public boolean isEmptySearchKey() {
		return searchKey == null || searchKey.length() == 0;
	}

	public MemberVO(String id, String pwd, String name, String addr, String phone, String gender, HobbyVO hobbyVO) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.addr = addr;
		this.phone = phone;
		this.gender = gender;
		this.hobbyVO = hobbyVO;
	}

}
