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

public class TabSrch1 extends JPanel {
	private JTable tbl = new JTable();
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

	JButton btn1 = new JButton("검색1");
	JButton btn2 = new JButton("검색2");
	JButton btn3 = new JButton("검색3");
	JButton btn4 = new JButton("검색4");

	/**
	 * Create the panel.
	 */
	public TabSrch1() {
		setLayout(null);
		tbl.setRowSelectionAllowed(true);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.setBounds(29, 61, 908, 433);
		add(tbl);

		btn4.setBounds(420, 20, 117, 29);
		add(btn4);

		btn2.setBounds(160, 20, 117, 29);
		add(btn2);

		btn1.setBounds(31, 20, 117, 29);
		add(btn1);

		btn3.setBounds(291, 20, 117, 29);
		add(btn3);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable(1);
			}
		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable(2);
			}
		});
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable(3);
			}
		});
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable(4);
			}
		});

	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void printTable(int srch) {
		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("database connect success");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (srch == 1) {
			try {
				String header[] = new String[] { "총 대여횟수", "총 반납횟수" };
				dtm.setRowCount(0);
				dtm.setColumnIdentifiers(header);
				tbl.setModel(dtm);
				String query_orders = "\n" + "SELECT  (\n" + "        SELECT COUNT(*)\n" + "        FROM history_rent\n"
						+ "        ) AS Rent_Total,\n" + "       (\n" + "        SELECT COUNT(*)\n"
						+ "        FROM history_return\n" + "        ) AS Return_Total \n" + "FROM    dual;";
				pstmt = con.prepareStatement(query_orders);
				rs = pstmt.executeQuery();
				dtm.addRow(header);
				while (rs.next()) {
					dtm.addRow(new Object[] { rs.getInt(1), rs.getInt(2) });
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (srch == 2) {
			try {
				String header[] = new String[] { "총 반납횟수", "총 수리신청횟수" };
				dtm.setRowCount(0);
				dtm.setColumnIdentifiers(header);
				tbl.setModel(dtm);
				String query_orders = "\n" + "SELECT(\n" + "        SELECT COUNT(*)\n" + "        FROM history_return\n"
						+ "        ) AS Return_Total,\n" + "       (\n" + "        SELECT COUNT(*)\n"
						+ "        FROM history_fix\n" + "        ) AS Fix_Total\n" + "FROM    dual;";
				pstmt = con.prepareStatement(query_orders);
				rs = pstmt.executeQuery();
				dtm.addRow(header);
				while (rs.next()) {
					dtm.addRow(new Object[] { rs.getInt(1), rs.getInt(2) });
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (srch == 3) {
			try {
				//
				String header[] = new String[] { "캠핑카ID", "캠핑카이름", "차량번호", "승차인원", "제조회사", "제조연도", "누적주행거리", "대여비용",
						"소속회사ID", "등록일자" };
				dtm.setRowCount(0);
				dtm.setColumnIdentifiers(header);
				tbl.setModel(dtm);
				String query_orders = "SELECT * FROM car INNER JOIN history_fix "
						+ "ON car.id = history_fix.fix_car_id WHERE history_fix.fix_detail = '헤드라이트고장';";
				pstmt = con.prepareStatement(query_orders);
				rs = pstmt.executeQuery();
				dtm.addRow(header);
				while (rs.next()) {
					dtm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
							rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
							rs.getString(10) });
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (srch == 4) {
			try {
				//
				String header[] = new String[] { "운전면허번호", "고객명", "고객주소", "고객연락처", "고객이메일" };
				dtm.setRowCount(0);
				dtm.setColumnIdentifiers(header);
				tbl.setModel(dtm);
				String query_orders = "\n" + 
						"SELECT * FROM customer LEFT JOIN history_rent ON customer.customer_license = history_rent.rent_customer_license WHERE history_rent.rent_price<10000;";
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

	}
}