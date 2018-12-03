package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class ServertoUser implements Runnable {	
	Socket soc = null;
	BufferedReader in = null;
	PrintWriter pw = null;
	private String id;	
	Thread servertouserth;

	public void sendMeg(String str) {
		System.out.println("Server to User send msg : " + str);
		pw.print(str);
		pw.flush();
	}
	public HashMap<Socket, String> listadd(){
		HashMap<Socket, String> h = new HashMap<Socket, String>();
		h.put(soc, getId());
		return h;
	}	
	public void infofirstadd(String str) throws IOException, InterruptedException {
		String geid = getId();
		int num = ServerMain.user.size();
		Set set = null;
		Iterator iterator = null;
		Map.Entry entry = null;
		if(num==0) {
			pw.println(""); pw.flush(); pw.println(""); pw.flush();
			pw.println("olduser/"+geid);
			pw.flush();
			/*pw.println("listupdate/"); pw.flush();
			pw.println("listupdate/"); pw.flush();*/
		}
		if(num>0) { // 기존사용자에게  새유저 알림
			set = ServerMain.user.entrySet();
			iterator = set.iterator();
			while(iterator.hasNext()){
				entry = (Map.Entry)iterator.next();
				Socket key = (Socket)entry.getKey();
				String value = (String)entry.getValue();
				PrintWriter pwa = new PrintWriter(key.getOutputStream());				  
				System.out.println("새유저 발생 알림메시지!! : " + key.toString());
				pwa.println(""); pwa.flush(); pwa.println(""); pwa.flush();
				pwa.println("newuser/"+ geid);
				pwa.flush();
			}
			set = ServerMain.user.entrySet();
			iterator = set.iterator();
			while(iterator.hasNext()){
				entry = (Map.Entry)iterator.next();
				Socket key = (Socket)entry.getKey();
				String value = (String)entry.getValue();
				PrintWriter pwb = new PrintWriter(soc.getOutputStream());
				pwb.println(""); pwb.flush(); pwb.println(""); pwb.flush();
				pwb.println("olduser/"+ value);
				pwb.flush();
			}
		}		

		ServerMain.user.put(soc, geid);	
		set = ServerMain.user.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()){
			entry = (Map.Entry)iterator.next();
			Socket key = (Socket)entry.getKey();
			PrintWriter pwc = new PrintWriter(key.getOutputStream());	
			pwc.println(""); pwc.flush(); pwc.println(""); pwc.flush();
			pwc.println("listupdate/"); pwc.flush();
			pwc.println("listupdate/"); pwc.flush();
		}
	}
	private void boardmsg(String str) throws IOException {
		StringTokenizer st = new StringTokenizer(str, "/");
		st.nextToken(); // 앞 프로토콜 자르기
		String ID = st.nextToken();
		String note = st.nextToken();
		Set set = ServerMain.user.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry entry = (Map.Entry)iterator.next();
			Socket key = (Socket)entry.getKey();
			if(soc != key) {
				PrintWriter pwa = new PrintWriter(key.getOutputStream());	
				pwa.println("newmsg/"+ ID + "/" + note);
				pwa.flush();
			}
		}
	}
	private void close(String str) throws IOException {
		StringTokenizer st = new StringTokenizer(str, "/");
		st.nextToken(); // 앞 프로토콜 자르기
		String ID = st.nextToken();
		Set set = null;
		Iterator iterator = null;
		Map.Entry entry = null;

		set = ServerMain.user.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()){
			entry = (Map.Entry)iterator.next();
			Socket key = (Socket)entry.getKey();
			String value = (String)entry.getValue();
			if(value.equals(ID)) {
				ServerMain.user.remove(key);
				break;
			}
		}		
		set = ServerMain.user.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()){
			entry = (Map.Entry)iterator.next();
			Socket key = (Socket)entry.getKey();
			PrintWriter pwd = new PrintWriter(key.getOutputStream());
			pwd.println(""); pwd.flush(); pwd.println(""); pwd.flush();
			pwd.println("removeuser/"+ID);
			pwd.flush();
			pwd.println("listupdate/"); pwd.flush();
			pwd.println("listupdate/"); pwd.flush();
		}

	}
	private void idsearch(String str) throws IOException {
		StringTokenizer st = new StringTokenizer(str, "/");
		st.nextToken(); // 앞 프로토콜 자르기
		String ID = st.nextToken();
		String keytostring = null;
		Set set = null;
		Iterator iterator = null;
		Map.Entry entry = null;
		set = ServerMain.user.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()){
			entry = (Map.Entry)iterator.next();
			Socket key = (Socket)entry.getKey();
			String value = (String)entry.getValue();
			if(value.equals(ID)) {
				PrintWriter pwe = new PrintWriter(key.getOutputStream());	
				pwe.println(""); pwe.flush(); pwe.println(""); pwe.flush();
				pwe.println("fileready/"); pwe.flush();				
				keytostring = key.getInetAddress().getHostAddress();
				break;
			}
		}
		pw.println(""); pw.flush(); pw.println(""); pw.flush();
		pw.println("socket/"+keytostring); pw.flush();
	}
	private String check(String str) {
		StringTokenizer st = new StringTokenizer(str, "/");
		st.nextToken(); // 앞 프로토콜 자르기
		String compare = st.nextToken(); // 아이디 확인
		String pwconfirm = null; // 기본 null
		pwconfirm = ServerMain.hmap.get(compare); // 등록여부
		String log = null;
		if(pwconfirm != null) {
			if(ServerMain.user.size() > 0) {
				Set set = ServerMain.user.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()){
					Map.Entry entry = (Map.Entry)iterator.next();
					//Socket key = (Socket)entry.getKey();	
					String value = (String)entry.getValue();
					if(value.equals(compare)) {
						System.out.println("compare : " + compare);
						System.out.println("user.get(compare) : " + ServerMain.user.get(compare));
						return "overlap";
					}
				}
			}
			// 패스워드 일치여부
			String pw = st.nextToken();
			if(pw.equals(pwconfirm)) {
				id = compare; // 아이디 저장
				return "pass";
			}
			else return "pwfalse";
		}
		return "nodata";
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public void run() {	
		String str = null;
		try {
			while(true) {
				while((str = in.readLine()) != null) {
					System.out.println("전송받은 내용 : " + str);
					StringTokenizer st = new StringTokenizer(str, "/");
					String temp = st.nextToken();
					if(temp.equals("login")) {
						String checkstr = check(str);
						pw.println(""); pw.flush();
						pw.println("loginconfirm/"+checkstr); 
						pw.flush();
						System.out.println("loginconfirm/"+checkstr);
						if(!checkstr.equals("pass")) return;
						infofirstadd(str);	
					} else if(temp.equals("newmsg")) {
						boardmsg(str);
					} else if(temp.equals("closed")) {
						close(str);
					} else if(temp.equals("idsearch")) {
						idsearch(str);
					}
				}
			}
		}catch (IOException e) {
			//e.printStackTrace();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} finally {
			try {
			in.close();
			pw.close();
			if(!soc.isClosed()) soc.close();
			} catch(IOException e2) {
				e2.printStackTrace();
			}
		}
	}	
	ServertoUser(){};
	ServertoUser(Socket soc) throws IOException{
		System.out.println("ServertoUser 생성");
		this.soc = soc; // 소켓정보
		this.soc.setTcpNoDelay(true);
		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		pw = new PrintWriter(soc.getOutputStream());	
		servertouserth = new Thread(this);
		servertouserth.start();
	}	
}