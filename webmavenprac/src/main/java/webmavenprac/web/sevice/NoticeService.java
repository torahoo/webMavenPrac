package webmavenprac.web.sevice;

import java.sql.SQLException;
import java.util.List;

import webmavenprac.web.entity.Notice;

/* ================================================================ *
 * JDBC와 JPA같이 쓰려는 DB 클래스에 맞춰 바꿔주기 위한 interface.
 * 기본적으로 쓰는 CRUD를 넣어 줍니다. (spring boot jpa는 필요없음)
 * ctrl + shift + O 로 import된 클래스들 정리 가능
 * ================================================================ */

public interface NoticeService {

	List<Notice> getList(int page, String field, String query) throws ClassNotFoundException, SQLException;
	int getCount() throws ClassNotFoundException, SQLException;
	int insert(Notice notice) throws ClassNotFoundException, SQLException;
	int update(Notice notice) throws ClassNotFoundException, SQLException;
	int delete(int noticeId) throws ClassNotFoundException, SQLException;

}
