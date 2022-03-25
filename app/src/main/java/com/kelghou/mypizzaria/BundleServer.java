package com.kelghou.mypizzaria;

public class BundleServer {
    public MainActivity context;
    public String message = "";

    public BundleServer(MainActivity m,String msg){
        this.context = m;
        this.message = msg;
    }
}
