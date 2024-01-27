package ru.otus.model;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() {
        try {
            ObjectForMessage clonedObject = (ObjectForMessage) super.clone();
            clonedObject.data = new ArrayList<>(this.data); // Create a new ArrayList with cloned data
            return clonedObject;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
