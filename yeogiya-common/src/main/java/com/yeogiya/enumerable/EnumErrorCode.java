package com.yeogiya.enumerable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumErrorCode {

    SUCCESS(1, "Success"); // example

    private final int result;
    private final String message;
}
