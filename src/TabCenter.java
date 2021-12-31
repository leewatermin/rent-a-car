import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;


import javax.swing.JComboBox;
import java.awt.Font;


public class TabCenter extends JPanel implements ActionListener{

	private JButton btnModify = new JButton("선택 정비소정보 수정");
	private JButton btnAdd = new JButton("신규 정비소정보 등록");
	private JButton btnDelete = new JButton("선택 정비소정보 삭제");
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_3;
	private JTextField textField_5;

	private DefaultTableModel dtm = new DefaultTableModel(0, 0);

	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang" + "?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	JTable tbl = new JTable();
	private PreparedStatement pstmtAdd = null;
	private PreparedStatement pstmtDel = null;
	private PreparedStatement pstmtUpdate = null;
	
	private int row;
	/**
	 * Create the panel.
	 */
	public TabCenter() {
		setLayout(null);
		
		btnDelete.setBounds(803, 465, 140, 29);
		add(btnDelete);
		
		btnAdd.setBounds(803, 383, 140, 29);
		add(btnAdd);
		
		btnModify.setBounds(803, 424, 140, 29);
		add(btnModify);
		
		JLabel lblNewLabel = new JLabel("정비소 이름");
		lblNewLabel.setBounds(729, 66, 86, 16);
		add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(823, 61, 120, 26);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("정비소 주소");
		lblNewLabel_1.setBounds(729, 99, 86, 16);
		add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(823, 94, 120, 26);
		add(textField_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("정비소전화번호");
		lblNewLabel_1_1.setBounds(729, 132, 86, 16);
		add(lblNewLabel_1_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(823, 160, 120, 26);
		add(textField_4);
		
		JLabel lblNewLabel_2 = new JLabel("담당자 이름");
		lblNewLabel_2.setBounds(729, 165, 86, 16);
		add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(823, 127, 120, 26);
		add(textField_3);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("담당자 이메일");
		lblNewLabel_1_1_1.setBounds(729, 198, 86, 16);
		add(lblNewLabel_1_1_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(823, 193, 120, 26);
		add(textField_5);

		JButton btnNewButton = new JButton("정보 업데이트");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable();
			}
		});
		btnNewButton.setBounds(590, 19, 117, 29);
		add(btnNewButton);

		btnAdd.addActionListener(this);
		btnModify.addActionListener(this);
		btnDelete.addActionListener(this);
		
		tbl.setRowSelectionAllowed(true);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.addMouseListener(new MyMouseListener());
		tbl.setBounds(29, 61, 678, 433);
		printTable();
		add(tbl);
	}

