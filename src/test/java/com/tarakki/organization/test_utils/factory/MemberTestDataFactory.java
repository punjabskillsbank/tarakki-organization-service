package com.tarakki.organization.test_utils.factory;

import java.util.UUID;

public class MemberTestDataFactory {

    public static String createMemberResponseBody(UUID memberId) {
        return "{\"memberId\":\"" + memberId + "\",\"name\":\"John Doe\"}";
    }
}
