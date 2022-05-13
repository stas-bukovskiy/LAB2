package source;

import java.util.ArrayList;

public class ActionWithData {
    public ArrayList<String> find(int choose, String whatToFind){
        if(choose == 1)
            return findGroup(whatToFind);
        if(choose == 2)
            return findProduct(whatToFind);
        return null;
    }
    public ArrayList<String> findGroup(String whatToFind){
        ArrayList<String> result = new ArrayList<>();
        for(Group group: Group.getGroups()){
            if(group.getGroupName().contains(whatToFind)){
                result.add(group.toString());
                continue;
            }
            if(group.getGroupDescription().contains(whatToFind))
                result.add(group.toString());
        }
        return result;
    }
    public ArrayList<String> findProduct(String whatToFind){
        ArrayList<String> result = new ArrayList<>();
        for(Product product: Product.getProducts()) {
            if (product.getGroupNameInProduct().contains(whatToFind)) {
                result.add(product.toString());
                continue;
            }
            if (product.getProductName().contains(whatToFind)){
                result.add(product.toString());
                continue;
            }
            if (product.getProductDescription().contains(whatToFind)){
                result.add(product.toString());
                continue;
            }
            try{
                Double.parseDouble(whatToFind));

            } catch(NumberFormatException e){

            }
        }
        return result;
    }
}
