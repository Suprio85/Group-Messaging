package Messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateGroup implements Serializable{
    public String Name;
    public List<String> members;


    public CreateGroup(String Name){
        this.Name = Name;
        this.members = new ArrayList<String>();
    }

    public CreateGroup(String Name, List<String> members){
        this.Name = Name;
        this.members = members;
    }

    public void setMembers(List<String> members){
        this.members = members;

    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof CreateGroup)) return false;
        var grp = (CreateGroup)o;
         return this.Name.equalsIgnoreCase(grp.Name);
    }

}
