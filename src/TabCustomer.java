import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;

public class TabCustomer extends JPanel implements ActionListener {
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable tbl = new JTable();
	private JButton btnModify = new JButton("선택 고객정보 수정");
	private JButton btnAdd = new JButton("신규 고객정보 추가");
	private JButton btnDelete = new JButton("선택 고객정보 삭제");
	private DefaultTableModel dtm = new DefaultTableModel(0, 0);

	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang" + "?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	private PreparedStatement pstmtAdd = null;
	private PreparedStatement pstmtDel = null;
	private PreparedStatement pstmtUpdate = null;
	private int idNum = 0;
	private int row;

	/**
	 * Create the panel.
	 */
	public TabCustomer() {
		setLayout(null);

		btnModify.setBounds(803, 424, 140, 29);
		add(btnModify);

		JLabel lblNewLabel = new JLabel("고객이름");
		lblNewLabel.setBounds(728, 118, 87, 16);
		add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setBounds(823, 61, 120, 26);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("운전면허번호");
		lblNewLabel_1.setBounds(728, 66, 87, 16);
		add(lblNewLabel_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(823, 113, 120, 26);
		add(textField_2);

		JLabel lblNewLabel_1_1 = new JLabel("고객 주소");
		lblNewLabel_1_1.setBounds(728, 151, 87, 16);
		add(lblNewLabel_1_1);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(823, 146, 120, 26);
		add(textField_3);

		JLabel lblNewLabel_2 = new JLabel("고객 이메일");
		lblNewLabel_2.setBounds(728, 236, 87, 16);
		add(lblNewLabel_2);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(823, 179, 120, 26);
		add(textField_4);

		JLabel lblNewLabel_1_1_1 = new JLabel("고객 연락처");
		lblNewLabel_1_1_1.setBounds(728, 184, 87, 16);
		add(lblNewLabel_1_1_1);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(823, 231, 120, 26);
		add(textField_5);

		btnDelete.setBounds(803, 465, 140, 29);
		add(btnDelete);

		btnAdd.setBounds(803, 383, 140, 29);
		add(btnAdd);

		btnAdd.addActionListener(this);
		btnModify.addActionListener(this);
		btnDelete.addActionListener(this);
		tbl.setRowSelectionAllowed(true);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.addMouseListener(new MyMouseListener());
		tbl.setBounds(29, 61, 678, 433);
		printTable();
		add(tbl);

		JLabel lblNewLabel_3 = new JLabel("하이픈(-)없이 12자리 정수값 정보");
		lblNewLabel_3.setForeground(Color.GRAY);
		lblNewLabel_3.setBounds(728, 85, 215, 16);
		add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("예:010-0000-0000");
		lblNewLabel_3_1.setForeground(Color.GRAY);
		lblNewLabel_3_1.setBounds(728, 203, 215, 16);
		add(lblNewLabel_3_1);

		JLabel lblNewLabel_3_2 = new JLabel("예:sejong@gmail.com");
		lblNewLabel_3_2.setForeground(Color.GRAY);
		lblNewLabel_3_2.setBounds(728, 255, 215, 16);
		add(lblNewLabel_3_2);
		
		JButton btnNewButton = new JButton("정보 업데이트");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable();
			}
		});
		btnNewButton.setBounds(590, 19, 117, 29);
		add(btnNewButton);

	}


	private class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) { // 리스트에 마우스클릭이벤트발생시
			row = tbl.getSelectedRow();
			try {
				textField_1.setText((tbl.getModel().getValueAt(row, 0)).toString());
				textField_2.setText((tbl.getModel().getValueAt(row, 1)).toString());
				textField_3.setText((tbl.getModel().getValueAt(row, 2)).toString());
				textField_4.setText((tbl.getModel().getValueAt(row, 3)).toString());
				textField_5.setText((tbl.getModel().getValueAt(row, 4)).toString());
				System.out.println(row);
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
				Pattern p0 = Pattern.compile("^[0-9]{12}$");
				Matcher m0 = p0.matcher(textField_1.getText());
				Pattern p1 = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");
				Matcher m1 = p1.matcher(textField_4.getText());
				Pattern p2 = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]+$");
				Matcher m2 = p2.matcher(textField_5.getText());
				if (m1.find() == true && m2.find() == true && m0.find() == true) {
					if (e.getSource() == btnAdd) { // 저장
						insert(textField_1.getText(), textField_2.getText(), textField_3.getText(),
								textField_4.getText(), textField_5.getText());
					} else if (e.getSource() == btnModify) {
						update(textField_1.getText(), textField_2.getText(), textField_3.getText(),
								textField_4.getText(), textField_5.getText(), textField_1.getText());
					} else if (e.getSource() == btnDelete) {
						delete(row);
					}
				} else {
					if (m0.find() != true) {
						JOptionPane.showMessageDialog(null, "운전면허증번호는 하이픈(-)없이 12자리 정수로 입력하세요!");
					} else if (m1.find() != true) {
						JOptionPane.showMessageDialog(null, "고객연락처 형식을 수정하세요!\n(예:010-1234-5678)");
					} else if (m2.find() != true) {
						JOptionPane.showMessageDialog(null, "이메일 형식을 수정하세요!\n(예:sejong@naver.com)");
					}
				}
			}
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private void printTable() {

		// add header of the table
		String header[] = new String[] { "운전면허번호", "고객명", "고객주소", "고객연락처", "고객이메일" };
		dtm.setRowCount(0);
		
		// add header in table model
		dtm.setColumnIdentifiers(header);
		tbl.setModel(dtm);

		String query_orders = "SELECT * FROM customer";
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
				dtm.addRow(new Object[] { rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insert(String customer_license, String customer_name, String customer_address, String customer_number,
			String customer_email) {
		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtAdd = con.prepareStatement(
					"insert into customer (customer_license, customer_name, customer_address, customer_number, customer_email) values(?,?,?,?,?)");
//			insert into member values(? -> 1 ,? -> 2, ? -> 3)" 각각의 ? 에 값 대입
			pstmtAdd.setString(1, customer_license);
			pstmtAdd.setString(2, customer_name);
			pstmtAdd.setString(3, customer_address);
			pstmtAdd.setString(4, customer_number);
			pstmtAdd.setString(5, customer_email);
//			대입받은 쿼리를 실행 -> 입력 (insert)
			pstmtAdd.executeUpdate();
			// JOptionPane.showMessageDialog(null, "등록 완료");
			String query_get = "SELECT * FROM customer where id = LAST_INSERT_ID()";
			pstmt = con.prepareStatement(query_get);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6) });
			}
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null, "문자열 값이 너무 길어 수정이 필요합니다.\n(45자 이내)");
		} catch (SQLIntegrityConstraintViolationException er) {
			JOptionPane.showMessageDialog(null, "이미 존재하는 고객입니다!\n(운전면허증번호 중복)");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void update(String customer_license_id, String customer_name, String customer_address,
			String customer_number, String customer_email, String customer_license) {
		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtUpdate = con.prepareStatement(
					"update customer set customer_name = ?, customer_address = ?, customer_number = ?,"
							+ "customer_email = ? where customer_license = ?");
//			값 대입
			pstmtUpdate.setString(5, customer_license);
			pstmtUpdate.setString(1, customer_name);
			pstmtUpdate.setString(2, customer_address);
			pstmtUpdate.setString(3, customer_number);
			pstmtUpdate.setString(4, customer_email);

			pstmtUpdate.executeUpdate();
			pstmtUpdate = con.prepareStatement("update customer set customer_license = ? where customer_license = ?");

			pstmtUpdate.setString(1, customer_license);
			pstmtUpdate.setString(2, customer_license_id);
			// 쿼리 실행
			pstmtUpdate.executeUpdate();
			System.out.println(row);
			dtm.setValueAt(customer_license, row, 0);
			dtm.setValueAt(customer_name, row, 1);
			dtm.setValueAt(customer_address, row, 2);
			dtm.setValueAt(customer_number, row, 3);
			dtm.setValueAt(customer_email, row, 4);
		} catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private void delete(int customer_license) {
		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtDel = con.prepareStatement("delete from customer where customer_license = ?");
//			id 값을 비교해서 삭제함
			pstmtDel.setInt(1, customer_license);
// 			대입받은 쿼리를 실행-> 삭제 (delete)
			pstmtDel.executeUpdate();
			dtm.removeRow(row);
		} catch (NumberFormatException er) {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
