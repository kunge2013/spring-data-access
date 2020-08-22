package com.convertools.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor
@Setter
@Getter
public class BendResult {

    /*id*/
    private int id;

    /*短文本*/
    private String bendResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBendResult() {
        return bendResult;
    }

    public void setBendResult(String bendResult) {
        this.bendResult = bendResult;
    }
}
