package Client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FileSendRecevie extends JFrame implements ActionListener{
	JFileChooser jfc = new JFileChooser("C:\\Users\\user\\Documents");
	File file=null;
	JComboBox userlist;
	ArrayList<String> listmap = new ArrayList<String>();
	JMenuBar menuBar = new JMenuBar();
	JMenu mnFile;
	JMenuItem mntmOpen, mntmQuit;
	JButton fileSend;
	JLabel fileroot;
	String targetID=null;
	FileSendRecevie(ArrayList<String> listmap) {
		this.setLayout(null);
		this.setResizable(false);
		int width = 650;
		int heigth = 140;
		this.setSize(width, heigth);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screen.width/2 - width/2;
		int y = screen.height/2 - heigth/2;
		this.setLocation(x, y);

		this.listmap = listmap;

		menuBar = new JMenuBar();
		mnFile = new JMenu("File");
		mntmOpen = new JMenuItem("Open");
		mntmQuit = new JMenuItem("Quit");

		menuBar.add(mnFile);
		mnFile.add(mntmOpen);
		mnFile.add(mntmQuit);
		this.setJMenuBar(menuBar);

		fileroot = new JLabel("fileroot : ");
		fileroot.setBounds(0, 0, 650, 40);
		this.add(fileroot);

		String[] array = new String[listmap.size()];
		for(int i = 0; i < array.length; i++) {
			array[i] = listmap.get(i);
		}
		userlist = new JComboBox(array);
		userlist.setBounds(0, 40, 325, 30);
		this.add(userlist);

		fileSend = new JButton("보내기");
		fileSend.setBounds(325, 40, 325, 30);
		this.add(fileSend);

		Event();
		this.setVisible(true);
	}
	void Event() {
		mntmOpen.addActionListener(this);
		mntmQuit.addActionListener(this);
		fileSend.addActionListener(this);	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fileSend) {
			if(file != null) {
				String selectid = userlist.getSelectedItem().toString();
				Chatmain.sendID = selectid;
				Chatmain.sendFile = file.getAbsolutePath();
				JOptionPane.showMessageDialog(null, "파일전송중..");
				Chatmain.peertopeer();				
				this.dispose();				
			} else {
				JOptionPane.showMessageDialog(null, "파일미선택");
			}
		} else if(e.getSource() == mntmOpen) {
			int retval = jfc.showOpenDialog(this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				fileroot.setText("fileroot : " + file.getAbsolutePath());				
			}
		} else if(e.getSource() == mntmQuit) {
			this.dispose();
		}
	}	
}
