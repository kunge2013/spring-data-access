package com.convertools.service;

public interface IMdbService {

    public final int INPUT_TYPE = 0;
    public final int OUTPUT_TYPE = 1;

    default public void execute(int type){
        if (type == INPUT_TYPE) {
            inputData();
        }
        if (type == OUTPUT_TYPE) {
            outData();
        }
    }

    public void inputData();

    public void outData();

}
