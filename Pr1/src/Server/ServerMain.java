package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerMain implements Runnable{ // 서버측
	public static HashMap<Socket, String> user = new HashMap<Socket, String>();
	public static Map<String, String> hmap = new HashMap<String, String>(); // 회원정보 <ID, PW> // null 불허용
	static ServerSocket ss = null;
	static Thread Serverth;
	@Override
	public void run() {
		while(true) {
			try {
				Socket soc =  ss.accept();
				System.out.println("새 클라이언트 접속!! : " + soc.toString());
				new ServertoUser(soc);
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}	
	ServerMain() throws IOException{
		ss = new ServerSocket(12345); // 소켓설정
		System.out.println("Server Ready...");
		Serverth = new Thread(this);
		Serverth.start();
	}
	public static void main(String[] arg0) throws IOException, ClassNotFoundException {
		/* 유저정보 읽기 & 쓰기 
		 */
		
		File file = new File("userinfo.txt");		
		if(!file.exists()) {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
			ObjectOutputStream oos=new ObjectOutputStream(bos);			
			// 초기데이터
			hmap.put("User1", "12345");
			hmap.put("User2", "12345");
			hmap.put("User3", "12345");
			hmap.put("User4", "12345");
			hmap.put("User5", "12345");
			hmap.put("User6", "12345");
			hmap.put("User7", "12345");
			hmap.put("User8", "12345");
			hmap.put("User9", "12345");
			hmap.put("User10", "12345");
			hmap.put("User11", "12345");
			oos.writeObject(hmap);
			oos.writeUTF("");
			oos.close();
		}					
		// -- 읽기
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream  bis = new BufferedInputStream(fis, 1024);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Map<String, String> temp = (Map<String, String>) ois.readObject();
		hmap = temp;
		for(Map.Entry<String, String>m : hmap.entrySet()) {
			System.out.println(m.getKey() + " : " + m.getValue());
		}		
		
		ServerMain serverm = new ServerMain();
	}
}