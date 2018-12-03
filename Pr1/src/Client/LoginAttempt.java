package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
public class LoginAttempt implements Runnable{ // 클라이언트측
	private InetAddress ia = null;
	private Socket soc = null;
	private BufferedReader in = null;
	private PrintWriter pw = null;
	//private ArrayList<String> data = new ArrayList<String>();
	private Thread Serverth;
	private String resultstr = "1";
	public String ID=null, PW=null;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPW() {
		return PW;
	}
	public void setPW(String pW) {
		PW = pW;
	}	
	LoginAttempt() throws IOException{			
		try{			
			ia = InetAddress.getByName("127.0.0.1");	
			soc = new Socket(ia, 12345);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
			in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			Serverth = new Thread(this);
			Serverth.start();
		}catch(IOException e) {
			System.err.println("접속오류 : " + e.toString());
			System.exit(-1);
		}
	}
	public void disconnect() throws IOException {
		Serverth.stop();
		in.close();
		pw.close();
		if(!soc.isClosed()) soc.close();
	}
	public void attemp() { // 로그인 시도
		String addstr = "login/"+ID+"/"+PW;
		//data.add(addstr);
		sendMsg(addstr);
	}
	public String getresult() { // 로그인 결과 // 초기값 : no, 로그인성공 : ok
		return resultstr;
	}
	public void setresult() { // 로그인 결과 // 초기값 : no, 로그인성공 : ok
		resultstr="1";
	}
	public Socket getsoket() throws IOException { // 로그인성공시 소켓정보 반환
		Serverth.stop();
		return soc;		
	}
	public BufferedReader getin() {
		return in;
	}
	public PrintWriter getpw() {
		return pw;
	}
	void sendMsg(String str) { // 메세지 전송
		pw.println(str);
		pw.flush();
	}
	@Override
	public void run() {
		while(true) {
			try {				
				String msg = "";
				while((msg = in.readLine()) != null) {
					System.out.println("전송받은 내용 : " + msg);
					if(msg.equals("loginconfirm/pass")) {
						resultstr = "ok";
					} else if(msg.equals("loginconfirm/pwfalse")) {
						resultstr = "pwfalse";
					} else if(msg.equals("loginconfirm/nodata")) {
						resultstr = "nodata";
					} else if(msg.equals("loginconfirm/overlap")) {
						resultstr = "overlap";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}
	}		
}