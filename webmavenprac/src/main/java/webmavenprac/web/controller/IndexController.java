package webmavenprac.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/*
 * 모든 Controller는 함수를 정의하는 handleRequest를 사용해야 한다.
 */

public class IndexController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("IndexController Activate");
		
		/* ==============================================
		 * tiles.xml에서 index페이지를 위해 root.*을 썼기에 
		 * 여기서 반환되는 페이지도 index에서 root.index로 바꿔주도록 한다. 
		 * ============================================== */
		ModelAndView mv = new ModelAndView("root.index");
		mv.addObject("data", "Hello Spring MVC");
//		mv.setViewName("WEB-INF/view/index.jsp");
		
		return mv;
	}
	
}
