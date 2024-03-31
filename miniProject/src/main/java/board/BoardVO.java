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

	public BoardVO(String id, String title, String content, String name, String date, int viewCount) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = name;
		this.date = date;
		this.viewCount = viewCount;
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
