package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Chatmain extends JFrame implements ActionListener, KeyListener, WindowListener, Runnable{
	JFileChooser jfc = new JFileChooser("C:\\Users\\user\\Documents");
	File file;
	JScrollPane userlistp, chatnotelistp; // userlist를 담을 판넬
	JList<String> list;
	ArrayList<String> listmap = new ArrayList<String>();
	JTextArea chatArea;
	JTextField note;
	JButton notebt;
	DefaultListModel<String> model;
	JMenuBar menuBar = new JMenuBar();
	JMenu mnFile, mnNewMenu;
	JMenuItem mntmAbout;
	JMenuItem mntmOpen, mntmSave, mntmSend, mntmClear, mntmQuit;
	JLabel lbuserip; // 유저 아이피 라벨
	JLabel lbuserid; // 유저 아이디 라벨
	JLabel lbusernumber; // 유저수 표시하는 라벨
	int currentusernumber=0; // 유저수 저장하는 변수
	public static String sendID = null; // 파일전송시 받을 ID
	public static String sendFile = null; // 파일전송시 보낼 파일
	String localip = null;
	String ID = null;
	Socket soc = null;
	static PrintWriter pw = null;
	BufferedReader in = null;
	Thread Serverth;
	void sendMsg(String str) {
		pw.println(str);
		pw.flush();
		System.out.println("sendMsg : " + str);
	}
	void init() throws UnknownHostException {
		this.setLayout(null); // Absoulte 레이아웃
		this.setResizable(false);

		localip = InetAddress.getLocalHost().toString();
		localip = localip.substring(localip.indexOf('/')+1);			
		menuBar = new JMenuBar();
		mnFile = new JMenu("File");
		mntmOpen = new JMenuItem("Open");
		mntmSave = new JMenuItem("ChatSave");
		mntmSend = new JMenuItem("Send");
		mntmClear = new JMenuItem("Clear");
		mntmQuit = new JMenuItem("Quit");		
		mnNewMenu = new JMenu("Help");
		mntmAbout = new JMenuItem("About");

		menuBar.add(mnFile);
		menuBar.add(mnNewMenu);
		mnFile.add(mntmOpen);
		mnFile.add(mntmSave);
		mnFile.add(mntmSend);
		mnFile.add(mntmClear);
		mnFile.addSeparator();
		mnFile.add(mntmQuit);
		mnNewMenu.add(mntmAbout);
		this.setJMenuBar(menuBar);

		lbuserip = new JLabel("IP : "+localip);
		lbuserid = new JLabel("ID : "+ID);
		lbusernumber = new JLabel("현재 총 접속숫자 : "+ ++currentusernumber);
		lbuserip.setBounds(25,10,179,38);
		lbuserid.setBounds(25,48,179,38);
		lbusernumber.setBounds(25,86,179,38);
		this.add(lbuserip);
		this.add(lbuserid);
		this.add(lbusernumber);

		userlistp = new JScrollPane();
		userlistp.setBounds(25,147,179,381);
		this.add(userlistp);

		chatnotelistp = new JScrollPane();
		chatnotelistp.setBounds(239, 10, 379, 476);
		this.add(chatnotelistp);

		listmap.add(ID);
		Collections.sort(listmap);
		model = new DefaultListModel<>();		
		list = new JList<>(model);
		list.setBackground(Color.LIGHT_GRAY);
		userlistp.setViewportView(list);

		chatArea = new JTextArea();
		chatArea.setBackground(Color.LIGHT_GRAY);
		chatnotelistp.setViewportView(chatArea);

		note = new JTextField();
		note.setBounds(239,496,286,32);
		this.add(note);

		notebt = new JButton("Send");
		notebt.setBounds(539, 496, 77, 32);
		this.add(notebt);

	}
	void Event(){
		mntmOpen.addActionListener(this);
		mntmSave.addActionListener(this);
		mntmSend.addActionListener(this);
		mntmClear.addActionListener(this);
		mntmQuit.addActionListener(this);
		mntmAbout.addActionListener(this);
		note.addKeyListener(this); // 엔터키 이벤트
		notebt.addActionListener(this);
		this.addWindowListener(this);
	}
	public static void peertopeer() {
		String sendt = "idsearch/"+sendID;
		pw.println(sendt);
		pw.flush();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==notebt) {
			chatArea.append(ID+" : "+note.getText()+"\n");			
			sendMsg("newmsg/"+ID+"/"+note.getText());
			note.setText("");
		} else if(e.getSource() == mntmOpen) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
			jfc.setFileFilter(filter);    //필터 셋팅
			int retval = jfc.showOpenDialog(this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				try {
					new ExcelView(file);
				} catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if(e.getSource() == mntmSave) {
			long time = System.currentTimeMillis();
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			String str = dayTime.format(new Date(time));
			File file = new File(str+".txt");
			PrintWriter output = null;
			try {
				file.createNewFile();
				output = new PrintWriter(file);
				String rollmsg = chatArea.getText();
				StringTokenizer rollst = new StringTokenizer(rollmsg, "\n");
				while(rollst.hasMoreTokens()) {
					String savestr = rollst.nextToken();
					System.out.println(savestr);
					output.println(savestr);
				}
				output.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, str+".txt 에 저장되었습니다.");
		} else if(e.getSource() == mntmSend) {
			if(listmap.size()>1) {
				ArrayList<String> listmaptemp = new ArrayList<String>();
				for(String lnstr : listmap) {
					if(!lnstr.equals(ID)) listmaptemp.add(lnstr);
				}
				new FileSendRecevie(listmaptemp);
			}
			else {
				JOptionPane.showMessageDialog(null, "보낼상대가 1명이상 있어야됩니다.");
				return;
			}
		}else if(e.getSource() == mntmClear) {
			chatArea.setText(""); // 채팅창 클리어
			JOptionPane.showMessageDialog(null, "채팅창 클리어");
		} else if(e.getSource() == mntmQuit) {
			sendMsg("closed/"+ID);
			this.dispose();
		} else if(e.getSource() == mntmAbout) {
			JOptionPane.showMessageDialog(null, "버전 1.0\n제작자 : 김현기","About",JOptionPane.PLAIN_MESSAGE);
		}
	}
	@Override
	public void run() {
		try {
			while(true) {
				String msg;
				int tempn=0;
				while((msg = in.readLine()) != null) {
					System.out.println("전송받은 내용 : " + msg);
					if(!msg.equals("")) {
						StringTokenizer stm = new StringTokenizer(msg, "/");		
						String temp = stm.nextToken();
						System.out.println("stm 앞 프로토콜 : " + temp);
						if(temp.equals("newuser")) { // 새유저 리스트 추가							
							String newid = stm.nextToken();
							if(!listmap.contains(newid)) {
								listmap.add(newid);
								chatArea.append("새 유저 : ( "+newid+" )가 접속하였습니다.\n");
								System.out.println("새유저 추가 : " + newid);
							}
						} else if(temp.equals("olduser")) {
							String oldid = stm.nextToken();
							if(!listmap.contains(oldid)) {
								listmap.add(oldid);
								chatArea.append("구 유저 : ( "+oldid+" )가 접속하였습니다.\n");
								System.out.println("구유저 추가 : " + oldid);	
							}
						} else if(temp.equals("newmsg")) {
							String id = stm.nextToken();
							String note = stm.nextToken();
							chatArea.append(id + " : " + note+"\n");
						} else if(temp.equals("removeuser")) {
							String removeid = stm.nextToken();
							if(listmap.contains(removeid)) {
								chatArea.append("구 유저 : ( "+removeid+" )가 나갔습니다.\n");
								listmap.remove(removeid);
								System.out.println("구유저 삭제 : " + removeid);	
							}
						} else if(temp.equals("socket")) {
							String recviet  = stm.nextToken();
							System.out.println("recviet : " + recviet);
							Socket r = new Socket(recviet, 15000);
							new FileSendSocket(r, sendFile);
						} else if(temp.equals("fileready")){
							new FileRecevieSocket();
						}
						if(temp.equals("listupdate")) {
							lbusernumber.setText("현재 총 접속숫자 : "+ listmap.size());
							Collections.sort(listmap);
							model.clear();
							model = new DefaultListModel<>();	
							list = new JList<>(model); // JList model apply
							for(String each: listmap) {
								model.addElement(each);
							}
							list.repaint();
							list.setBackground(Color.LIGHT_GRAY);
							userlistp.setViewportView(list);
						}						
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar() == KeyEvent.VK_ENTER) {
			chatArea.append(ID+" : "+note.getText()+"\n");			
			sendMsg("newmsg/"+ID+"/"+note.getText());
			note.setText("");
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println("windowClosing 이벤트");
		sendMsg("closed/"+ID);
		//this.dispose();
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	Chatmain(Socket soc, String ID) throws IOException{
		System.out.println("생성자 부분 받은값 : " + soc.toString() + " // " + ID);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.soc = soc; // 로그인 클래스에서 받은 소켓정보
		this.soc.setTcpNoDelay(true);
		this.ID = ID; // 아이디 받기
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
		in = new BufferedReader(new InputStreamReader(soc.getInputStream()));		
		int width = 650;
		int heigth = 600;
		this.setTitle("Chatmin");
		this.setSize(width, heigth);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screen.width/2 - width/2;
		int y = screen.height/2 - heigth/2;
		this.setLocation(x, y);
		init();
		Event();
		Serverth = new Thread(this);
		Serverth.start();
		this.setVisible(true);
	}
}
