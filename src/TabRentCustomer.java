import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
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
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import java.awt.Color;

public class TabRentCustomer extends JPanel implements ActionListener {
	private JButton btnSubmit = new JButton("대여신청");
	private JButton btnSrch = new JButton("대여가능 캠핑카 검색");
	private JButton btnAll = new JButton("전체 캠핑카 조회");

	private JTextField textField_3;
	private JTextField textField_9;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_8;
	private JTextField textField_7;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable tbl;
	private DefaultTableModel dtm = new DefaultTableModel(0, 0);
	private boolean flag = false;

	Connection con;
	Statement st;
	PreparedStatement pstmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang" + "?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	private PreparedStatement pstmtAdd = null;
	private PreparedStatement pstmtUpdate = null;
	private int row;

	/**
	 * Create the panel.
	 */
	public TabRentCustomer() {
		setLayout(null);

		btnSubmit.setBounds(837, 465, 117, 29);
		add(btnSubmit);

		JLabel lblNewLabel_1 = new JLabel("대여회사ID");
		lblNewLabel_1.setForeground(Color.GRAY);
		lblNewLabel_1.setBounds(752, 169, 84, 16);
		add(lblNewLabel_1);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(828, 362, 126, 26);
		add(textField_9);

		JLabel lblNewLabel_1_1 = new JLabel("*대여기간-일");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setBounds(746, 235, 90, 16);
		add(lblNewLabel_1_1);

		textField_4 = new JTextField("예:2020-03-01");
		textField_4.setColumns(10);
		textField_4.setBounds(828, 197, 126, 26);
		add(textField_4);

		JLabel lblNewLabel_2 = new JLabel("*대여시작일");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setBounds(746, 202, 90, 16);
		add(lblNewLabel_2);

		JLabel lblNewLabel_1_1_1 = new JLabel("청구요금(일당)");
		lblNewLabel_1_1_1.setForeground(Color.GRAY);
		lblNewLabel_1_1_1.setBounds(752, 268, 84, 16);
		add(lblNewLabel_1_1_1);

		textField_5 = new JTextField("예:4");
		textField_5.setColumns(10);
		textField_5.setBounds(828, 230, 126, 26);
		add(textField_5);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(828, 164, 126, 26);
		add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel = new JLabel("*운전면허증번호");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(746, 136, 90, 16);
		add(lblNewLabel);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("납입기한");
		lblNewLabel_1_1_1_1.setForeground(Color.GRAY);
		lblNewLabel_1_1_1_1.setBounds(752, 301, 84, 16);
		add(lblNewLabel_1_1_1_1);

		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setColumns(10);
		textField_6.setBounds(828, 263, 126, 26);
		add(textField_6);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(828, 329, 126, 26);
		add(textField_8);

		JLabel lblNewLabel_1_1_1_2 = new JLabel("(기타청구내역)");
		lblNewLabel_1_1_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_1_1_2.setBounds(752, 334, 84, 16);
		add(lblNewLabel_1_1_1_2);

		JLabel lblNewLabel_1_1_2 = new JLabel("(기타청구요금)");
		lblNewLabel_1_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_1_2.setBounds(752, 367, 84, 16);
		add(lblNewLabel_1_1_2);

		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		textField_7.setBounds(828, 296, 126, 26);
		add(textField_7);

		JLabel lblNewLabel_3 = new JLabel("<< 캠핑카 대여 신청서 >>");
		lblNewLabel_3.setBounds(752, 58, 190, 26);
		add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(828, 98, 126, 26);
		add(textField_1);

		textField_2 = new JTextField("예:123412341234");
		textField_2.setColumns(10);
		textField_2.setBounds(828, 131, 126, 26);
		add(textField_2);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("캠핑카등록ID");
		lblNewLabel_1_1_2_1.setForeground(Color.GRAY);
		lblNewLabel_1_1_2_1.setBounds(752, 103, 84, 16);
		add(lblNewLabel_1_1_2_1);

		JLabel lblNewLabel_4 = new JLabel("*표시는 필수입력항목");
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setBounds(752, 395, 174, 26);
		add(lblNewLabel_4);

		JLabel lblNewLabel_4_1 = new JLabel("2. \uC2E0\uCCAD\uC11C \uC791\uC131 \uD6C4 \uC2E0\uCCAD");
		lblNewLabel_4_1.setForeground(Color.BLUE);
		lblNewLabel_4_1.setBounds(752, 16, 163, 26);
		add(lblNewLabel_4_1);

		JLabel lblNewLabel_4_1_1 = new JLabel("1. 캠핑카 검색 / 대여신청 할 캠핑카 선택");
		lblNewLabel_4_1_1.setForeground(Color.BLUE);
		lblNewLabel_4_1_1.setBounds(20, 16, 213, 26);
		add(lblNewLabel_4_1_1);

		btnSubmit.addActionListener(this);
		tbl = new JTable();
		tbl.setBounds(16, 98, 718, 396);
		tbl.addMouseListener(new MyMouseListener());
		add(tbl);

		btnSrch.setBounds(241, 53, 151, 29);
		btnSrch.addActionListener(this);
		add(btnSrch);

		btnAll.setBounds(385, 53, 117, 29);
		btnAll.addActionListener(this);
		add(btnAll);

		JLabel lblNewLabel_4_2 = new JLabel("(괄호 안은 선택입력항목)");
		lblNewLabel_4_2.setForeground(Color.BLACK);
		lblNewLabel_4_2.setBounds(752, 415, 174, 26);
		add(lblNewLabel_4_2);

		btnSubmit.setEnabled(false);
	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void printAllTable() { // 모든 캠핑카를 보여준다.

		// add header of the table등록일자는 고객에게 보여주지 않음!
		String header[] = new String[] { "캠핑카ID", "캠핑카이름", "차량번호", "승차인원", "제조회사", "제조연도", "누적주행거리", "대여비용", "소속회사ID" };

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
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initTable() {
		// add header of the table등록일자는 고객에게 보여주지 않음!
		String header[] = new String[] { "캠핑카ID", "캠핑카이름", "차량번호", "승차인원", "제조회사", "제조연도", "누적주행거리", "대여비용", "소속회사ID" };

		// add header in table model
		dtm.setColumnIdentifiers(header);
		dtm.addRow(header);
	}

	// 캠핑카 목록에서 캠핑카 선택한경우
	private class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) { // 리스트마우스클릭이벤트발생시
			// 선택한 값 열 가져오기
			if (flag == true) {
				row = tbl.getSelectedRow();
				try {
					String query_orders = "SELECT * FROM car where id = "
							+ Integer.parseInt((tbl.getModel().getValueAt(row, 0)).toString());
					pstmt = con.prepareStatement(query_orders);
					rs = pstmt.executeQuery();
					System.out.println(Integer.parseInt((tbl.getModel().getValueAt(row, 0)).toString()));
					while (rs.next()) {
						textField_1.setText("" + rs.getInt(1));
						textField_3.setText("" + rs.getInt(9));
						textField_2.setText("");
						textField_4.setText("");
						textField_5.setText("");
						textField_8.setText("");
						textField_9.setText("");
						textField_6.setText(rs.getInt(8) + "");
						textField_7.setText("대여시작일");
					}
				} catch (NumberFormatException er) {

				} catch (Exception er) {
					er.printStackTrace();
				}
				btnSubmit.setEnabled(true);
				if (row == 0) {
					btnSubmit.setEnabled(false);
					textField_1.setText("");
					textField_2.setText("");
					textField_3.setText("");
					textField_4.setText("");
					textField_5.setText("");
					textField_6.setText("");
					textField_7.setText("");
					textField_8.setText("");
					textField_9.setText("");
				}
			} else {
				btnSubmit.setEnabled(false);

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("database connect success");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (e.getSource() == btnAll) {
			flag = false;
			dtm.setRowCount(0);
			printAllTable();
		} else if (e.getSource() == btnSrch) {
			flag = true;
			dtm.setRowCount(0);
			printSearchTable();
		} else if (e.getSource() == btnSubmit) {
			if ((textField_2.getText().equals("")) || (textField_4.getText().equals(""))
					|| (textField_4.getText().equals("")) || (textField_5.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
			} else {
				if (textField_9.getText().length() == 0) {
					insert(Integer.parseInt(textField_1.getText()), textField_2.getText(),
							Integer.parseInt(textField_3.getText()), textField_4.getText(),
							Integer.parseInt(textField_5.getText()), Integer.parseInt(textField_6.getText()),
							textField_8.getText(), 0);
				} else {
					insert(Integer.parseInt(textField_1.getText()), textField_2.getText(),
							Integer.parseInt(textField_3.getText()), textField_4.getText(),
							Integer.parseInt(textField_5.getText()), Integer.parseInt(textField_6.getText()),
							textField_8.getText(), Integer.parseInt(textField_9.getText()));
				}
			}
		}

	}

	private void printSearchTable() {
		// add header of the table등록일자는 고객에게 보여주지 않음!
		String header[] = new String[] { "캠핑카ID", "캠핑카이름", "차량번호", "승차인원", "제조회사", "제조연도", "누적주행거리", "대여비용", "소속회사ID" };
		// add header in table model
		dtm.setColumnIdentifiers(header);
		tbl.setModel(dtm);
		dtm.addRow(header);
		
			
		String sql = "SELECT * FROM car WHERE car.available = 1; ";
		System.out.println("sql = " + sql);
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			dtm.setRowCount(0);
			initTable();
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9) });
			}
		} catch (Exception e) {
		}

	}

	private void insert(int car_id, String customer_license, int company_id, String rent_start_date, int rent_length,
			int rent_price, String rent_etc, int rent_etc_price) {

		try {
			pstmtAdd = con.prepareStatement(
					"insert into history_rent (car_id, rent_customer_license, rent_company_id, rent_start_date, rent_length, rent_price, rent_deadline, rent_etc, rent_etc_price)"
							+ " values(?,?,?,?,?,?,?,?,?)");
			// insert into member values(? -> 1 ,? -> 2, ? -> 3)" 각각의 ? 에 값 대입
			pstmtAdd.setInt(1, car_id);
			pstmtAdd.setString(2, customer_license);
			pstmtAdd.setInt(3, company_id);
			System.out.println(company_id);
			pstmtAdd.setString(4, rent_start_date);
			pstmtAdd.setInt(5, rent_length);
			pstmtAdd.setInt(6, rent_price);
			pstmtAdd.setString(7, rent_start_date);
			pstmtAdd.setString(8, rent_etc);
			pstmtAdd.setInt(9, rent_etc_price);
//			대입받은 쿼리를 실행 -> 입력 (insert)
			pstmtAdd.executeUpdate();
			JOptionPane.showMessageDialog(null, "등록 완료대여요금은 총 " + ((rent_price * rent_length) + rent_etc_price)
					+ "원, 납입기한은 대여시작일인 " + rent_start_date + "입니다.");
			
			pstmtUpdate = con.prepareStatement(
					"update car set available = ? where id = ?");
//			값 대입
			pstmtUpdate.setInt(2, car_id);
			pstmtUpdate.setInt(1, 0);
			// 쿼리 실행
			pstmtUpdate.executeUpdate();
		} catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null,
					"등록일자 값의 형식 오류\n(옳은 예 : 2020-02-28)\n혹은 문자값이 너무 길어 수정이 필요합니다.\n(45자 이내)");
		} catch (SQLIntegrityConstraintViolationException er) {

			JOptionPane.showMessageDialog(null, "등록되어있지 않은 고객정보입니다!\n운전면허번호는 올바른 값을 정수 12자리로 입력하세요!");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}
}
