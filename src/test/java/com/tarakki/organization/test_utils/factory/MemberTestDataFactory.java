package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.entity.Member;
import com.tarakki.common.enums.AccountStatus;

import java.util.UUID;

public class MemberTestDataFactory {
    public static Member createMemberEntity() {
        Member member = new Member();
        member.setMemberId(UUID.randomUUID());
        member.setFirstName("firstName");
        member.setLastName("lastName");
        member.setEmail("example@gmail.com");
        member.setAccountStatus(AccountStatus.ACTIVE);
        return member;
    }
}