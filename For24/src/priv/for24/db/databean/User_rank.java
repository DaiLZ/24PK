package priv.for24.db.databean;

public class User_rank {
	private String name;
	private int high_grade;
	
	public User_rank(String name,int high_grade) {
		this.high_grade=high_grade;
		this.name=name;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