	private class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) { // 리스트에 마우스클릭이벤트발생시
			row = tbl.getSelectedRow();
			try {
				String query_orders = "SELECT * FROM center where id = "
						+ Integer.parseInt((tbl.getModel().getValueAt(row, 0)).toString());
				pstmt = con.prepareStatement(query_orders);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
					textField_4.setText(rs.getString(5));
					textField_5.setText(rs.getString(6));
				}
			} catch (NumberFormatException er) {
			} catch (Exception er) {
				er.printStackTrace();
			}

			btnAdd.setEnabled(true);
			btnDelete.setEnabled(true);
			btnModify.setEnabled(true);
			if (row == 0) {
				btnDelete.setEnabled(false);
				btnModify.setEnabled(false);
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
			}
		}
	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("database connect success");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if ((textField_1.getText().equals("")) || (textField_2.getText().equals(""))
					|| (textField_3.getText().equals("")) || (textField_4.getText().equals(""))
					|| (textField_5.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
			} else {
				Pattern p1 = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");
				Matcher m1 = p1.matcher(textField_3.getText());
				Pattern p2 = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
				Matcher m2 = p2.matcher(textField_5.getText());

				if (m1.find() == true && m2.find() == true) {
					System.out.println(m1.find());
					System.out.println(m2.find());
					if (e.getSource() == btnAdd) { // 저장
						insert(textField_1.getText(), textField_2.getText(), textField_3.getText(),
								textField_4.getText(), textField_5.getText());
					} else if (e.getSource() == btnModify) {
						update(row, textField_1.getText(), textField_2.getText(), textField_3.getText(),
								textField_4.getText(), textField_5.getText());
					} else if (e.getSource() == btnDelete) {
						delete(row);
					}
				} else {
					JOptionPane.showMessageDialog(null, "이메일/전화번호 형식이 틀렸습니다!\n(예:sejong@naver.com/02-0000-0000)");
				}
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
	}

	private void printTable() {

		// add header of the table
		String header[] = new String[] { "정비소ID", "정비소이름", "정비소주소", "정비소전화번호", "담당직원이름", "직원이메일" };
		dtm.setRowCount(0);
		// add header in table model
		dtm.setColumnIdentifiers(header);
		tbl.setModel(dtm);
		
		String query_orders = "SELECT * FROM center";
		try {
			try { /* 데이터베이스를 연결하는 과정 */
				System.out.println("데이터베이스 연결 준비...");
				con = DriverManager.getConnection(url, userid, pwd);
				System.out.println("database connect success");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			pstmt = con.prepareStatement(query_orders);
			rs = pstmt.executeQuery();
			dtm.addRow(header);
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insert(String center_name, String center_address, String center_number,
			String center_employee_name, String center_employee_email) {
		try {
//			PreparedStatement 생성-> con은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtAdd = con.prepareStatement(
					"insert into center (center_name, center_address, center_number, center_employee_name, center_employee_email) values(?,?,?,?,?)");
//			insert into member values(? -> 1 ,? -> 2, ? -> 3)" 각각의 ? 에 값 대입
			pstmtAdd.setString(1, center_name);
			pstmtAdd.setString(2, center_address);
			pstmtAdd.setString(3, center_number);
			pstmtAdd.setString(4, center_employee_name);
			pstmtAdd.setString(5, center_employee_email);

//			대입받은 쿼리를 실행 -> 입력 (insert)
			pstmtAdd.executeUpdate();
			// JOptionPane.showMessageDialog(null, "등록 완료");
			String query_get = "SELECT * FROM center where id = LAST_INSERT_ID()";
			pstmt = con.prepareStatement(query_get);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6) });
			}
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null, "문자열 값이 너무 길어 수정이 필요합니다.\n(45자 이내)");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void update(int id, String center_name, String center_address, String center_number,
			String center_employee_name, String center_employee_email) {

		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtUpdate = con.prepareStatement(
					"update center set center_name = ?, center_address = ?, center_number = ?, center_employee_name = ?, "
							+ "center_employee_email = ? where id = ?");
//			값 대입
			pstmtUpdate.setInt(6, id);
			pstmtUpdate.setString(1, center_name);
			pstmtUpdate.setString(2, center_address);
			pstmtUpdate.setString(3, center_number);
			pstmtUpdate.setString(4, center_employee_name);
			pstmtUpdate.setString(5, center_employee_email);
			// 쿼리 실행
			System.out.println(id);
			pstmtUpdate.executeUpdate();
			dtm.setValueAt(center_name, id, 1);
			dtm.setValueAt(center_address, id, 2);
			dtm.setValueAt(center_number, id, 3);
			dtm.setValueAt(center_employee_name, id, 4);
			dtm.setValueAt(center_employee_email, id, 5);
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null, "문자값이 너무 길어 수정이 필요합니다.\n(45자 이내)");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private void delete(int id) {
		if (id > 0) {
			try {
//			PreparedStatement 생성-> conn은 preDbTreatment() 메소드를 통해 생성되어 있음
				pstmtDel = con.prepareStatement("delete from center where id = ?");
//			id 값을 비교해서 삭제함
				pstmtDel.setInt(1, id);
// 			대입받은 쿼리를 실행-> 삭제 (delete)
				pstmtDel.executeUpdate();
				dtm.removeRow(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
