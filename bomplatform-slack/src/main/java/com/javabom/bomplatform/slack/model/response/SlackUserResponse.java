package com.javabom.bomplatform.slack.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackUserResponse {

    private User user;

    public String getUserId() {
        return this.user.getId();
    }

    @Getter
    @NoArgsConstructor
    private class User {
        private String id;
    }
}
