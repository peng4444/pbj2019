package cn.pbj2019.demo.sso_vue.domain;


import cn.pbj2019.demo.sso_vue.model.User;

public class LoginResultDetails {
    private Integer status;
    private ResultDetails resultDetails;
    private User user;

    public ResultDetails getResultDetails() {
        return resultDetails;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setResultDetails(ResultDetails resultDetails) {
        this.resultDetails = resultDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
