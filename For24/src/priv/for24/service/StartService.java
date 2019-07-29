package priv.for24.service;
//servlet:��һ����״̬��������Ӧ���ͻ��˷���һ����������url��ֻ��Ҫ���ͼ򵥵�httprequest����
//service:����  server�����
import java.sql.ResultSet;
import java.sql.SQLException;

import priv.for24.db.DBManager;
import priv.for24.db.databean.User;

public class StartService {
	//��֤��¼
	public Boolean login(String username, String password) {
		//��ѯ�Ƿ����user��passwordƥ��ļ�¼
		String logSql = "select * from user where name ='"
				+ username + "' and password ='" + password + "'";
		System.out.println(logSql);
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(logSql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}
	//��ȡ�û���Ϣ
	public User GetUser(String username) {
		User userBean =null;
		String infoSql = "SELECT integral,total,win,lose,tie,high_grade "
				+ "FROM user WHERE name='" + username + "'";
		System.out.println(infoSql);
		// ��ȡDB����
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		// ����DB����
		try {
			ResultSet rs = sql.executeQuery(infoSql);
			while (rs.next()) {
				//123456��ʾ selected �е���� �����ݿ��к�
				int integral = rs.getInt(1);
				int total = rs.getInt(2);
				int win = rs.getInt(3);
				int lose = rs.getInt(4);
				int tie = rs.getInt(5);
				int high_grade = rs.getInt(6);
				userBean = new User(username, integral, total, win, lose, tie, high_grade);
			}
			sql.closeDB();
			return userBean;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return userBean;
	}
	//ע��
	public Boolean register(String username, String password) {
		if (!this.namerepeat(username)) { //��ע��
			String regSql = "insert into user(name,password,integral,total,win,lose,tie,high_grade) "
					+ "values('" + username + "','" + password + "',0,0,0,0,0,0)";
			System.out.println(regSql);
			DBManager sql = DBManager.createInstance();
			sql.connectDB();
			int ret = sql.executeUpdate(regSql);
			if (ret > 0) { //���ڲ����¼
				sql.closeDB();
				return true;
			}
			sql.closeDB();
		}
		return false;
	}
	//�û����Ƿ��ѱ�ע��
	public Boolean namerepeat(String username) {
		String checkSql = "select name from user where name ='" + username + "'";
		System.out.println(checkSql);
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		try {
			ResultSet rs = sql.executeQuery(checkSql);
			if (rs.next()) { //re���м�¼������user
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}
}