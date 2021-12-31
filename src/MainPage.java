import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;

public class MainPage extends JFrame {

	static Connection con;
	static Statement stmt;
	static PreparedStatement pstmt;
	static ResultSet rs;
	static String Driver = "";
	static String url = "jdbc:mysql://localhost:3306/madang" + "?&serverTimezone=Asia/Seoul&useSSL=false";
	static String userid = "madang";
	static String pwd = "madang";

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		conDB();

	}

	public static void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public MainPage() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 661);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("16010991 이수민 Final");
		lblNewLabel.setBounds(22, 14, 290, 40);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);

		JButton btnNewButton_1 = new JButton("관리자");

		btnNewButton_1.setBounds(703, 20, 130, 35);

		contentPane.add(btnNewButton_1);

		JButton btnNewButton = new JButton("사용자");
		btnNewButton.setBounds(848, 20, 130, 35);

		contentPane.add(btnNewButton);

		JTabbedPane AdminTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		AdminTabbedPane.setBackground(Color.WHITE);
		AdminTabbedPane.setBounds(6, 66, 988, 566);
		contentPane.add(AdminTabbedPane);
		JTabbedPane UserTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		UserTabbedPane.setBackground(Color.WHITE);
		UserTabbedPane.setBounds(6, 66, 988, 567);
		contentPane.add(UserTabbedPane);
		AdminTabbedPane.addTab("회사 목록", new TabCompany());
		AdminTabbedPane.addTab("캠핑카 목록", new TabCar());
		AdminTabbedPane.addTab("고객 목록", new TabCustomer());
		AdminTabbedPane.addTab("정비소 목록", new TabCenter());
		AdminTabbedPane.addTab("캠핑카 반환", new TabReturn());
		AdminTabbedPane.addTab("초기화", new TabInit());
		AdminTabbedPane.addTab("검색하기", new TabSrch1());

		UserTabbedPane.addTab("캠핑카 대여", new TabRentCustomer());
		AdminTabbedPane.hide();
		UserTabbedPane.hide();

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminTabbedPane.show();
				UserTabbedPane.hide();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminTabbedPane.hide();
				UserTabbedPane.show();
			}
		});

	}
}
