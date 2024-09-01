package Messaging;

import java.io.Serializable;


public class Group implements Serializable  {
     String  Name;
     String groupMsg;
     String From;

    public Group(){
        this.Name = null;
    }

    public Group(String Name, String text,String From){
        this.Name= Name;
        this.groupMsg = text;
        this.From = From;
    }

    public Group(String Name){
        this.Name = Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public void setgroupMsg(String grpMsg){
        this.groupMsg = grpMsg;
    }

    public String getName(){
        return this.Name;
    }
    
    public String getgroupMsg(){
        return this.groupMsg;
    }
}
