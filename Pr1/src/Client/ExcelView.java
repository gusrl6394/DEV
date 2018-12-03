package Client;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelView extends JFrame implements ActionListener, WindowListener{
	JFileChooser jfc = new JFileChooser("C:\\Users\\user\\Documents");
	static JFrame frame;
	static File file;
	JTable table;
	JScrollPane js;
	String header[];
	String contents[][];
	JMenuBar menuBar;
	JPanel jp;
	MenuBar menu;
	JMenu mnFile;
	JMenuItem mntmOpen, mntmSave, mntmClose, mntmQuit;
	static DataFormatter formatter = new DataFormatter();
	static int rerow = 0, recol=0;
	static List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	private static void fileLoader(File file) throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException
	{
		File fs = file;
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(fs);
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "다른 프로세서가 사용중입니다.");
		}
		if(workbook.getNumberOfSheets() < 1) {
			JOptionPane.showMessageDialog(null, "시트가 존재하지 않습니다.");
			frame.dispose();
			return;
		}
		Sheet worksheet = workbook.getSheetAt(0);
		int maxrowNum = worksheet.getLastRowNum();
		for(int i=0; i<=maxrowNum; i++) {
			Row row = worksheet.getRow(i);
			try {
				if(row.getLastCellNum() != -1) {
					list.add(readCellData(row));
				}
			}catch(Exception e) {
			}
		}
		return;
	}

	private static HashMap<String, Object> readCellData(Row row) {
		HashMap<String, Object> hMap = new HashMap<String, Object>();
		int maxNum = row.getLastCellNum();
		rerow++;
		if(maxNum > recol) recol = maxNum;
		for(int i=0; i<maxNum; i++) {			
			hMap.put(String.valueOf(i), formatter.formatCellValue(row.getCell(i)));
		}
		return hMap;
	}
	void tableLoader() {		
		header = new String[recol];
		contents = new String[rerow][recol];
		for(int i=0; i<header.length; i++) { // A, B, C, D
			char temp = (char)('A'+i);
			header[i] = Character.toString(temp);
		}
		int conmaxrow = contents.length;
		for(int i=0; i<conmaxrow; i++){
			for(int j=0; j<contents[i].length; j++) {
				Object obj = list.get(i).get(String.valueOf(j));
				String temp = String.valueOf(obj);
				if(temp == "null") temp = "";
				contents[i][j] = temp;
			}
			
		}
	}
	void Save(String filename) {
		System.out.println(filename);
		table.editingCanceled(null);
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("first");
		XSSFRow row=null;
		int rowcnt = table.getRowCount();
		int colcnt = table.getColumnCount();
		for(int i=0; i<rowcnt; i++) {
			row = sheet.createRow(i);			
			for(int j=0; j<colcnt; j++) {
				String temp = String.valueOf(table.getValueAt(i, j));
				if(temp != null) row.createCell(j).setCellValue(temp);
			}
		}
		try {
			workbook.write(fs);
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void event(){
		mntmOpen.addActionListener(this);
		mntmSave.addActionListener(this);
		mntmClose.addActionListener(this);
		mntmQuit.addActionListener(this);
	}
	ExcelView(File file) throws EncryptedDocumentException, FileNotFoundException, InvalidFormatException, IOException{
		this.file = file;
		frame = new JFrame();
		fileLoader(file);
		tableLoader();
		table = new JTable(contents, header);
		for(int i=0; i<table.getColumnCount(); i++) { // 셀널비 최소 100이상			
			table.getColumn(table.getColumnName(i)).setPreferredWidth(100);
		}
		js = new JScrollPane(table);
		jp = new JPanel();		
		frame.setBounds(100, 100, 500, 500);
		
		menuBar = new JMenuBar();
		mnFile = new JMenu("File");
		mntmOpen = new JMenuItem("Open");
		mntmSave = new JMenuItem("Save");
		mntmClose = new JMenuItem("Close");
		mntmQuit = new JMenuItem("Quit");
		menuBar.add(mnFile);
		mnFile.add(mntmOpen);
		mnFile.add(mntmSave);
		mnFile.add(mntmClose);
		mnFile.addSeparator();
		mnFile.add(mntmQuit);
		frame.setJMenuBar(menuBar); // 메뉴바 적용		
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		frame.add(table.getTableHeader(), BorderLayout.NORTH);
		frame.add(table, BorderLayout.CENTER);
		frame.setVisible(true);
		event(); // 이벤트 등록
	}
	public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, InvalidFormatException, IOException {
		file = new File("test.xlsx");
		new ExcelView(file);
	}
	@Override
	public void windowOpened(WindowEvent e) {	}
	@Override
	public void windowClosing(WindowEvent e) {
		frame.dispose();		
	}
	@Override
	public void windowClosed(WindowEvent e) {	}
	@Override
	public void windowIconified(WindowEvent e) {	}
	@Override
	public void windowDeiconified(WindowEvent e) {	}
	@Override
	public void windowActivated(WindowEvent e) {	}
	@Override
	public void windowDeactivated(WindowEvent e) {	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mntmOpen) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
			jfc.setFileFilter(filter);    //필터 셋팅
			int retval = jfc.showOpenDialog(frame);
			if (retval == JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				try {
					new ExcelView(file);
				} catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(e.getSource() == mntmSave) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
			jfc.setFileFilter(filter);
			int retval = jfc.showSaveDialog(frame);
			if (retval == JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				String temp = file + ".xlsx";
				Save(temp);
			}
		}else if(e.getSource() == mntmQuit) {
			frame.dispose();
		}
	}
}