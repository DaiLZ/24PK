package priv.for24.service;
//servlet:是一种无状态的请求响应，客户端访问一个服务器的url，只需要发送简单的httprequest即可
//service:功能  server：软件
import java.sql.ResultSet;
import java.sql.SQLException;

import priv.for24.db.DBManager;
import priv.for24.db.databean.User;

public class StartService {
	//验证登录
	public Boolean login(String username, String password) {
		//查询是否存在user、password匹配的记录
		String logSql = "select * from user where name ='"
				+ username + "' and password ='" + password + "'";
		System.out.println(logSql);
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		// 操作DB对象
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
	//获取用户信息
	public User GetUser(String username) {
		User userBean =null;
		String infoSql = "SELECT integral,total,win,lose,tie,high_grade "
				+ "FROM user WHERE name='" + username + "'";
		System.out.println(infoSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(infoSql);
			while (rs.next()) {
				//123456表示 selected 列的序号 非数据库列号
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
	//注册
	public Boolean register(String username, String password) {
		if (!this.namerepeat(username)) { //已注册
			String regSql = "insert into user(name,password,integral,total,win,lose,tie,high_grade) "
					+ "values('" + username + "','" + password + "',0,0,0,0,0,0)";
			System.out.println(regSql);
			DBManager sql = DBManager.createInstance();
			sql.connectDB();
			int ret = sql.executeUpdate(regSql);
			if (ret > 0) { //存在插入记录
				sql.closeDB();
				return true;
			}
			sql.closeDB();
		}
		return false;
	}
	//用户名是否已被注册
	public Boolean namerepeat(String username) {
		String checkSql = "select name from user where name ='" + username + "'";
		System.out.println(checkSql);
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		try {
			ResultSet rs = sql.executeQuery(checkSql);
			if (rs.next()) { //re中有记录，存在user
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