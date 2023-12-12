package webmavenprac.web.sevice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import webmavenprac.web.entity.Notice;
import webmavenprac.web.sevice.NoticeService;

/* ======================================*
 * https://luvris2.tistory.com/372		 *
 * 해당 티스토리를 참조하여 url및 mysql세팅을 하였습니다.*
 * 강의 db Oracle ==> MySQL로 변경			 *
 * ===================================== */
/* =========================================*
 * @Component 어노테이션을 활용해 service-context 의
 * NoticeService 빈 등록을 할 필요 없게 해준다.
 * 스캔 범위 지정 필요 --> 패키지 활용
 *  --> service-context 안의 
 *  context:component-scan에 의해 @Component가
 *  인식되게 되고 IoC컨테이너에 등록된다.
 *  context:annotation또한 쓸 필요 없게 된다.
 *  @Component = 구성요소이기에 무슨 역할을 하는지 모른다.
 *  @Controller, @Service, @Repository등을
 *  이용해 해당 클래스가 무슨 역할을 하는지 구분해줄 수 있다.
 * =========================================*/
@Component 
public class JDBCNoticeService implements NoticeService {
	
	/* ==================================================== *
	 * jdbc 설정을 위한 String 세팅
	 * mysql로 세팅헤 주었다.
	 * 세팅 중 반복되는 connection 때문에 클래스를 따로 뺌
	 * ==> JdbcConnectionService
	 * 따로 빼려 하였으나 https://androphil.tistory.com/436?category=464878
	 * 이 티스토리 글에 보면 보안적으로나 여러 사용자가 사용하는 환경에서나 쓰지 말아야할 경우에 해당되기에
	 * (connection들을 인스턴스 변수로 쓰고 사용할 경우 각 사용자의 thread만다 혼동될 경우의 수가 있기에
	 * 사용을 지양하도록 한다.)
	 * 포기하도록 하였다. 결국 connection은 매 메서드마다 그때그때 넣어주도록..
	 * 참고 : connection들을 인스턴스 변수 ==> 클래스에서 메서드 밖에서 private나 public으로
	 * 선언 할 경우를 뜻한다.
	 * url, id, password, driver 변경 시마다 이에 해당하는 변경점을 일일히 바꿔줘야 한다.
	 * 이를 피해주기 위해 xml에서 DI로 추가하여 하나하나 바꿔줄 필요 없게끔 바꿔준다.
	 * ===================================================== */
//	private String url = "jdbc:mysql://localhost:3306/springmav_newlac";
//	private String id = "practiceAccount";
//	private String password = "1234";
//	private String driver = "com.mysql.jdbc.Driver";
	
	/* ========================================================= *
	 * 위의 매개변수를 저장해 담을 수 있는 Interface = DataSource (javax.sql.DataSource)
	 * xml에서 바인딩 해 줄수 있는 객체를 준비함.
	 * ==> DataSource 로 Setter를 만들어 사용.
	 * ========================================================= */
	/* ========================================================= *
	 * @Autowired 사용을 위해 setter처리를 지우고 @Autowired만 남긴다.
	 * 이로서 xml에서 전부 처리해주어야 할 필요 없이 쉽게 property를 관리할 수 있다.
	 * ========================================================= */
	@Autowired
	private DataSource dataSource;
	
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

	public List<Notice> getList (int page, String field, String query) throws ClassNotFoundException, SQLException {
		/* =========================================== 	*
		 * List로 불러오는 데이터들의 페이징(Paging)을 위한 변수 선언	*
		 * start로 각 블록마다 첫 페이지 숫자를 결정해준다.				*
		 * end로 각 블록의 끝 숫자를 결정한다.						*
		 * =========================================== 	*/
		int start = 1+(page-1)*10; // 1, 11, 21, 31, ...
		int end = 10*page; // 10, 20, 30, ...
		
		String sql = "SELECT * FROM NOTICE_VIEW WHERE "+field+" LIKE ? AND NUM BETWEEN ? AND ? ";
		
		/*
		 * 위의 DataSource를 이용해 getConnection 을 얻을 수 있다.
		 */
//		Class.forName(driver);
//		Connection connection = DriverManager.getConnection(url, id, password);
		Connection connection = dataSource.getConnection(); 
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "%"+query+"%");
		preparedStatement.setInt(2, start);
		preparedStatement.setInt(3, end);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<Notice> noticeLists = new ArrayList<Notice>();
		
