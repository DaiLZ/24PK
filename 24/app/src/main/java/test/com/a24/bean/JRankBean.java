package test.com.a24.bean;

import java.util.List;

public class JRankBean {
    private List<User_rank> rank;

    public List<User_rank> getRank(){
        return rank;
    }
    public void setRank(List<User_rank> rank) {
        this.rank=rank;
    }
}
