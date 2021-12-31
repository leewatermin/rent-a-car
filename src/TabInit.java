import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class TabInit extends JPanel {

	static Connection con;
	static Statement stmt;
	static PreparedStatement pstmt;
	static ResultSet rs;
	static String Driver = "";
	static String url = "jdbc:mysql://localhost:3306/madang" + "?&serverTimezone=Asia/Seoul&useSSL=false";
	static String userid = "madang";
	static String pwd = "madang";

	/**
	 * Create the panel.
	 */
	public TabInit() {
		setLayout(null);

		JButton btnNewButton = new JButton("데이터 초기화 (이 동작은 되돌릴 수 없습니다!)");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				initDB();
				JOptionPane.showMessageDialog(null, "데이터 초기화 성공!");
			}
		});
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(185, 101, 644, 306);
		add(btnNewButton);

	}

	private static void initDB() {
		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공!");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
		try {
			// 생성하는 구문 짜보기
			stmt = con.createStatement();

			StringBuilder sb = new StringBuilder();

			String sql = sb.append("DROP TABLE IF EXISTS `history_fix`;").toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("DROP TABLE IF EXISTS `history_return`;").toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("DROP TABLE IF EXISTS `history_rent`;").toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("DROP TABLE IF EXISTS `car`;").toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("DROP TABLE IF EXISTS `customer`;").toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("DROP TABLE IF EXISTS `center`;").toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("DROP TABLE IF EXISTS `company`;").toString();

			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `company`(").append("`id` INT NOT NULL AUTO_INCREMENT,")
					.append("`company_name` VARCHAR(45) NOT NULL,").append("`company_address` VARCHAR(45) NOT NULL,")
					.append("`company_number` VARCHAR(45) NOT NULL,")
					.append("`company_employee_name` VARCHAR(45) NOT NULL,")
					.append("`company_employee_email` VARCHAR(45) NOT " + "NULL,")
					.append("PRIMARY KEY (`id`))\nENGINE = InnoDB;").toString();

			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `car`(").append("`id` INT NOT NULL AUTO_INCREMENT,")
					.append("`car_name` VARCHAR(45) NOT NULL,").append("`car_number` VARCHAR(45) NOT NULL UNIQUE,")
					.append("`car_capacity` INT NOT NULL,").append("`car_brand` VARCHAR(45) NULL,")
					.append("`car_year` INT NOT NULL,").append("`car_run` INT NOT NULL,")
					.append("`car_price` INT NOT NULL,").append("`available` INT DEFAULT 1,")
					.append("PRIMARY KEY (`id`),").append("`car_company_id` INT NOT NULL,")
					.append("`car_adddate` DATETIME NOT NULL,")
					.append("INDEX `car_company_id_idx` (`car_company_id` ASC) VISIBLE,")
					.append(" CONSTRAINT `car_company_id`\n" + "    FOREIGN KEY (`car_company_id`)\n"
							+ "    REFERENCES `company` (`id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION)\nENGINE = InnoDB;")
					.toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `customer`(").append("`id` INT NOT NULL AUTO_INCREMENT,")
					.append("`customer_license` VARCHAR(45) UNIQUE,").append("`customer_name` VARCHAR(45) NOT NULL,")
					.append("`customer_address` VARCHAR(45) NOT NULL,")
					.append("`customer_number` VARCHAR(45) NOT NULL,").append("`customer_email` VARCHAR(45) NOT NULL,")
					.append("INDEX `customer_license_idx` (`customer_license` ASC) VISIBLE,")
					.append("PRIMARY KEY (`id`))\nENGINE = InnoDB;").toString();

			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `center`(").append("`id` INT NOT NULL AUTO_INCREMENT,")
					.append("`center_name` VARCHAR(45) NOT NULL,").append("`center_address` VARCHAR(45) NOT NULL,")
					.append("`center_number` VARCHAR(45) NOT NULL,").append("`center_employee_name` VARCHAR(45) NULL,")
					.append("`center_employee_email` VARCHAR(45) NULL,").append("PRIMARY KEY (`id`))\nENGINE = InnoDB;")
					.toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `history_rent`(").append("`id` INT NOT NULL AUTO_INCREMENT,")
					.append("`car_id` INT NOT NULL,").append("`rent_customer_license` VARCHAR(45) NOT NULL,")
					.append("`rent_company_id` INT NOT NULL,").append("`rent_start_date` DATETIME NOT NULL,")
					.append("`rent_length` INT NOT NULL,").append("`rent_price` INT NOT NULL,")
					.append("`rent_deadline` DATETIME NOT NULL,").append("`rent_etc` VARCHAR(45) default '내용 없음',")
					.append("`rent_etc_price` INT default 0,").append("`returned` INT default 0,")
					.append("PRIMARY KEY (`id`),")
					.append(" CONSTRAINT `rent_customer_license`\n" + "    FOREIGN KEY (`rent_customer_license`)\n"
							+ "    REFERENCES `customer` (`customer_license`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION,")
					.append("CONSTRAINT `car_id`\n" + "    FOREIGN KEY (`car_id`)\n" + "    REFERENCES `car` (`id`)\n"
							+ "    ON DELETE NO ACTION\n" + "    ON UPDATE NO ACTION,")
					.append("CONSTRAINT `rent_company_id`\n" + "    FOREIGN KEY (`rent_company_id`)\n"
							+ "    REFERENCES `company` (`id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION)\nENGINE = InnoDB;")
					.toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `history_return`(").append("`rent_id` INT NOT NULL,")
					.append("`rent_car_id` INT NOT NULL,").append("`return_detail_front` VARCHAR(45) NULL,")
					.append("`return_detail_back` VARCHAR(45) NULL,").append("`return_detail_right` VARCHAR(45) NULL,")
					.append("`return_detail_left` VARCHAR(45) NULL,").append("`return_needfix` TINYINT NOT NULL,")
					.append("INDEX `rent_car_id_idx` (`rent_car_id` ASC) VISIBLE,").append(" PRIMARY KEY (`rent_id`),")
					.append("CONSTRAINT `rent_car_id`\n" + "    FOREIGN KEY (`rent_car_id`)\n"
							+ "    REFERENCES `history_rent` (`car_id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION,")
					.append(" CONSTRAINT `rent_id`\n" + "    FOREIGN KEY (`rent_id`)\n"
							+ "    REFERENCES `history_rent` (`id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION)\nENGINE = InnoDB;")
					.toString();
			stmt.execute(sql);
			sb.setLength(0);
			sql = sb.append("CREATE TABLE if not exists `history_fix`(").append("`id` INT NOT NULL AUTO_INCREMENT,")
					.append("`fix_car_id` INT NOT NULL,").append("`fix_center_id` INT NOT NULL,")
					.append("`fix_company_id` INT NOT NULL,").append("`fix_customer_license` VARCHAR(45) NOT NULL,")
					.append("`fix_detail` VARCHAR(45) NOT NULL,").append("`fix_date` DATETIME NOT NULL,")
					.append("`fix_price` INT NOT NULL,").append("`fix_deadline` DATETIME NOT NULL,")
					.append("`fix_etc` VARCHAR(45) NULL,").append("INDEX `fix_car_id_idx` (`fix_car_id` ASC) VISIBLE,")
					.append(" PRIMARY KEY (`id`),")
					.append(" CONSTRAINT `fix_car_id`\n" + "    FOREIGN KEY (`fix_car_id`)\n"
							+ "    REFERENCES `history_return` (`rent_car_id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION,\n")
					.append(" CONSTRAINT `fix_center_id`\n" + "    FOREIGN KEY (`fix_center_id`)\n"
							+ "    REFERENCES `center` (`id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION,\n")
					.append(" CONSTRAINT `fix_company_id`\n" + "    FOREIGN KEY (`fix_company_id`)\n"
							+ "    REFERENCES `history_rent` (`rent_company_id`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION,\n")
					.append("CONSTRAINT `fix_customer_license`\n" + "    FOREIGN KEY (`fix_customer_license`)\n"
							+ "    REFERENCES `history_rent` (`rent_customer_license`)\n" + "    ON DELETE NO ACTION\n"
							+ "    ON UPDATE NO ACTION)\nENGINE = InnoDB;")
					.toString();
			stmt.execute(sql);
			sb.setLength(0);

			String query_init = "insert into company (company_name, company_address, company_number, company_employee_name, company_employee_email)"
					+ "VALUES('HYUNDAI렌트', '서울', '02-999-1111', '이현대', 'aaa@hyundai.kr'),"
					+ "('기아렌트카', '서울', '02-1111-2222', '박기아', 'bbb@kia.com'),"
					+ "('뽀로로렌트', '경기도', '031-9999-9999', '뽀로로', 'aaa@gmail.com'),"
					+ "('뿌요뿌요렌트카', '광진구', '02-1234-1234', '박폴셰', 'ccc@porshe.kr'),"
					+ "('최고다렌트카', '은평구', '02-000-0000	', '포호드', 'for@ford.com'),"
					+ "('박렌트카', '관악구', '02-234-1324', '랜도바', 'lala@land.com'),"
					+ "('ABC카', '송파구', '02-124-1241	', '최지푸', 'jeep@jp.com'),"
					+ "('빠르다렌트', '강남구', '02-4234-1241', '권람보', 'lb@lamb.kr'),"
					+ "('제주사랑렌트카', '땅끝마을	', '02-1341-3325', '박요타', 'dy11@naa.com'),"
					+ "('가족사랑렌트카', '양꼬치마을', '02-1513-1242', '문용용', 'bbb@naver.com'),"
					+ "('친구사랑렌트카', '강남역', '02-1214-5343', '이르노', 'fmsh@naver.com'),"
					+ "('외사랑렌트카', '을지로', '02-3252-1223', '박박박', 'doo@ss.com'),"
					+ "('우렌트카', '신촌', '02-225-1711', '최우', 'fam33@naver.com'),"
					+ "('친렌트카', '강남역', '02-314-5473', '이친', 'fms2h@naver.com'),"
					+ "('사랑렌트카', '을지로', '02-312-1223', '우사', 'voo@his.com')";
			stmt.executeUpdate(query_init);
			query_init = "INSERT INTO CAR (car_name, car_number, car_capacity, car_brand, car_year, car_run, car_price, car_company_id, car_adddate)"
					+ "VALUES('소나타', '소1111', 5, '현대자동차', 2010, 80000, 2000, 9, '2010-03-01'), "
					+ "('소나타', '소2222', 5, '현대자동차', 2010, 100000, 2000, 3, '2010-05-01'), "
					+ "('소나타', '소3333', 5, '현대자동차', 2010, 100000, 2000, 2, '2010-05-01'), "
					+ "('소나타', '소4444', 5, '현대자동차', 2012, 30000, 2000, 11, '2012-01-02'), "
					+ "('소나타', '소5555', 5, '현대자동차', 2012, 20000, 2000, 13, '2012-11-11'), "
					+ "('레인지로버', '레1111', 7, '랜드로버', 2012, 20000, 10000, 2, '2012-12-01'), "
					+ "('레인지로버', '레2222', 7, '랜드로버', 2012, 1000, 10000, 3, '2012-12-11'), "
					+ "('레인지로버', '레3333', 7, '랜드로버', 2012, 100000, 10000, 4, '2012-12-21'), "
					+ "('NEW 레인지로버', '레4444', 7, '랜드로버', 2015, 30000, 10000, 5, '2015-11-02'), "
					+ "('NEW 레인지로버', '레5555', 7, '랜드로버', 2015, 100000, 10000, 1, '2015-11-11'), "
					+ "('티볼리', '티1111', 7, '쌍용자동차', 2016, 90000, 4000, 3, '2016-12-01'), "
					+ "('티볼리', '티2222', 7, '쌍용자동차', 2016, 1000, 4000, 10, '2016-12-11'), "
					+ "('티볼리', '티3333', 7, '쌍용자동차', 2016, 40000, 4000, 5, '2017-12-21'), "
					+ "('2019 티볼리', '티4444', 7, '쌍용자동차', 2019, 30000, 4000, 10, '2019-11-02'), "
					+ "('2019 티볼리', '티5555', 7, '쌍용자동차', 2019, 40000, 4000, 1, '2019-11-11')";
			stmt.executeUpdate(query_init);

			query_init = "insert into center (center_name, center_address, center_number, center_employee_name, center_employee_email)"
					+ "VALUES('다고쳐정비소', '서울시 광진구', '02-9999-9999', '다고쳐', 'ekr13@naver.com'),"
					+ "('잘고침정비소', '서울시 송파구', '010-1111-2929', '잘고쳐', 'wkf2@nana.com'),"
					+ "('세종정비소', '서울시 광진구', '031-9999-9999', '권세종', 'rd11@gmail.com'),"
					+ "('건국정비소', '서울시 광진구', '02-1234-1234', '박건국' , 'rj2r@she.kr'),"
					+ "('하하정비소', '서울시 하하구', '02-000-0000 ', '박하하', 'gkgk@haha.com'),"
					+ "('호호정비소', '서울시 호호구', '010-1234-1324', '이호호', 'lghgh@hd.com'),"
					+ "('웃으렴정비소', '비키니시티', '02-1224-1241 ', '박웃음', 'happy@jp.com'),"
					+ "('머리아파정비소', '나뭇잎마을', '010-4234-1241', '권정비', 'fix@lamb.kr'),"
					+ "('손목통증정비소', '나무마을 ', '02-1000-2222', '박정비', 'wjdql@naa.com'),"
					+ "('거북이정비소', '지구마을', '02-1513-1772', '최정비', 'wjdql11@naver.com'),"
					+ "('시력저하정비소', '산타마을 난쟁이로', '02-1666-5343', '이정비', 'fms2@naver.com'),"
					+ "('배나와정비소', '눈아파마을 아이고길', '02-3555-1223', '서정비', 'doo13@sss.com'),"
					+ "('뿌링클정비소', '신촌 한복판', '02-0000-1711', '민정비', 'fam313@naver.com'),"
					+ "('엽기떠뽀끼정비소', '강남역 10번출구', '02-314-0000', '박정비', 'fms23@naver.com'),"
					+ "('거북목정비소', '을지로 갈색건물 2층', '02-3121-0003', '김거북', 'voo4@naver.com')";

			stmt.executeUpdate(query_init);
			query_init = "insert into customer (customer_license, customer_name, customer_address, customer_number, customer_email)"
					+ "VALUES('111111111101', '채송화', '서울시 광진구', '010-0000-0001', 'cust01@gmail.com'),"
					+ "('111111111102', '김준완', '서울시 성동구', '010-0000-0002', 'cust02@gmail.com'),"
					+ "('111111111103', '이익준', '서울시 동대문구', '010-0000-0003', 'cust03@gmail.com'),"
					+ "('111111111104', '안정원', '서울시 중랑구', '010-0000-0004', 'cust04@gmail.com'),"
					+ "('111111111105', '장겨울', '서울시 강동구', '010-0000-0005', 'cust05@gmail.com'),"
					+ "('111111111106', '약석형', '서울시 송파구', '010-0000-0006', 'cust06@gmail.com'),"
					+ "('111111111107', '추민하', '서울시 강남구', '010-0000-0007', 'cust07@gmail.com'),"
					+ "('111111111108', '안치홍', '서울시 서초구', '010-0000-0008', 'cust08@gmail.com'),"
					+ "('111111111109', '도재학', '서울시 용산구', '010-0000-0009', 'cust09@gmail.com'),"
					+ "('111111111110', '용석민', '서울시 중구', '010-0000-0010', 'cust10@gmail.com'),"
					+ "('111111111111', '허선빈', '서울시 종로구', '010-0000-0011', 'cust11@gmail.com'),"
					+ "('111111111112', '명은원', '서울시 광진구', '010-0000-0012', 'cust12@gmail.com'),"
					+ "('111111111113', '장윤복', '서울시 광진구', '010-0000-0013', 'cust13@gmail.com'),"
					+ "('111111111114', '장홍도', '서울시 광진구', '010-0000-0014', 'cust14@gmail.com'),"
					+ "('111111111115', '송수빈', '서울시 서대문구', '010-0000-0015', 'cust15@gmail.com')";

			stmt.executeUpdate(query_init);
			query_init = "insert into history_rent (car_id, rent_customer_license, rent_company_id, rent_start_date, rent_length, rent_price, rent_deadline, rent_etc, rent_etc_price, returned)"
					+ "VALUES(1, 111111111111, 5, '2015-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (2, 111111111112, 5, '2015-03-03', 5, 10000, '2015-03-03', '내용 없음', 0, 1), "
					+ " (3, 111111111113, 5, '2019-04-03', 4, 8000, '2019-04-03', '내용 없음', 0, 1), "
					+ " (4, 111111111114, 5, '2019-05-03', 5, 10000, '2019-05-03', '내용 없음', 0, 1), "
					+ " (5, 111111111111, 5, '2019-06-03', 3, 6000, '2019-06-03', '내용 없음', 0, 1), "
					+ " (1, 111111111113, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (2, 111111111114, 5, '2019-02-03', 2, 4000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (3, 111111111101, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (4, 111111111103, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (5, 111111111111, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (1, 111111111101, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (1, 111111111111, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (1, 111111111114, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (1, 111111111114, 5, '2019-02-03', 5, 10000, '2019-02-03', '내용 없음', 0, 1), "
					+ " (1, 111111111111, 5, '2019-02-03', 5, 10000, '2019-02-02', '내용 없음', 0, 0)";
			stmt.executeUpdate(query_init);
			query_init = "insert into history_return (rent_id, rent_car_id, return_needfix)" + "VALUES(1, 1, 1),"
					+ " (2, 2, 1)," + " (3, 3, 1)," + " (4, 4, 1)," + " (5, 5, 1)," + " (6, 1, 1)," + " (7,2,1),"
					+ " (8,3, 1)," + " (9, 4, 1)," + " (10, 5, 1)," + " (11, 1, 1)," + " (12, 1, 1)," + " (13, 1, 1),"
					+ " (14, 1, 1)," + " (15, 1, 1)";
			stmt.executeUpdate(query_init);
			query_init = "insert into history_fix (fix_car_id, fix_center_id, fix_company_id, fix_customer_license, fix_detail, fix_date, fix_price, fix_deadline, fix_etc)"
					+ "VALUES(1, 1, 5, '111111111111', '앞바퀴', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(2, 2, 5, '111111111112', '뒷바퀴', '2019-05-01', 20000, '2019-05-05', '없음'),"
					+ "(3, 3, 5, '111111111113', '범퍼', '2019-05-03', 100000, '2019-05-05', '없음'),"
					+ "(4, 4, 5, '111111111114', '앞바퀴고장', '2019-05-05', 100000, '2019-05-05', '없음'),"
					+ "(5, 5, 5, '111111111111', '헤드라이트고장', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(1, 10, 5, '111111111113', '앞바퀴', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(2, 2, 5, '111111111114', '앞바퀴고장', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(3, 3, 5, '111111111101', '헤드라이트고장', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(4, 4, 5, '111111111103', '앞바퀴', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(5, 5, 5, '111111111111', '브레이크고장', '2019-05-05', 40000, '2019-05-05', '없음'),"
					+ "(1, 5, 5, '111111111101', '앞바퀴', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(1, 1, 5, '111111111111', '헤드라이트고장', '2019-05-05', 100000, '2019-05-05', '없음'),"
					+ "(1, 2, 5, '111111111114', '앞바퀴', '2019-05-05', 10000, '2019-05-05', '없음'),"
					+ "(1, 4, 5, '111111111114', '엔진오일교체', '2019-05-09', 1000, '2019-05-05', '없음'),"
					+ "(1, 10, 5, '111111111111', '헤드라이트고장', '2019-05-10', 10000, '2019-05-05', '없음')";
			stmt.executeUpdate(query_init);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}

}
