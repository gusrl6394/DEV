package foodvisor.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import foodvisor.domain.FoodvisorVO;
import foodvisor.domain.Paging;
import foodvisor.service.FoodvisorService;

@Controller
@SessionAttributes({"foodvisorVO","paging"})
@RequestMapping(value="/foodvisor")
public class FoodvisorController{
	private static final String noimagefilename = "no-image.jpg"; // 기본이미지 이름.확장자
	private static final String resourcesimagepath = "/resources/foodvisor/images"; // 이미지 업로드 위치
	private FoodvisorService foodvisorService;
	public void setFoodvisorService(FoodvisorService foodvisorService) {
		this.foodvisorService = foodvisorService;
	}
	
	// 패스워드 메소드(AJAX통신)
	@ResponseBody
	@RequestMapping(value="/pwd/{seq}", method=RequestMethod.POST)
	public String pwdconfirm(@PathVariable int seq, @RequestParam(value="pwd") String newpwd) {
		System.out.println("pwdconfirm controller 실행완료");
		String result = foodvisorService.pwdconfirm(seq);
		System.out.println("result:"+result+","+"newpwd:"+newpwd);
		if(result.equals(newpwd)) {
			return "ok";
		}		
		return "reject";		
	}
	
	// 리스트 호출
	@RequestMapping(value="/foodvisorReviewlist")
	public String list(@RequestParam(value="currentPage", required=false) String currentPage, Model model) {
		System.out.println("list controller 실행완료");
		// 페이지 관련 처리
		Paging paging = new Paging();
		paging.setTotalcount(foodvisorService.totalcount());
		System.out.println("currentPage:"+currentPage);
		if(currentPage != null) {
			int temp;
			try {
				temp = Integer.parseInt(currentPage);
				paging.setCurrentPage(temp);
			}catch(Exception e) {
				paging.setCurrentPage(1);
				System.out.println(e.getMessage());
			}			
		}		
		paging.makePaging();
		System.out.println(paging.toString());
		
		List<FoodvisorVO> list = null;
		list =  foodvisorService.paging(paging);
		// 미리보기 이미지 호출을 위해 
		String[] str = null;
		for(int i=0; i<list.size(); i++) {
			// 이미지 여러개인경우 , 기준으로 배열생성
			str = list.get(i).getImg().split(",");
			try {
				// 첫번째 이미지를 미리보기 이미지로 변경
				String Path = str[0];
				list.get(i).setPreviewimg(Path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("foodvisorVO", list);
		model.addAttribute("paging", paging);
		return "/foodvisor/foodvisorReviewlist";
	}
	// 파일업로드 관련 참고사이트
	// http://androphil.tistory.com/329
	// 파일 읽기 메소드
	@RequestMapping(value="/foodvisorReviewRead", method=RequestMethod.GET)
	public String reviewRead(Model model, 
			@RequestParam(value="currentPage", required=true) @PathVariable int currentPage, 
			@RequestParam(value="seq", required=true) @PathVariable int seq) { 	
		System.out.println("reviewRead controller 실행완료");
		// 조회수 증가
		foodvisorService.updateReviewcnt(seq);
		// 게시글 읽기
		FoodvisorVO foodvisorVO = foodvisorService.reviewread(seq);
		// 이미지 가져오기
		String[] str =  new String[] {""};
		// 여러 이미지인경우 if문안 실행, 아니면 else문 실행
		if(foodvisorVO.getImg().lastIndexOf(",") != -1) {
			str =  foodvisorVO.getImg().split(",");
			for(int i=0; i<str.length; i++) {
				str[i] = str[i].trim();
			}
		}else {
			str[0] = foodvisorVO.getImg();			
		}
		foodvisorVO.setImgarr(str);
		model.addAttribute("foodvisorVO", foodvisorVO);
		model.addAttribute("currentPage", currentPage);
		return "/foodvisor/foodvisorReviewRead";
	}
	// 게시글 수정시 동작 되는 메소드 GET
	@RequestMapping(value="/foodvisorReviewedit", method=RequestMethod.GET)
	public String editRead(Model model,
			@RequestParam(value="currentPage", required=true) @PathVariable int currentPage, 
			@RequestParam(value="seq", required=true) @PathVariable int seq) {
		System.out.println("editRead controller GET 실행완료");
		FoodvisorVO foodvisorVO = foodvisorService.reviewread(seq);
		String[] str =  new String[] {""};
		// 여러 이미지인경우 if문안 실행, 아니면 else문 실행
		if(foodvisorVO.getImg().lastIndexOf(",") != -1) {
			str =  foodvisorVO.getImg().split(",");
			for(int i=0; i<str.length; i++) {
				str[i] = str[i].trim();
			}
		}else {
			str[0] = foodvisorVO.getImg();
			// 첫이미지가 noimage이면 null로 처리
			if(str[0].equalsIgnoreCase(noimagefilename)) {
				str=null;
			}
		}
		foodvisorVO.setImgarr(str);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("foodvisorVO", foodvisorVO);
		return "/foodvisor/foodvisorReviewEdit";
	}	
	// 게시글 수정시 동작 되는 메소드 POST
	@RequestMapping(value="/foodvisorReviewedit", method=RequestMethod.POST)
	public @ResponseBody String editRead(@Valid FoodvisorVO foodvisorVO, HttpServletRequest request,
			@RequestParam(value="currentPage", required=true) @PathVariable int currentPage, 
			@RequestParam(value="seq", required=true) @PathVariable int seq) throws IOException {
		System.out.println("editRead controller POST 실행완료");
		String oldpwd = foodvisorService.pwdconfirm(seq);
		String newpwd = foodvisorVO.getPwd();
		if(!oldpwd.equals(newpwd)) {
			return "200OK"; // 비번틀리지만 위조해서 보낸거이므로 200OK신호 보내 착각하게 만들기
		}
		ServletContext context = request.getServletContext();
        String uploadFilePath = context.getRealPath(resourcesimagepath);
        
        // 아래것은 함수로 정리는 가능하나 다음에 시도
        
		// 클라이언트로 부터 FILE 을 안누를시
		if(foodvisorVO.getImgPath() == null) {		
			// 기존에 이미지 전부 삭제한경우
			if(foodvisorVO.getImg().equalsIgnoreCase(noimagefilename)) {
				// 기존 그림 삭제 진행
	            FoodvisorVO oldfoodvisorVO = foodvisorService.reviewread(seq);
	            String[] oldimage = null;
	            // 기존이미지가 여러개인경우
	            if(oldfoodvisorVO.getImg().lastIndexOf(",") != -1) {
	            	oldimage =  oldfoodvisorVO.getImg().split(",");
	    			for(int i=0; i<oldimage.length; i++) {
	    				oldimage[i] = oldimage[i].trim();    				
	    				String path = uploadFilePath + File.separator + oldimage[i];
	        			File file = new File(path);
	        			if(file.exists()){
	        	            if(file.delete()){
	        	                System.out.println("파일삭제 성공1");
	        	            }else{
	        	            	System.out.println(path);
	        	                System.out.println("파일삭제 실패1");
	        	            }
	        	        }else{
	        	        	System.out.println(path);
	        	            System.out.println("파일이 존재하지 않습니다.1");
	        	        }
	    			}
	    			
	    		}else { // 기존이미지가 1개인경우
	    			// 이미 이미지가 없다고 선언하였으로 스킵
	    			if(oldfoodvisorVO.getImg().equalsIgnoreCase(noimagefilename)) {
						System.out.println("기본이미지");					
	    			}else { // 기존이미지 1개 삭제 진행
		    			String path = uploadFilePath + File.separator + oldfoodvisorVO.getImg();
		    			File file = new File(path);
		    			if(file.exists()){
		    	            if(file.delete()){
		    	                System.out.println("파일삭제 성공2");
		    	            }else{
		    	            	System.out.println(path);
		    	                System.out.println("파일삭제 실패2");
		    	            }
		    	        }else{
		    	        	System.out.println(path);
		    	            System.out.println("파일이 존재하지 않습니다.2");
		    	        }
	    			}
	    		}
			}else { // 기존 여러 이미지중 일부만 삭제된경우	
				FoodvisorVO oldfoodvisorVO = foodvisorService.reviewread(seq);
				String[] oldimage = oldfoodvisorVO.getImg().split(",");
	            String[] newimage = foodvisorVO.getImg().split(",");
	            for(int i=0; i<oldimage.length; i++) {
    				oldimage[i] = oldimage[i].trim(); 
	            }
	            for(int i=0; i<newimage.length; i++) {
	            	newimage[i] = newimage[i].trim(); 
	            }
	            List<String> oldimagelist = new ArrayList<String>();
	            Collections.addAll(oldimagelist, oldimage);
	            List<String> newimagelist = new ArrayList<String>();
	            Collections.addAll(newimagelist, newimage);
	            // 기존이미지 - 남아있는 이미지 => 삭제할이미지들
	            for(int i=0; i<newimagelist.size(); i++) {
	            	oldimagelist.remove(newimagelist.get(i));
	            }
	            if(oldimagelist != null && oldimagelist.size() > 0) {
	            	for(int i=0; i<oldimagelist.size(); i++) {
	            		if(oldimagelist.get(i) != "" && oldimagelist.get(i) != null) {
		            		String path = uploadFilePath + File.separator + oldimagelist.get(i);
		        			File file = new File(path);
		        			if(file.exists()){
		        	            if(file.delete()){
		        	                System.out.println("파일삭제 성공7");
		        	            }else{
		        	            	System.out.println(path);
		        	                System.out.println("파일삭제 실패7");
		        	            }
		        	        }else{
		        	        	System.out.println(path);
		        	            System.out.println("파일이 존재하지 않습니다.7");
		        	        }
	            		}
	            	}
	            }
			}
			foodvisorService.Reviewedit(foodvisorVO);
		}else { // 클라이언트로 부터 새이미지 요청시
			List<MultipartFile> files = foodvisorVO.getImgPath();
			List<String> fileNames = new ArrayList<String>();			
            // 기존 그림 삭제 진행
            FoodvisorVO oldfoodvisorVO = foodvisorService.reviewread(seq);
            String[] oldimage = null;
            // 기존그림이 여러개인경우
            if(oldfoodvisorVO.getImg().lastIndexOf(",") != -1) {
            	oldimage =  oldfoodvisorVO.getImg().split(",");
    			for(int i=0; i<oldimage.length; i++) {
    				oldimage[i] = oldimage[i].trim();    				
    				String path = uploadFilePath + File.separator + oldimage[i];
        			File file = new File(path);
        			if(file.exists()){
        	            if(file.delete()){        	            	
        	                System.out.println("파일삭제 성공3");
        	            }else{
        	            	System.out.println(path);
        	                System.out.println("파일삭제 실패3");
        	            }
        	        }else{
        	        	System.out.println(path);
        	            System.out.println("파일이 존재하지 않습니다.3");
        	        }
    			}
    		}else { // 기존그림이 1개인경우
    			// 처음에 no-image였다가 새로운 이미지 요청시, no-image삭제방지
    			if(oldfoodvisorVO.getImg().equalsIgnoreCase(noimagefilename)) {
					System.out.println("기본이미지");					
    			}else {
	    			String path = uploadFilePath + File.separator + oldfoodvisorVO.getImg();
	    			File file = new File(path);
	    			if(file.exists()){
	    	            if(file.delete()){
	    	                System.out.println("파일삭제 성공4");
	    	            }else{
	    	            	System.out.println(path);
	    	                System.out.println("파일삭제 실패4");
	    	            }
	    	        }else{
	    	        	System.out.println(path);
	    	            System.out.println("파일이 존재하지 않습니다.4");
	    	        }
    			}
    		}
			if (null != files && files.size() > 0) {
				for (MultipartFile multipartFile : files) {
					int opint = multipartFile.getOriginalFilename().lastIndexOf(".");
					String op =multipartFile.getOriginalFilename().substring(opint);
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String fileName = uuid + "_" + System.currentTimeMillis() + op;					
					String path = uploadFilePath + File.separator + fileName;
					File file = new File(path);
					multipartFile.transferTo(file);
					fileNames.add(fileName);
				}
			}
			String fileNameslen = fileNames.toString();
			foodvisorVO.setImg(fileNameslen.substring(1,fileNameslen.length()-1));
			foodvisorService.Reviewedit(foodvisorVO);
		}
		System.out.println("editRead POST 실행완료");
		return "200OK";
	}
	// 좋아요 버튼누를시 진행되는 메소드(AJAX통신)
	@RequestMapping(value = "/foodvisorReview/up", method = RequestMethod.GET)
	public @ResponseBody int ajaxreviewlike(@RequestParam("number") int seq) {
		System.out.println("ajaxreviewlike controller GET 실행완료");
		foodvisorService.updateReviewlike(seq);
		FoodvisorVO foodvisorVO = foodvisorService.reviewread(seq);
		int num = foodvisorVO.getLikecnt();
		return num;
	}
	// 새글작성 메소드 GET
	@RequestMapping(value="/foodvisorReviewNew", method=RequestMethod.GET)
	public String regist(Model model) {
		System.out.println("regist controller GET 실행완료");
		model.addAttribute("foodvisorVO", new FoodvisorVO());
		return "/foodvisor/foodvisorReviewNew";
	}
	// 새글작성시 메소드 POST
	@RequestMapping(value="/foodvisorReviewNew", method=RequestMethod.POST)
	public String regist(FoodvisorVO foodvisorVO, BindingResult bindingResult, HttpServletRequest request) throws IOException {
		System.out.println("regist controller POST 실행완료");
		List<MultipartFile> files = foodvisorVO.getImgPath();
		List<String> fileNames = new ArrayList<String>();
		// 파일이 null 아니면서 file 사이즈가 1인경우
		// 클라이언트측에서 기본이미지 채택시 빈 파일로 1개 전송을 보내서 받음, 이경우 아래 if문 실행
		if(files != null && files.size() == 1) {
			// 기본이미지 파일이름인지 다시 확인
			if(files.get(0).getOriginalFilename().equalsIgnoreCase(noimagefilename)) {
				System.out.println("기본이미지 if문 실행완료");				
				fileNames.add(noimagefilename);
				String fileNameslen = fileNames.toString();	
				// 기본이미지 이름을 setImg처리
				foodvisorVO.setImg(fileNameslen.substring(1,fileNameslen.length()-1));
				// DB INSERT
				foodvisorService.Reviewwrite(foodvisorVO);
				return "redirect:/foodvisor/foodvisorReviewlist";
			}
		}
		// 파일이 null 아니고 이미지가 1개인경우 기본이미지가 아닌경우, 또는 이미지가 2개이상인경우 실행
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				System.out.println("originFileName : " + multipartFile.getOriginalFilename());				
				int opint = multipartFile.getOriginalFilename().lastIndexOf(".");
				// op는 확장자 추출
				String op =multipartFile.getOriginalFilename().substring(opint);
				// UUID 생성 (단, - 가 중간에 생성된것이지만 replace로 빈칸으로 대체
				String uuid = UUID.randomUUID().toString().replace("-", "");
				// 최종 DB저장될 파일이름 = UUID_시스템시간.확장자
				String fileName = uuid + "_" + System.currentTimeMillis() + op;
	            ServletContext context = request.getServletContext();
	            // src -> resources -> foodvisor -> images 로 업로드 패치 진행
	            String uploadFilePath = context.getRealPath(resourcesimagepath);
				// 패치 = 업로드폴더이름/파일이름.확장자
	            String path = uploadFilePath + File.separator + fileName;
				// 파일 생성
	            File file = new File(path);
				// 파일쓰기
				multipartFile.transferTo(file);
				// DB에 저장할 이미지파일이름 add
				fileNames.add(fileName);
			}
		}
		String fileNameslen = fileNames.toString();		
		// toString 할시 양쪽끝에 있는 [ ] 제거 진행
		foodvisorVO.setImg(fileNameslen.substring(1,fileNameslen.length()-1));
		foodvisorService.Reviewwrite(foodvisorVO);
		return "redirect:/foodvisor/foodvisorReviewlist";
	}
	
	// 게시글 삭제 
	@ResponseBody
	@RequestMapping(value="/foodvisorReviewdelete/{seq}", method=RequestMethod.POST)
	public String Reviewdelete(@PathVariable int seq, @RequestParam(value="pwd") String newpwd, HttpServletRequest request) {
		System.out.println("Reviewdelete controller POST 실행완료");
		// 패스워드 맞는지 확인
		String oldpwd = foodvisorService.pwdconfirm(seq);
		if(!oldpwd.equals(newpwd)) {
			return "200OK"; // 비번틀리지만 위조해서 보낸거이므로 200OK신호 보내 착각하게 만들기
		}
		// 기존 그림 삭제 진행
        FoodvisorVO oldfoodvisorVO = foodvisorService.reviewread(seq);
        String[] oldimage = null;
        ServletContext context = request.getServletContext();
        String uploadFilePath = context.getRealPath(resourcesimagepath);
        // 삭제할 파일이 여러개인경우 if문 실행, 1개인경우 else문 실행
        if(oldfoodvisorVO.getImg().lastIndexOf(",") != -1) {
        	oldimage =  oldfoodvisorVO.getImg().split(",");
			for(int i=0; i<oldimage.length; i++) {
				oldimage[i] = oldimage[i].trim();    				
				String path = uploadFilePath + File.separator + oldimage[i];
    			File file = new File(path);
    			if(file.exists()){
    	            if(file.delete()){
    	                System.out.println("파일삭제 성공5");
    	            }else{
    	            	System.out.println(path);
    	                System.out.println("파일삭제 실패5");
    	            }
    	        }else{
    	        	System.out.println(path);
    	            System.out.println("파일이 존재하지 않습니다.5");
    	        }
			}
		}else {
			// 기본이미지인경우 파일삭제 진행하지 않음.
			if(oldfoodvisorVO.getImg().equalsIgnoreCase(noimagefilename)) {
				System.out.println("기존파일에 해당되므로 스킵");					
			}else {
    			String path = uploadFilePath + File.separator + oldfoodvisorVO.getImg();
    			File file = new File(path);
    			if(file.exists()){
    	            if(file.delete()){
    	                System.out.println("파일삭제 성공6");
    	            }else{
    	            	System.out.println(path);
    	                System.out.println("파일삭제 실패6");
    	            }
    	        }else{
    	        	System.out.println(path);
    	            System.out.println("파일이 존재하지 않습니다.6");
    	        }
			}
		}
        // DB 삭제
		foodvisorService.Reviewdelete(seq);
		return "200OK";
	}
}
