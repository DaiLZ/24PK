package priv.for24.db.databean;

import java.util.ArrayList;

public class JRankBean {
	private ArrayList<User_rank> rank;
	
	public ArrayList<User_rank> getRank(){
		return rank;
	}
	public void setRank(ArrayList<User_rank> rank) {
		this.rank=rank;
	}
}
