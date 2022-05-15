package source;

import java.util.ArrayList;

public class ActionWithData {
    public ArrayList<?> find(int choose, String whatToFind){
        if(choose == 1)
            return findGroup(whatToFind);
        if(choose == 2)
            return findProduct(whatToFind);
        return null;
    }
    public ArrayList<Group> findGroup(String whatToFind){
        ArrayList<Group> result = new ArrayList<>();
        for(Group group: Group.getGroups()){
            if(group.getGroupName().contains(whatToFind)){
                result.add(group);
                continue;
            }
            if(group.getGroupDescription().contains(whatToFind))
                result.add(group);
        }
        return result;
    }
    public ArrayList<Product> findProduct(String whatToFind){
        ArrayList<Product> result = new ArrayList<>();
        for(Product product: Product.getProducts()) {
            if (product.getGroupNameInProduct().contains(whatToFind)) {
                result.add(product);
                continue;
            }
            if (product.getProductName().contains(whatToFind)){
                result.add(product);
                continue;
            }
            if (product.getProductDescription().contains(whatToFind)){
                result.add(product);
                continue;
            }
        }
        return result;
    }
}