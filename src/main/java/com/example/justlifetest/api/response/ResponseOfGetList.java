package com.example.justlifetest.api.response;

import java.util.List;
import java.util.Objects;

public class ResponseOfGetList<T> {
    private List<T> resultList;

    public ResponseOfGetList() {
    }

    public ResponseOfGetList(List<T> list) {
        this.resultList = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseOfGetList<?> that)) return false;
        return Objects.equals(resultList, that.resultList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resultList);
    }

    public List<T> getList() {
        return this.resultList;
    }

    public void setList(List<T> resultList) {
        this.resultList = resultList;
    }
}

