package com.convertools.service;

public interface IMdbService {

    public static final int INPUT_TYPE = 0 ;
    public static final int OUTPUT_TYPE = 1 ;

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
