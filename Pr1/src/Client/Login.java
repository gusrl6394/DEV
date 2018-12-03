package Client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
	static Login Loginframe;
	Panel contentPane = new Panel();
	Panel loginbtpanel = new Panel();
	Button loginbt = new Button("Login");
	ImageIcon ic1 = new ImageIcon("01.jpg");
	JButton capreload = new JButton(ic1);
	Panel logincenterpanel = new Panel();	
	Label myiplb = new Label("MyIP");
	JTextField myiptf = new JTextField();
	Label serveriplb = new Label("ServerIP");
	JTextField serveriptf = new JTextField("127.0.0.1");
	Label idlb = new Label("ID");
	JTextField idtf = new JTextField("User1");
	Label pwlb = new Label("PW");
	JPasswordField pwjtf = new JPasswordField("12345");;
	Captcha cp = null;
	ImageIcon img = null;
	JLabel caplb;
	JTextField captf = new JTextField();
	String vCode;
	// 메뉴바
	MenuBar menuBar = new MenuBar();
	Menu mnNewMenu = new Menu("Help");
	MenuItem mntmNewMenuItem = new MenuItem("About");
	// 정규식
	Pattern idp = Pattern.compile("(^[0-9a-zA-Z]*$)"); // ID가 소문자이거나 대문자이거나 숫자인경우, 최소 1자리 이상
	Pattern pwp = Pattern.compile("(^[0-9]*$)"); // PW 숫자만입력, 최소 1자리 이상
	//Pattern emailp = Pattern.compile("/^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$/"); // 이메일
	// 네트워크
	Socket soc = null;
	BufferedReader in = null;
	PrintWriter pw = null;
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == mntmNewMenuItem) {
				JOptionPane.showMessageDialog(null, "버전 1.0\n제작자 : 김현기","About",JOptionPane.PLAIN_MESSAGE);
			} else if(e.getSource() == capreload) {
				try {
					cp = new Captcha();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				byte[] data = cp.getbyteArrayOutputStream();
				img = new ImageIcon(data);
				caplb.setIcon(img);
				vCode = cp.getvCode();
			} else if(e.getSource() == loginbt) {
				if(vCode.equals(captf.getText())) {
					Matcher idm = idp.matcher(idtf.getText());
					Matcher pwm = pwp.matcher(pwjtf.getText());
					if(idm.find()) System.out.println("ID패턴 일치");
					else {
						JOptionPane.showMessageDialog(null, "ID패턴이 틀렸습니다.","Login false",JOptionPane.PLAIN_MESSAGE);
						System.out.println("ID패턴이 틀렸습니다.");
						return;
					}
					if(pwm.find()) System.out.println("PW패턴 일치");
					else {
						JOptionPane.showMessageDialog(null, "PW패턴이 틀렸습니다.","Login false",JOptionPane.PLAIN_MESSAGE);
						System.out.println("PW패턴이 틀렸습니다.");
						return;
					}
					try {
						String idtfstr = idtf.getText();
						String pwstr = pwjtf.getText();
						LoginAttempt at = new LoginAttempt(); // 네트워크 메소드 실행
						at.setID(idtfstr);
						at.setPW(pwstr);
						at.attemp(); // 로그인 시도 
						String msg = "1"; // 로그인 정보 결과 반환
						while(msg.equals("1")) {
							msg = at.getresult(); // ok 또는 no 반환
						}
						String res = msg;
						if(res.equals("ok")) {
							soc = at.getsoket(); // soc 정보 취득
							in = at.getin();
							pw = at.getpw();
							Chatmain ch = new Chatmain(soc, idtf.getText());
							Loginframe.dispose();						
						} else if(res.equals("pwfalse")) {
							JOptionPane.showMessageDialog(null, "pwflase","Login false",JOptionPane.PLAIN_MESSAGE);
							msg = "1";
							at.setresult();	
							//at.disconnect();
							at=null;
						} else if(res.equals("nodata")) {
							JOptionPane.showMessageDialog(null, "nodata","Login false",JOptionPane.PLAIN_MESSAGE);
							msg = "1";
							at.setresult();
							//at.disconnect();
							at=null;
						} else if(res.equals("overlap")) {
							JOptionPane.showMessageDialog(null, "중복로그인","Login false",JOptionPane.PLAIN_MESSAGE);
							msg = "1";
							at.setresult();
							//at.disconnect();
							at=null;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}  
				} else {
					JOptionPane.showMessageDialog(null, "보안코드 불일치","Login false",JOptionPane.PLAIN_MESSAGE);
					System.out.println("보안코드 불일치");
				}
				// 서버한테 메세지 보낸후 확인처리 & (ID+PW+vCode)일치 여부
			}
		}		
	}
	void setEvent() {
		mntmNewMenuItem.addActionListener(new MyActionListener());
		capreload.addActionListener(new MyActionListener());
		loginbt.addActionListener(new MyActionListener());
	}
	void init() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		String localip = null;
		try {
			// 자기 IP취득
			localip = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		localip = localip.substring(localip.indexOf('/')+1);
		myiptf.setText(localip);
		myiptf.setEditable(false);
		setMenuBar(menuBar);
		menuBar.add(mnNewMenu);
		mnNewMenu.add(mntmNewMenuItem);

		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		contentPane.add(loginbtpanel, BorderLayout.SOUTH);
		loginbtpanel.setLayout(new GridLayout(1, 1, 0, 0));
		loginbtpanel.add(loginbt);
		contentPane.add(logincenterpanel, BorderLayout.CENTER);
		logincenterpanel.setLayout(null);
		myiptf.setBounds(168, 13, 216, 29);
		logincenterpanel.add(myiptf);
		myiplb.setBounds(24, 13, 69, 35);
		logincenterpanel.add(myiplb);
		serveriplb.setBounds(24, 48, 69, 29);
		logincenterpanel.add(serveriplb);
		serveriptf.setBounds(168, 48, 216, 29);
		logincenterpanel.add(serveriptf);
		idlb.setBounds(24, 83, 69, 29);
		logincenterpanel.add(idlb);
		idtf.setBounds(168, 83, 216, 29);
		logincenterpanel.add(idtf);
		pwlb.setBounds(24, 118, 69, 29);
		logincenterpanel.add(pwlb);
		pwjtf.setBounds(168, 118, 216, 29);
		logincenterpanel.add(pwjtf);
		cp = new Captcha();
		byte[] data = cp.getbyteArrayOutputStream();
		img = new ImageIcon(data);
		caplb = new JLabel(img);
		vCode = cp.getvCode();
		captf.setText(vCode);
		caplb.setBounds(24, 150, img.getIconWidth(), img.getIconHeight());		
		logincenterpanel.add(caplb);
		captf.setBounds(240, 150, 145, 60);
		logincenterpanel.add(captf);
		capreload.setBounds(390, 150, 30, 30);
		logincenterpanel.add(capreload);		
	}	
	public Login() {}
	public Login(String title, int width, int heigth) throws IOException {
		super(title);
		this.setSize(width, heigth);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screen.width/2 - width/2;
		int y = screen.height/2 - heigth/2;
		this.setLocation(x, y);
		init();
		setEvent();
		this.setVisible(true);
	}
	public static void main(String[] args) throws IOException {
		Loginframe = new Login("Login창", 450, 300);
	}
}