		while (resultSet.next()) {
			int id = resultSet.getInt("ID");
			String title = resultSet.getString("TITLE");
			String writerId = resultSet.getString("WRITER_ID");
			Date regDate = resultSet.getDate("REGDATE");
			String content = resultSet.getString("CONTENT");
			int hit = resultSet.getInt("hit");
			String files = resultSet.getString("FILES");
			Notice getNotice = new Notice(id, title, writerId, regDate, content, hit, files);
			noticeLists.add(getNotice);
		} // while (resultSet.next) end
		
		resultSet.close();
		preparedStatement.close();
		connection.close();
		
		return noticeLists;
	} // getList end
	
	//Scalar
	public int getCount() throws ClassNotFoundException, SQLException {
		int count = 0;
		String sql = "select count(id) count from notice";
		
		/* ============================================ *
		 * 위의 DataSource를 이용해 getConnection 을 얻을 수 있다.
		 * ============================================ */
//		Class.forName(driver);
//		Connection connection = DriverManager.getConnection(url, id, password);
		Connection con = dataSource.getConnection(); 
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next()) {
			count = rs.getInt("COUNT");
		}
		
		rs.close();
		st.close();
		con.close();
		
		return count;
	} // getCount end
	
	public int insert(Notice notice) throws SQLException, ClassNotFoundException {
		String title = notice.getTitle();
		String writerId = notice.getWriterId();
		String content = notice.getContent();
		String files = notice.getFiles();
		
		String sql = "insert into notice (title, wirter_id, content, files) valuse (?,?,?,?)";
		
		/* ============================================ *
		 * 위의 DataSource를 이용해 getConnection 을 얻을 수 있다.
		 * ============================================ */
//		Class.forName(driver);
//		Connection connection = DriverManager.getConnection(url, id, password);
		Connection con = dataSource.getConnection(); 
		
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setString(1, title);
		pstm.setString(2, writerId);
		pstm.setString(3, content);
		pstm.setString(4, files);
		
		int result = pstm.executeUpdate();
		
		pstm.close();
		con.close();
		
		return result;
	} // insert end
	
	public int update (Notice notice) throws SQLException, ClassNotFoundException {
		String title = notice.getTitle();
		String content = notice.getContent();
		String files = notice.getFiles();
		int noticeId = notice.getId();
		
		String sql = "update notice set title = ?, content = ?, files = ? where id = ?";
		
		/* ============================================ *
		 * 위의 DataSource를 이용해 getConnection 을 얻을 수 있다.
		 * ============================================ */
//		Class.forName(driver);
//		Connection connection = DriverManager.getConnection(url, id, password);
		Connection con = dataSource.getConnection(); 
		
		PreparedStatement pstm = con.prepareStatement(sql);
		
		pstm.setString(1, title);
		pstm.setString(2, content);
		pstm.setString(3, files);
		pstm.setInt(4, noticeId);
		
		int result = pstm.executeUpdate();
		
		pstm.close();
		con.close();
		
		return result;
	} // update end
	
	public int delete(int noticeId) throws ClassNotFoundException, SQLException {
		String sql = "delete notice where id=?";
		
		/* ============================================ *
		 * 위의 DataSource를 이용해 getConnection 을 얻을 수 있다.
		 * ============================================ */
//		Class.forName(driver);
//		Connection connection = DriverManager.getConnection(url, id, password);
		Connection con = dataSource.getConnection(); 
		
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, noticeId);
		
		int result = pstm.executeUpdate();
		
		pstm.close();
		con.close();
		
		return result;
	} // delete end

} // NoticeService end





















