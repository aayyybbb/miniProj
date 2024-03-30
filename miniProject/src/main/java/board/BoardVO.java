package board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	public BoardVO(String id, String title, String content, String date, String writer) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.writer = writer;
	}

	private String id;
	private String title;
	private String content;
	private String date;
	private String memberId;
	private int viewCount;

	private String searchKey;
	private String writer;
	private String action;
}
