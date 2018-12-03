package Client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileSendSocket{
	File file;
	Socket receviesoc = null; // 받을소켓
	FileSendSocket(Socket receviesoc, String sendFile) throws UnknownHostException, IOException{
		System.out.println("FileSendSocket 생성자");
		this.receviesoc = receviesoc;
		file = new File(sendFile);
		OutputStream out;
		FileInputStream fin = new FileInputStream(file); //
		out = this.receviesoc.getOutputStream();  //
		DataOutputStream dout = new DataOutputStream(out);
		byte[] buffer = new byte[1024];
		int len; int data=0; 
		while((len = fin.read(buffer))>0){ 
			data++; 
		}
		int datas = data;
		fin.close();
		fin = new FileInputStream(file);
		dout.writeInt(data); 
		dout.writeUTF(file.getName());
		
		len = 0;
		for(;data>0;data--){   
			len = fin.read(buffer);
			out.write(buffer,0,len);  
		}
		System.out.println("약 : "+datas+" kbyte");
		fin.close();
		dout.close();		
		this.receviesoc.close();
	}
}
