package webmavenprac.web.controller.customer;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import webmavenprac.web.entity.Notice;
import webmavenprac.web.sevice.NoticeService;

/* ====================================================== *
 * NoticeController 
 * --> controller.customer 패키지안에 Detail과 List를 합쳐
 * NoticeController로 한번에 관리하도록 한다.
 * 이는 WEB-INF안의 jsp파일들 즉, 서비스의 구분을 통해 패키지와 Controller를
 * 구분해서 만들도록 한다. 
 * ex) 	root폴더 --> HomeController
 * 		view.cutomer폴더 --> NoticeController 
 * 
 * Mapping에서 '/customer/notice/' 가 반복되니 이를 해결하고자
 * @Contorller 밑에 @RequestMapping으로 NoticeController 자체에
 * '/customer/notice/'를 적용시켜 메서드들의 url을 간편화 한다.
 * ====================================================== */

@Controller
@RequestMapping("/customer/notice/")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;

	@RequestMapping("list")
	public String list() throws ClassNotFoundException, SQLException {
		List<Notice> list = noticeService.getList(1, "TITLE", "");
		return "notice.list";
	}
	
	@RequestMapping("detail")
	public String detail() {
		return "notice.detail";
	}
	
}
