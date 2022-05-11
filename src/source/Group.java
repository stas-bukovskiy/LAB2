package source;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Існує декілька груп товарів (наприклад: Продовольчі, непродовольчі...).
 * Група товарів містить наступні властивості - назва, опис.*/
public class Group {


    private String groupName;
    private String groupDescription;

    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    private Group(String groupName,String groupDescription){
        this.groupName=groupName;
        this.groupDescription=groupDescription;
    }
    public void addGroup(String groupName,String groupDescription){
        groups.add(new Group(groupName,groupDescription));
    }
    public void editGroup(int index,String newGroupName, String newGroupDescription){
        groups.set(index,new Group(newGroupName,newGroupDescription));

    }
    public void deleteGroup(String groupName){
        for(Group group: groups){
            if(group.getGroupName().equals(groupName)){
                groups.remove(group);
//                Product.remo*ve(group);
            }
        }
    }
    public ArrayList<Group> getGroups(){
        return groups;
    }
    private void sortByName(){
        Group current;
        Group previous;
        for(int i=0; i<groups.size(); i++){
            for(int j=1;j< groups.size() - i; j++){
                current = groups.get(j);
                previous = groups.get(j-1);
                int res = previous.getGroupName().compareTo(current.getGroupName());
                if(res > 0){
                    Group temp = previous;
                    groups.set(j-1,current);
                    groups.set(j,previous);
                }
            }
        }
    }


    /**
     * block of getter and setter for fields
     * */
    public String getGroupName() {return groupName;}
    public void setGroupName(String groupName) {this.groupName = groupName;}

    public String getGroupDescription() {return groupDescription;}
    public void setGroupDescription(String groupDescription) {this.groupDescription = groupDescription;}
}
