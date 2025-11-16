package com.example.justlifetest.api.response;

import java.util.Objects;

public class ResponseOfGet<T> {

    private T result;

    public ResponseOfGet(T result) {
        this.result = result;
    }

    public ResponseOfGet() {

    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getValue() {
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseOfGet<?> that)) {
            return false;
        }
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }
}

