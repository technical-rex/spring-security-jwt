package com.technicalrex.springsecurityjwt.api.greetings;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.technicalrex.springsecurityjwt.support.validation.StringConditions;

public class Greeting {
    private final String text;

    public Greeting(String text) {
        this.text = StringConditions.checkNotBlank(text);
    }

    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Greeting that = (Greeting) o;
        return Objects.equal(this.text, that.text);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .toString();
    }
}
