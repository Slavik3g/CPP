package com.example.shift.entity;

import org.springframework.data.annotation.Id;

import java.util.Objects;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;

public class ShiftData {

    public String data;

    @Id
    private long id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftData shiftData = (ShiftData) o;
        return Objects.equals(data, shiftData.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    public ShiftData(long id, String data) {
        this.data = data;
        this.id = id;
    }

    public ShiftData() {
        data = NULL;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
