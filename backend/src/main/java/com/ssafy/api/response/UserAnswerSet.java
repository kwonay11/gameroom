package com.ssafy.api.response;

public class UserAnswerSet {
    private Long id;
    private String nickname;
    private Integer answer;
    public UserAnswerSet(Long id, String nickname, Integer answer) {
        this.id = id;
        this.nickname = nickname;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getAnswer() {
        return answer;
    }
}
