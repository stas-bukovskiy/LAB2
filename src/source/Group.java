package source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Існує декілька груп товарів (наприклад: Продовольчі, непродовольчі...).
 * Група товарів містить наступні властивості - назва, опис.*/
public class Group {


    private String groupName;
    private String groupDescription;

    private static ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<String> uniqueGroups = new ArrayList<>();

    private Group(String groupName,String groupDescription){
        this.groupName=groupName;
        this.groupDescription=groupDescription;
    }
    public static void addGroup(String groupName, String groupDescription){
        boolean contain = false;
        for(String unique: uniqueGroups){
            if(unique.equals(groupName)){
                contain = true;
                System.err.println("Try to add non unique group)");
                break;
            }
        }
        if(!contain){
            groups.add(new Group(groupName,groupDescription));
            uniqueGroups.add(groupName);
        }

    }
    public void editGroup(int index,String newGroupName, String newGroupDescription){
        products = Product.getProducts();
        String oldName = groups.get(index).getGroupName();
        for(int i = 0; i<products.size(); i++){
            if(products.get(i).getGroupNameInProduct().equals(oldName)){
                products.get(i).setGroupNameInProduct(newGroupName);
                uniqueGroups.set(i,newGroupName);
            }
        }
        Product.setProducts(products);
        groups.get(index).setGroupName(newGroupName);
        groups.get(index).setGroupDescription(newGroupDescription);
    }
    public void deleteGroup(String groupName){
        for(Group group: groups){
            if(group.getGroupName().equals(groupName)){
                groups.remove(group);
                remove(group.getGroupName());
            }
        }
    }
    /**Use when you remove group to remove all products in this group*/
    public void remove(String groupName){
        products = Product.getProducts();
        for(int i=0; i<products.size(); i++)
            if(groupName.equals(products.get(i).getGroupNameInProduct()))
                products.remove(i);
        Product.setProducts(products);
    }
    private static void sortByName(){
        Group current;
        Group previous;
        for(int i=0; i<groups.size(); i++){
            for(int j=1;j< groups.size() - i; j++){
                current = groups.get(j);
                previous = groups.get(j-1);
                int res = previous.getGroupName().toLowerCase().compareTo(current.getGroupName().toLowerCase());
                if(res > 0){
                    Group temp = previous;
                    groups.set(j-1,current);
                    groups.set(j,previous);
                }
            }
        }
    }
    public String toString(){
        return "\nGroup: "+getGroupName()+
                "\nInfo: "+getGroupDescription();
    }


    /**
     * block of getter and setter for fields
     * */

    public static ArrayList<Group> getGroups(){
        sortByName();
        return groups;
    }
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getGroupName() {return groupName;}
    public void setGroupName(String groupName) {this.groupName = groupName;}

    public String getGroupDescription() {return groupDescription;}
    public void setGroupDescription(String groupDescription) {this.groupDescription = groupDescription;}
}
