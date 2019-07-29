package test.com.a24.bean;

import java.util.List;

public class JQBean {
    private List<QuestionBean> qbeans;
    private String matchname;//匹配对手姓名

    public List<QuestionBean> getQbeans() {
        return qbeans;
    }
    public void setQbeans(List<QuestionBean> qbeans) {
        this.qbeans = qbeans;
    }
    public String getMatchname() {
        return matchname;
    }
    public void setMatchname(String matchname) {
        this.matchname = matchname;
    }
}
