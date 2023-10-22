package com.yeogiya.web.util;

import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import org.springframework.util.ObjectUtils;

import java.security.Principal;

public class MemberUtil {

    public static Long getMemberId(Principal principal) {
        if (ObjectUtils.isEmpty(principal)) {
            throw new ClientException.Unauthorized(EnumErrorCode.INVALID_MEMBER_STATUS);
        }

        return Long.parseLong(principal.getName());
    }
}
