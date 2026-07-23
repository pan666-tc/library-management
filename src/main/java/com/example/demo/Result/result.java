package com.example.demo.Result;

public class result<T>{
    private int code;
    private String message;
    private T data;

    private  result(int code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    //成功（无数据）
    public static <T> result<T> success(){
        return new result<>(200,"success",null);
    }

    //成功（有数据）
    public static <T> result<T> success(T data){
        return new result<>(200,"success",data);
    }

    //成功（自定义消息）
    public static <T> result<T> success(String message,T data){
        return new result<>(200,message,data);
    }

    //失败
    public static <T> result<T> error(String message){
        return new result<>(500,message,null);
    }

    //失败（自定义状态码）
    public static <T> result<T> error(int code,String message){
        return new result<>(code,message,null);
    }

    public int getcode(){
        return code;
    }
    public String getmessage(){
        return message;
    }
    public T getdata(){
        return data;
    }
}
