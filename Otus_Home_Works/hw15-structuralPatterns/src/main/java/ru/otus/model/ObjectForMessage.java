package ru.otus.model;

import lombok.ToString;

import java.util.List;

@ToString
public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
