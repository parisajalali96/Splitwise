package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import java.util.ArrayList;

public class Group{
    String name;
    String type;
    ArrayList<User> members = new ArrayList<>();
    User creator;
    static int id = 0;
    public Group(String name, String type, User creator){
        this.name = name;
        this.type = type;
        this.creator = creator;
        members.add(creator);
        id++;
    }

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public String getType(){
        return type;
    }
    public User getCreator(){
        return creator;
    }
    public ArrayList<User> getMembers(){
        return members;
    }
}
