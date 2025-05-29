package com.aparttime.exception.member;

import static com.aparttime.exception.ErrorCode.*;

import com.aparttime.exception.RestApiException;

public class MemberNotFoundException extends RestApiException {

    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
    }
}
