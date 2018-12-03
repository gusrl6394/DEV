package Client;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileRecevieSocket{
	File file;
	ServerSocket soc = null;
	Socket client = null;
	DataInputStream din = null;
	FileRecevieSocket() throws UnknownHostException, IOException{
		try {
			System.out.println("FileRecevieSocket 생성자");
			soc = new ServerSocket(15000);
			client = new Socket();
			client = soc.accept();  
			InputStream in = null;                       
			FileOutputStream out = null;
			in = client.getInputStream();
			din = new DataInputStream(in);
			while(true){
				int data = din.readInt();        
				String filename = din.readUTF();
				File file = new File(filename);
				out = new FileOutputStream(file);
				int datas = data;
				byte[] buffer = new byte[1024];
				int len;
				for(;data>0;data--){
					len = in.read(buffer);
					out.write(buffer,0,len);
				}
				System.out.println("약 : "+datas+" kbps");
				out.flush();
				out.close();
			}
		} catch(NullPointerException | EOFException e) {
			din.close();
			if(!client.isClosed())client.close();
			if(!soc.isClosed())soc.close();
		}
	}
}
