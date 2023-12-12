package webmavenprac.web.entity;

import java.util.Date;

public class Notice {

	private int id;
	private String title;
	private String writerId;
	private Date regDate;
	private String content;
	private int hit;
	private String files;
	
	public Notice() {}
	
	/*
	 * spring boot 의 @Builder 같은 역할을 해주는 생성자 
	 * (물론, builder와는 다르기에 이 생성자를 쓰려면 변수 모두 입력이 되어야 한다.)
	 */
	public Notice (int id, String title, String writerId, Date regDate, String content, int hit, String files) {
		this.id = id;
		this.title = title;
		this.writerId = writerId;
		this.regDate = regDate;
		this.content = content;
		this.hit = hit;
		this.files = files;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
}
