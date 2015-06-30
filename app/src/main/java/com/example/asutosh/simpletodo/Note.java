package com.example.asutosh.simpletodo;

public class Note {

    //private variables
    int _id;
    String _message;
    String _date;

    // Empty constructor
    public Note(){

    }
    // constructor
    public Note(int id, String message, String date){
        this._id = id;
        this._message = message;
        this._date = date;
        //setter
    }

    // constructor
    public Note(String message, String date){
        this._message = message;
        this._date = date;
        //getter
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting mesage
    public String getMessage(){
        return this._message;
    }

    // setting message
    public void setMessage(String message){
        this._message = message;
    }

    // getting date
    public String getDate(){
        return this._date;
    }

    // setting date
    public void setDate(String date){
        this._date = date;
    }
}
