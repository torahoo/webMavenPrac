package webmavenprac.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/* ====================================================== *
 * 모든 Controller는 함수를 정의하는 handleRequest를 사용해야 한다.
 * handleRequest를 dispatcher-servlet.xml에서 
 * servlet-context.xml로 옮겼음.
 * servlet-context의 indexController 빈 등록한 것을 
 * content:component-scan을 이용하여 @Controller로 대체한다.
 * --> implements Controller를 삭제한다.
 * 에러가 뜨는데 이는 
 * import org.springframework.web.servlet.mvc.Controller;
 * 때문에 생기는 에러로 ctrl+shift+O 로 정리할 수 있지만 위 import는 지워지지
 * 않으므로 직접 지운다.
 * --> import org.springframework.stereotype.Controller;
 * 가 옳은 import 객체임.
 * ====================================================== */
/* ================================================================== *
 * @RequestMapping을 통해 URL을 엮어주면서 Index 이외에 Help도 같이 넣어줄 건데 		  *
 * 이는 IndexController라 지칭을 하기엔 범위가 좁으므로 폴더 명으로 바꿔주도록 한다. 		  *
 * IndexController --> RootController (index, help 등 모두 root 폴더 안에 	  *
 * 있으므로. --> Root 대신 Home이라 지칭									  *
 * ================================================================== */
@Controller
@RequestMapping("/")
public class HomeController {

	/* ================================================================== *
	 * implements Controller 를 제거하였으니 Override된 handleRequest는 주석 처리한다. *
	 * ================================================================== */
//	@Override
//	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("IndexController Activate");
//		
//		/* ==============================================
//		 * tiles.xml에서 index페이지를 위해 root.*을 썼기에 
//		 * 여기서 반환되는 페이지도 index에서 root.index로 바꿔주도록 한다. 
//		 * ============================================== */
//		ModelAndView mv = new ModelAndView("root.index");
//		mv.addObject("data", "Hello Spring MVC");
////		mv.setViewName("WEB-INF/view/index.jsp");
//		
//		return mv;
//	}
	
	@RequestMapping("index")
	public String index() {
		/* ================================ *
		 * 그냥 실행할 시 오류가 발생한다.				*
		 * 설정 추가 필요. 						*
		 * servlet-context 에				*
		 * <mvc:annotation-driven /> 		*
		 * 추가한다. --> 사용자 요청을 처리			*
		 * 없을시엔  @RequestMapping("/index")	*
		 * 인식되지 않는다.						*
		 * ================================ */
		System.out.println("index Activate");
		return "root.index";
	}
	
}
