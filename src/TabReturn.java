
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Panel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JCheckBox;
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

public class TabReturn extends JPanel implements ActionListener {

	Connection con;
	Statement st;
	private PreparedStatement pstmtUpdate = null;
	private PreparedStatement pstmtFixFunc = null;
	private PreparedStatement pstmtReturn = null;
	private PreparedStatement pstmtFix = null;

	private PreparedStatement pstmtDel = null;
	private PreparedStatement pstmt = null;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang" + "?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	private DefaultTableModel dtm = new DefaultTableModel(0, 0);

	private int row;
	private JTextField textField_2 = new JTextField();
	private JTextField textField_3 = new JTextField();
	private JTextField textField_4 = new JTextField();
	private JTextField textField_5 = new JTextField();
	private JTextField textField_6 = new JTextField();
	private JTextField textField_7 = new JTextField();
	private JTextField textField_8 = new JTextField();
	private JTextField textField_10 = new JTextField();
	private JTextField textField_12 = new JTextField();

	private JTextField textField_1 = new JTextField();

	private JButton btnDelete = new JButton("수리정보 삭제");
	private JButton btnModify = new JButton("수리정보 수정");
	private JButton btnSubmit = new JButton("반환 하기");
	private JTable tbl;

	/**
	 * Create the panel.
	 */
	public TabReturn() {
		setLayout(null);

		btnSubmit.setBounds(223, 294, 117, 29);
		add(btnSubmit);
		btnSubmit.addActionListener(this);

		JLabel lblNewLabel_1 = new JLabel("대여ID");
		lblNewLabel_1.setBounds(6, 132, 61, 16);
		add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("캠핑카 반환사항");
		lblNewLabel_3.setBounds(6, 67, 171, 28);
		add(lblNewLabel_3);

		JLabel lblNewLabel_1_1 = new JLabel("뒷부분 설명");
		lblNewLabel_1_1.setBounds(6, 198, 61, 16);
		add(lblNewLabel_1_1);

		JLabel lblNewLabel_4 = new JLabel("앞부분 설명");
		lblNewLabel_4.setBounds(6, 165, 75, 16);
		add(lblNewLabel_4);

		textField_3.setBounds(79, 160, 103, 26);
		add(textField_3);

		textField_4.setBounds(79, 193, 103, 26);
		add(textField_4);

		JLabel lblNewLabel_1_2 = new JLabel("왼쪽 설명");
		lblNewLabel_1_2.setBounds(6, 264, 61, 16);
		add(lblNewLabel_1_2);

		JLabel lblNewLabel_2 = new JLabel("오른쪽 설명");
		lblNewLabel_2.setBounds(6, 231, 75, 16);
		add(lblNewLabel_2);

		textField_5.setColumns(10);
		textField_5.setBounds(79, 226, 103, 26);
		add(textField_5);

		textField_6.setColumns(10);
		textField_6.setBounds(79, 259, 103, 26);
		add(textField_6);

		JLabel lblNewLabel_3_1 = new JLabel("수리 의뢰사항");
		lblNewLabel_3_1.setBounds(194, 67, 228, 28);
		add(lblNewLabel_3_1);

		JLabel lblNewLabel_1_3 = new JLabel("정비소ID");
		lblNewLabel_1_3.setBounds(198, 132, 61, 16);
		add(lblNewLabel_1_3);

		textField_7.setColumns(10);
		textField_7.setBounds(257, 127, 95, 26);
		add(textField_7);

		JLabel lblNewLabel_4_1 = new JLabel("정비 내역");
		lblNewLabel_4_1.setBounds(198, 165, 61, 16);
		add(lblNewLabel_4_1);

		textField_8.setColumns(10);
		textField_8.setBounds(257, 160, 95, 26);
		add(textField_8);

		JLabel lblNewLabel_1_3_1 = new JLabel("수리 날짜");
		lblNewLabel_1_3_1.setBounds(198, 198, 61, 16);
		add(lblNewLabel_1_3_1);

		textField_10.setColumns(10);
		textField_10.setBounds(257, 226, 95, 26);
		add(textField_10);

		JLabel lblNewLabel_4_1_1 = new JLabel("수리 비용");
		lblNewLabel_4_1_1.setBounds(198, 231, 61, 16);
		add(lblNewLabel_4_1_1);

		textField_12.setColumns(10);
		textField_12.setBounds(257, 259, 95, 26);
		add(textField_12);

		JLabel lblNewLabel_4_1_1_1 = new JLabel("기타 정비내역");
		lblNewLabel_4_1_1_1.setBounds(184, 264, 75, 16);
		add(lblNewLabel_4_1_1_1);

		textField_1.setBounds(257, 193, 95, 26);
		add(textField_1);

		textField_2.setColumns(10);
		textField_2.setBounds(79, 127, 103, 26);
		add(textField_2);

		tbl = new JTable();
		tbl.setBounds(352, 63, 608, 414);
		tbl.setRowSelectionAllowed(true);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.addMouseListener(new MyMouseListener());
		add(tbl);

		JButton btnNewButton = new JButton("정보 업데이트");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable();
			}
		});
		btnNewButton.setBounds(491, 22, 117, 29);
		add(btnNewButton);

		btnModify.setBounds(223, 335, 117, 29);
		add(btnModify);
		btnModify.addActionListener(this);
		btnDelete.setBounds(223, 376, 117, 29);
		add(btnDelete);
		btnDelete.addActionListener(this);

	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) { // 반환 버튼 클릭했을 때
		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("database connect success");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if (e.getSource() == btnSubmit) {
				if (textField_7.getText().equals("")) {
					if (textField_8.getText().equals("") || (textField_10.getText().equals(""))) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
					}
					returnCar(Integer.parseInt(textField_2.getText()), textField_3.getText(), textField_4.getText(),
							textField_5.getText(), textField_6.getText());
				} else {
					if ((textField_2.getText().equals("")))
						JOptionPane.showMessageDialog(null, "캠핑카 ID 값은 필수 입력사항입니다!!");
					else {
						returnFixCar(Integer.parseInt(textField_2.getText()), textField_3.getText(),
								textField_4.getText(), textField_5.getText(), textField_6.getText(),
								Integer.parseInt(textField_7.getText()), textField_8.getText(), textField_1.getText(),
								Integer.parseInt(textField_10.getText()), textField_12.getText());

						System.out.println(Integer.parseInt(textField_2.getText()));

					}
				}

			} else if (e.getSource() == btnModify) {
				if (textField_7.getText().equals("") || (textField_8.getText().equals(""))
						|| (textField_10.getText().equals("")) || (textField_1.getText().equals(""))) {
					JOptionPane.showMessageDialog(null, "필수 수리의뢰사항을 모두 입력하세요!!");
				} else {
					update(Integer.parseInt(tbl.getModel().getValueAt(row, 0).toString()), Integer.parseInt(textField_7.getText()),
							textField_8.getText(), textField_1.getText(), Integer.parseInt(textField_10.getText()),
							textField_12.getText());

					System.out.println("update");
				}

			} else if (e.getSource() == btnDelete) {
				delete(Integer.parseInt(tbl.getModel().getValueAt(row, 0).toString()));
			}
		} catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "형식에 맞는 값을 입력하세요!");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) { // 리스트에 마우스클릭이벤트발생시
			row = tbl.getSelectedRow();
			try {
				textField_7.setText((tbl.getModel().getValueAt(row, 1)).toString());
				textField_8.setText((tbl.getModel().getValueAt(row, 2)).toString());
				textField_1.setText((tbl.getModel().getValueAt(row, 3)).toString());
				textField_10.setText((tbl.getModel().getValueAt(row, 4)).toString());
				textField_12.setText((tbl.getModel().getValueAt(row, 5)).toString());
				System.out.println(row);
			} catch (NumberFormatException er) {
			} catch (Exception er) {
				er.printStackTrace();
			}
			btnSubmit.setEnabled(true);
			btnDelete.setEnabled(true);
			btnModify.setEnabled(true);
			if (row == 0) {
				btnDelete.setEnabled(false);
				btnModify.setEnabled(false);
				textField_1.setText("");
				textField_7.setText("");
				textField_8.setText("");
				textField_10.setText("");
				textField_12.setText("");
			}
		}
	}

	private void printTable() {

		// add header of the table
		String header[] = new String[] { "정비ID", "정비소ID", "정비내역", "정비날짜", "수리비용", "기타정비내역" };
		dtm.setRowCount(0);

		// add header in table model
		dtm.setColumnIdentifiers(header);
		tbl.setModel(dtm);

		String query_orders = "SELECT * FROM history_fix";
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
				dtm.addRow(new Object[] { rs.getInt(1), rs.getInt(4), rs.getString(6), rs.getString(7).substring(0, 10),
						rs.getInt(8), rs.getString(10) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void returnFixCar(int rent_id, String front, String back, String right, String left, int center_id,
			String fix_detail, String fix_date, int fix_price, String fix_etc) {
		try {
			pstmtFix = con.prepareStatement("SELECT * FROM history_rent WHERE id = " + rent_id + ";");
			rs = pstmtFix.executeQuery();
			while (rs.next()) {
				int rsInt2 = rs.getInt(2);
				pstmtReturn = con.prepareStatement(
						"insert into history_return (rent_id, rent_car_id, return_detail_front, return_detail_back, return_detail_right, return_detail_left, return_needfix)"
								+ "values(?,?,?,?,?,?,?);");
				pstmtReturn.setInt(1, rs.getInt(1));
				pstmtReturn.setInt(2, rsInt2);
				pstmtReturn.setString(3, front);
				pstmtReturn.setString(4, back);
				pstmtReturn.setString(5, right);
				pstmtReturn.setString(6, left);
				pstmtReturn.setBoolean(7, true);
				pstmtReturn.executeUpdate();
				System.out.println(pstmtReturn);
				pstmtFixFunc = con.prepareStatement(
						"insert into history_fix (fix_car_id, fix_center_id, fix_company_id, fix_customer_license, fix_detail, fix_date, fix_price, fix_deadline, fix_etc)"
								+ " values(?,?,?,?,?,?,?,?,?);");
				System.out.println(pstmtFixFunc);
				pstmtFixFunc.setInt(1, rsInt2);
				pstmtFixFunc.setInt(2, center_id);
				pstmtFixFunc.setInt(3, rs.getInt(4));
				pstmtFixFunc.setString(4, rs.getString(3));
				pstmtFixFunc.setString(5, fix_detail);
				pstmtFixFunc.setString(6, fix_date);
				pstmtFixFunc.setInt(7, fix_price);
				pstmtFixFunc.setString(8, fix_date);
				pstmtFixFunc.setString(9, fix_etc);
				pstmtFixFunc.executeUpdate();
				rentToReturn(rent_id, rsInt2);
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
	}

	private void returnCar(int rent_id, String front, String back, String right, String left) {

		try {
			// 반환 후 수리의뢰 필요
			pstmtFix = con.prepareStatement("SELECT * FROM history_rent WHERE id = " + rent_id + ";");
			rs = pstmtFix.executeQuery();
			while (rs.next()) {
				pstmtReturn = con.prepareStatement(
						"insert into history_return (rent_id, rent_car_id, return_detail_front, return_detail_back, return_detail_right, return_detail_left, return_needfix)"
								+ "values(?,?,?,?,?,?,?);");
				pstmtReturn.setInt(1, rs.getInt(1));
				pstmtReturn.setInt(2, rs.getInt(2));
				pstmtReturn.setString(3, front);
				pstmtReturn.setString(4, back);
				pstmtReturn.setString(5, right);
				pstmtReturn.setString(6, left);
				pstmtReturn.setBoolean(7, false);
				pstmtReturn.executeUpdate();
				rentToReturn(rent_id, rs.getInt(2));
			}
		} catch (SQLIntegrityConstraintViolationException er) {
			JOptionPane.showMessageDialog(null, "존재하지 않는 내역입니다!");
		} catch (Exception er) {
			er.printStackTrace();
		}
	}

	private void rentToReturn(int rent_id, int car_id) {
		try {
			pstmtUpdate = con.prepareStatement("update history_rent set returned = ? WHERE id = " + rent_id + ";");
//		값 대입
			pstmtUpdate.setInt(1, 1);
			pstmtUpdate.executeUpdate();

			PreparedStatement pstmtUpdate2 = con.prepareStatement("update car set available = ? where id = ?;");
//			값 대입
			pstmtUpdate2.setInt(2, car_id);
			pstmtUpdate2.setInt(1, 1);
			// 쿼리 실행
			pstmtUpdate2.executeUpdate();
		} catch (Exception e) {
		}

	}

	private void update(int id, int center, String detail, String date, int price, String etc) {
		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtUpdate = con.prepareStatement(
					"update history_fix set fix_center_id = ?, fix_detail = ?, fix_date = ?, fix_price = ?, fix_etc = ?  where id = ?");
			pstmtUpdate.setInt(1, center);
			pstmtUpdate.setString(2, detail);
			pstmtUpdate.setString(3, date);
			pstmtUpdate.setInt(4, price);
			pstmtUpdate.setString(5, etc);
			pstmtUpdate.setInt(6, id);
			// 쿼리 실행
			pstmtUpdate.executeUpdate();
			System.out.println(row);
			dtm.setValueAt(center, row, 1);
			dtm.setValueAt(detail, row, 2);
			dtm.setValueAt(date, row, 3);
			dtm.setValueAt(price, row, 4);
			dtm.setValueAt(etc, row, 5);
		} catch (MysqlDataTruncation er) {
			JOptionPane.showMessageDialog(null, "형식에 맞는 값을 입력하세요!");
		}catch (NumberFormatException er) {
			JOptionPane.showMessageDialog(null, "모든 값을 입력하세요!");
		} catch (Exception er) {
			er.printStackTrace();
		}

	}

	private void delete(int fix_id) {
		try {
//			PreparedStatement 생성-> CON은 preDbTreatment() 메소드를 통해 생성되어 있음
			pstmtDel = con.prepareStatement("delete from history_fix where id = ?");
//			id 값을 비교해서 삭제함
			pstmtDel.setInt(1, fix_id);
// 			대입받은 쿼리를 실행-> 삭제 (delete)
			pstmtDel.executeUpdate();
			dtm.removeRow(row);
		} catch (NumberFormatException er) {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
