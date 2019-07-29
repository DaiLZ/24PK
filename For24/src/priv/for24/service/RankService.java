package priv.for24.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import priv.for24.db.DBManager;
import priv.for24.db.databean.User_rank;

public class RankService {
	public ArrayList<User_rank> Getrank() {
		
		ArrayList<User_rank> rank=new ArrayList<User_rank>();
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		try {
			String rankSql = "SELECT name,high_grade FROM user order by high_grade desc limit 10";
			ResultSet rs = sql.executeQuery(rankSql);
			while (rs.next()) {
				String name = rs.getString(1);
				int high = rs.getInt(2);
				User_rank Rank=new User_rank(name, high);
				rank.add(Rank);
			}
			sql.closeDB();
			return rank;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return rank;
	}

}
