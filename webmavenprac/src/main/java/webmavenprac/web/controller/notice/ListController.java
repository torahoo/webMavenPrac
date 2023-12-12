package webmavenprac.web.controller.notice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import webmavenprac.web.entity.Notice;
import webmavenprac.web.sevice.NoticeService;
import webmavenprac.web.sevice.jdbc.JDBCNoticeService;

public class ListController implements Controller {
	
	/* ========================================== *
	 * NoticeService를 사용하기 위한 Setter.
	 * Setter는 클래스 변수로만 존재할 수 있기에 메서드 밖에 선언한다.
	 * ========================================== */
	@Autowired 
	private NoticeService noticeService;
	
	/* ========================================== *
	 * @Autowired를 통해 servlet-context를 이용해 mapper를
	 * 지정하지 않아도 DI 주입이 가능해 진다.
	 * 위의 autowired로 setter 또한 불필요해짐
	 * ========================================== */
//	public void setNoticeService (NoticeService noticeService) {
//		this.noticeService = noticeService;
//		System.out.println("setNoticeService @Autowired Activated");
//	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ListController Activate");
		
		ModelAndView mv = new ModelAndView("notice.list");
		
		List<Notice> noticeLists = noticeService.getList(1, "title", "");
		mv.addObject("noticeLists", noticeLists);
		
		return mv;
	}
	
	

}
