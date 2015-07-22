package com.technicalrex.springsecurityjwt.support.validation;

import com.google.common.base.Preconditions;

public final class StringConditions {
    private StringConditions() { }

    public static String checkNotBlank(String string) {
        Preconditions.checkArgument(string != null && string.trim().length() > 0);
        return string;
    }
}
