import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Panel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TabCar extends JPanel implements ActionListener {

	private JButton btnNewButton = new JButton("정보 업데이트");
	private JButton btnNewButton_1 = new JButton("신규 차량 등록");
	private JButton btnNewButton_2 = new JButton("선택 차량 정보 삭제");
	private JButton btnNewButton_1_1 = new JButton("선택 차량 정보 수정");
	private JTextField textField_2 = new JTextField();
	private JTextField textField_3 = new JTextField();
	private JTextField textField_4 = new JTextField();
	private JTextField textField_5 = new JTextField();
	private JTextField textField_6 = new JTextField();
	private JTextField textField_7 = new JTextField();
	private JTextField textField_8 = new JTextField();
	private JTextField textField_10 = new JTextField();;
	private JTextField textField_9 = new JTextField();;
	private DefaultTableModel dtm = new DefaultTableModel(0, 0);
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

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
	 * 
	 * @throws SQLException
	 */
	public TabCar() throws SQLException {

		conDB();
		setLayout(null);

		btnNewButton_1.setBounds(793, 383, 150, 29);
		add(btnNewButton_1);
		btnNewButton_2.setBounds(793, 465, 150, 29);
		add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("캠핑카 이름");
		lblNewLabel.setBounds(786, 66, 61, 16);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("차량번호");
		lblNewLabel_1.setBounds(786, 99, 61, 16);
		add(lblNewLabel_1);

		textField_2.setColumns(10);
		textField_2.setBounds(847, 61, 101, 26);
		add(textField_2);

		JLabel lblNewLabel_1_1 = new JLabel("제조회사");
		lblNewLabel_1_1.setBounds(786, 165, 77, 16);
		add(lblNewLabel_1_1);

		textField_3.setColumns(10);
		textField_3.setBounds(847, 94, 101, 26);
		add(textField_3);

		JLabel lblNewLabel_2 = new JLabel("승차인원");
		lblNewLabel_2.setBounds(786, 132, 61, 16);
		add(lblNewLabel_2);
		textField_4.setColumns(10);
		textField_4.setBounds(847, 127, 101, 26);
		add(textField_4);

		JLabel lblNewLabel_1_1_1 = new JLabel("제조년도");
		lblNewLabel_1_1_1.setBounds(786, 198, 61, 16);
		add(lblNewLabel_1_1_1);

		textField_5.setColumns(10);
		textField_5.setBounds(847, 160, 101, 26);
		add(textField_5);

		textField_6.setColumns(10);
		textField_6.setBounds(847, 193, 101, 26);
		add(textField_6);


		JLabel lblNewLabel_1_1_2 = new JLabel("누적주행거리");
		lblNewLabel_1_1_2.setBounds(786, 231, 77, 16);
		add(lblNewLabel_1_1_2);

		textField_7.setColumns(10);
		textField_7.setBounds(847, 226, 101, 26);
		add(textField_7);

		JLabel lblNewLabel_1_1_1_2 = new JLabel("대여비용");
		lblNewLabel_1_1_1_2.setBounds(786, 264, 61, 16);
		add(lblNewLabel_1_1_1_2);

		textField_8.setColumns(10);
		textField_8.setBounds(847, 259, 101, 26);
		add(textField_8);

		btnNewButton_1.addActionListener(this);
		btnNewButton_1_1.addActionListener(this);
		btnNewButton_2.addActionListener(this);

		tbl.setBounds(29, 61, 745, 433);

		tbl.setRowSelectionAllowed(true);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.addMouseListener(new MyMouseListener());

		add(tbl);

		btnNewButton_1_1.setBounds(793, 424, 150, 29);
		add(btnNewButton_1_1);

		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("\uB4F1\uB85D\uC77C\uC790");
		lblNewLabel_1_1_1_2_1.setBounds(786, 330, 61, 16);
		add(lblNewLabel_1_1_1_2_1);

		textField_9.setColumns(10);
		textField_9.setBounds(847, 292, 101, 26);
		add(textField_9);

		textField_10.setColumns(10);
		textField_10.setBounds(847, 325, 101, 26);
		add(textField_10);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("\uC18C\uC18D\uD68C\uC0AC ID");
		lblNewLabel_1_1_2_1.setBounds(786, 297, 77, 16);
		add(lblNewLabel_1_1_2_1);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable();
			}
		});
		btnNewButton.setBounds(657, 23, 117, 29);
		add(btnNewButton);
	}


	private class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) { // 리스트마우스클릭이벤트발생시
			// 선택한 값 열 가져오기
			row = tbl.getSelectedRow();
			try {
				String query_orders = "SELECT * FROM car where id = "
						+ Integer.parseInt((tbl.getModel().getValueAt(row, 0)).toString());
				pstmt = con.prepareStatement(query_orders);
				rs = pstmt.executeQuery();
				System.out.println(Integer.parseInt((tbl.getModel().getValueAt(row, 0)).toString()));
				while (rs.next()) {
					textField_2.setText(rs.getString(2));
					textField_3.setText(rs.getString(3));
					textField_4.setText("" + rs.getInt(4));
					textField_5.setText(rs.getString(5));
					textField_6.setText("" + rs.getInt(6));
					textField_7.setText("" + rs.getInt(7));
					textField_8.setText("" + rs.getInt(8));
					textField_9.setText(rs.getString(9));
					textField_10.setText(rs.getString(10).substring(0, 10));
				}
			} catch (NumberFormatException er) {
				btnNewButton_1.setEnabled(true);
				btnNewButton_1_1.setEnabled(true);
				btnNewButton_2.setEnabled(true);
			} catch (Exception er) {
				er.printStackTrace();
			}
			btnNewButton_1.setEnabled(true);
			btnNewButton_1_1.setEnabled(true);
			btnNewButton_2.setEnabled(true);
			if (row == 0) {
				btnNewButton_1_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				textField_6.setText("");
				textField_7.setText("");
				textField_8.setText("");
				textField_9.setText("");
				textField_10.setText("");
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
			if ((textField_2.getText().equals("")) || (textField_3.getText().equals(""))
					|| (textField_5.getText().equals("")) || (textField_10.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
			} else {
				if (e.getSource() == btnNewButton_1) { // 저장
					insert(textField_2.getText(), textField_3.getText(), Integer.parseInt(textField_4.getText()),
							textField_5.getText(), Integer.parseInt(textField_6.getText()),
							Integer.parseInt(textField_7.getText()), Integer.parseInt(textField_8.getText()),
							Integer.parseInt(textField_9.getText()), textField_10.getText());
				} else if (e.getSource() == btnNewButton_1_1) {
					update(row, textField_2.getText(), textField_3.getText(), Integer.parseInt(textField_4.getText()),
							textField_5.getText(), Integer.parseInt(textField_6.getText()),
							Integer.parseInt(textField_7.getText()), Integer.parseInt(textField_8.getText()),
							Integer.parseInt(textField_9.getText()), textField_10.getText());

				} else if (e.getSource() == btnNewButton_2) {
					delete(row);
				}
			}
		
	}

	private void printTable() {

		// add header of the table
		String header[] = new String[] { "캠핑카ID", "캠핑카이름", "차량번호", "승차인원", "제조회사", "제조연도", "누적주행거리", "대여비용", "소속회사ID",
				"등록일자" };
		dtm.setRowCount(0);
		// add header in table model
		dtm.setColumnIdentifiers(header);
		tbl.setModel(dtm);

		String query_orders = "SELECT * FROM car";
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
				dtm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insert(String car_name, String car_number, int car_capacity, String car_brand, int car_year,
			int car_run, int car_price, int car_company_id, String car_adddate) {

		try {

			pstmtAdd = con.prepareStatement("insert into car"
					+ " (car_name, car_number, car_capacity, car_brand, car_year, car_run, car_price, car_company_id, car_adddate)"
					+ " values(?,?,?,?,?,?,?,?,?)");
//			insert into member values(? -> 1 ,? -> 2, ? -> 3)" 각각의 ? 에 값 대입
			pstmtAdd.setString(1, car_name);
			pstmtAdd.setString(2, car_number);
			pstmtAdd.setInt(3, car_capacity);
			pstmtAdd.setString(4, car_brand);
			pstmtAdd.setInt(5, car_year);
			pstmtAdd.setInt(6, car_run);
			pstmtAdd.setInt(7, car_price);
			pstmtAdd.setInt(8, car_company_id);
			pstmtAdd.setString(9, car_adddate);
//			대입받은 쿼리를 실행 -> 입력 (insert)
			pstmtAdd.executeUpdate();
			// JOptionPane.showMessageDialog(null, "등록 완료");

			String query_get = "SELECT * FROM car where id = LAST_INSERT_ID()";
			pstmt = con.prepareStatement(query_get);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10).substring(0, 10) });
			}

		} catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null,
					"등록일자 값의 형식 오류\n(옳은 예 : 2020-02-28)\n혹은 문자값이 너무 길어 수정이 필요합니다.\n(45자 이내)");
		} catch (SQLIntegrityConstraintViolationException er) {
			JOptionPane.showMessageDialog(null, "이미 존재하는 차량 번호이거나 존재하지 않는 회사 ID 번호입니다!!");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private void update(int id, String car_name, String car_number, int car_capacity, String car_brand, int car_year,
			int car_run, int car_price, int car_company_id, String car_adddate) {

		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtUpdate = con.prepareStatement(
					"update car set car_name = ?, car_number = ?, car_capacity = ?, car_brand = ?, car_year = ?, car_run = ?, car_price = ?, car_company_id = ?, car_adddate = ? where id = ?");
//			값 대입
			pstmtUpdate.setInt(10, id);
			pstmtUpdate.setString(1, car_name);
			pstmtUpdate.setString(2, car_number);
			pstmtUpdate.setInt(3, car_capacity);
			pstmtUpdate.setString(4, car_brand);
			pstmtUpdate.setInt(5, car_year);
			pstmtUpdate.setInt(6, car_run);
			pstmtUpdate.setInt(7, car_price);
			pstmtUpdate.setInt(8, car_company_id);
			pstmtUpdate.setString(9, car_adddate);
			// 쿼리 실행
			System.out.println(id);
			pstmtUpdate.executeUpdate();
			dtm.setValueAt(car_name, id, 1);
			dtm.setValueAt(car_number, id, 2);
			dtm.setValueAt(car_capacity, id, 3);
			dtm.setValueAt(car_brand, id, 4);
			dtm.setValueAt(car_year, id, 5);
			dtm.setValueAt(car_run, id, 6);
			dtm.setValueAt(car_price, id, 7);
			dtm.setValueAt(car_company_id, id, 8);
			dtm.setValueAt(car_adddate, id, 9);
		} catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "올바른 정수 값을 입력하세요(승차인원/연도/주행거리/대여비용)");
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null,
					"등록일자 값의 형식 오류\n(옳은 예 : 2019-02-08)\n혹은 문자값이 너무 길어 수정이 필요합니다.\n(45자 이내)");
		} catch (SQLIntegrityConstraintViolationException er) {
			JOptionPane.showMessageDialog(null, "이미 존재하는 차량 번호이거나 존재하지 않는 회사 ID 번호입니다!!");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private void delete(int id) {
		if (id > 0) {
			try {
//			PreparedStatement 생성-> conn은 preDbTreatment() 메소드를 통해 생성되어 있음
				pstmtDel = con.prepareStatement("delete from car where id = ?");
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
