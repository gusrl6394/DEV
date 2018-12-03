package Main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import basic.BasicBoardDto;

@Controller
@RequestMapping("/Main")
public class MainPage {
	private MainDB maindb;
	
	public MainPage(MainDB maindb) {
		this.maindb = maindb;
	}
		@RequestMapping("/home")
		public String home(Model model) {
			maindb.setList();
			model.addAttribute("boardlist", maindb.getDBs());
			return "Main/Home";
		}
		@RequestMapping("/login")
		public String login() {
			return "Main/Login";
		}
		@RequestMapping("/createLogin")
		public String CreatLogin() {
			return "Main/CreateLogin";
		}
		
		@RequestMapping("/menuBar")
		public String MenuBar() {
			return "Main/MenuBar";
		}
		
		//커멘드 객체를 사용해서 테이블 정보를 주도록.
		@RequestMapping("/hitView")
		public String Hit(Model model) {
			System.out.println("===================== hit view 지역 " +   maindb.getHitList().size());
			for(BasicBoardDto dto : maindb.getHitList()){
				System.out.println(dto.getNum() + ":" + dto.getSubject());
			}
			model.addAttribute("hit", maindb.getHitList());
			return "Main/HitView";
		}
		@RequestMapping("/mainView")
		public String MainView(Model model) {
			System.out.println("===================== Main view 지역 " +   maindb.getDBcount());
			System.out.println("===================== Main view 지역 " +   maindb.getRecentList().size());
			for(BasicBoardDto dto : maindb.getRecentList()){
				System.out.println(dto.getNum() + ":" + dto.getSubject());
			}
			model.addAttribute("Recent", maindb.getRecentList());
			return "Main/MainView";
		}
}
