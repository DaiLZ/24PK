package priv.for24.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import priv.for24.db.DBManager;
import priv.for24.db.databean.Question;

public class QuestionService {

	public ArrayList<Question> GetQuestion() {
	
		ArrayList<Question> Qs=new ArrayList<Question>();
		int id = 1;
		int count = 0;	
//		System.out.println(logSql);
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		try {
			while(count < 10) {
				id = (int)(Math.random()*60)+1; //目前题库中有60题
				String qSql = "SELECT num1,num2,num3,num4,answer FROM question" +
						" WHERE id = '"+id+"'";
				ResultSet rs = sql.executeQuery(qSql);
				while (rs.next()) {
					int num1 = rs.getInt(1);
					int num2 = rs.getInt(2);
					int num3 = rs.getInt(3);
					int num4 = rs.getInt(4);
					String answer = rs.getString(5);
					Question QBean = new Question(num1,num2,num3,num4,answer);
					Qs.add(QBean);
				}
				count++;
			}
			sql.closeDB();
			return Qs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return Qs;
	}
}
