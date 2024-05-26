package org.fullstack4.hammerteen.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
   user("ROLE_USER", "일반 사용자");

    private final String key;
    private final String string;
}
