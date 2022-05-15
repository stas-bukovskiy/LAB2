package source;

import java.util.ArrayList;

/**
 * Існує декілька груп товарів (наприклад: Продовольчі, непродовольчі...).
 * Група товарів містить наступні властивості - назва, опис.*/
public class Group {


    private String groupName;
    private String groupDescription;

    private static ArrayList<Group> groups = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();

    private Group(String groupName,String groupDescription){
        this.groupName=groupName;
        this.groupDescription=groupDescription;
    }
    public static void addGroup(String groupName, String groupDescription){
        boolean unique = true;
        for(Group group: getGroups()){
            if(group.getGroupName().equals(groupName)){
                unique = false;
                throw new RuntimeException("Try to add non unique group)");
            }
        }
        if(unique)
            groups.add(new Group(groupName,groupDescription));
    }
    public static void editGroup(int index, String newGroupName, String newGroupDescription){
        boolean unique = true;
        for(Group group: getGroups()){
            if(group.getGroupName().equals(newGroupName)){
                unique = false;
                throw new RuntimeException("Try to change group name for non unique");
            }
        }
        if(unique){
            products = Product.getProducts();
            String oldName = groups.get(index).getGroupName();
            for (Product product : products) {
                if (product.getGroupNameInProduct().equals(oldName)) {
                    product.setGroupNameInProduct(newGroupName);
                }
            }
            Product.setProducts(products);
            groups.get(index).setGroupName(newGroupName);
        }
        groups.get(index).setGroupDescription(newGroupDescription);
    }
    public static void deleteGroup(String groupName){
        groups.removeIf(group -> group.getGroupName().equalsIgnoreCase(groupName));
    }
    /**Use when you remove group to remove all products in this group*/
    public static void remove(String groupName){
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
