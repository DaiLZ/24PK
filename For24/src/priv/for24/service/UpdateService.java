package priv.for24.service;

import priv.for24.db.DBManager;

public class UpdateService {

	public void updatehigh(String username,int high_grade){
		String gradeSql = "update user set high_grade="+high_grade
				+ " where name ='" + username + "' and high_grade <" + high_grade;
		System.out.println(gradeSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		int ret = sql.executeUpdate(gradeSql);
		if (ret != 0) {
			sql.closeDB();
			System.out.println(username+" 破记录！");
		}
		sql.closeDB();
	}
	public void updatewin(String username,int score) {
		String gradeSql = "update user set win=(win +1),total=(total+1),integral=(integral+"+score+")"
				+ " where name ='" + username + "'";
		System.out.println(gradeSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		int ret = sql.executeUpdate(gradeSql);
		if (ret != 0) {
			sql.closeDB();
			System.out.println(username+" win！");
		}
		sql.closeDB();
	}
	public void updatelose(String username) {
		String gradeSql = "update user set lose=(lose +1),total=(total+1),integral=(integral-2)"
				+ " where name ='" + username + "'";
		System.out.println(gradeSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		int ret = sql.executeUpdate(gradeSql);
		if (ret != 0) {
			sql.closeDB();
			System.out.println(username+" lose！");
		}
		sql.closeDB();
	}
	public void updatetie(String username) {
		String gradeSql = "update user set tie=(tie +1),total=(total+1),integral=(integral+1)"
				+ " where name ='" + username + "'";
		System.out.println(gradeSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		int ret = sql.executeUpdate(gradeSql);
		if (ret != 0) {
			sql.closeDB();
			System.out.println(username+" tie！");
		}
		sql.closeDB();
	}
	
}
