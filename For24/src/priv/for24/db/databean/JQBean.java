package priv.for24.db.databean;

import java.util.ArrayList;

public class JQBean {
	private String matchname;//Æ¥Åä¶ÔÊÖĞÕÃû
	private ArrayList<Question> qbeans;
	
	public String getMatchname() {
		return matchname;
	}
	public void setMatchname(String matchname) {
		this.matchname = matchname;
	}
	public ArrayList<Question> getQbeans() {
		return qbeans;
	}
	public void setQbeans(ArrayList<Question> qbeans) {
		this.qbeans = qbeans;
	}
}