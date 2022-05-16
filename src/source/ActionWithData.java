package source;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActionWithData {
    public static ArrayList<?> find(int choose, String whatToFind){
        if(choose == 1)
            return findGroup(whatToFind);
        if(choose == 2)
            return findProduct(whatToFind);
        return null;
    }
    public static ArrayList<Group> findGroup(String whatToFind){
        ArrayList<Group> result = new ArrayList<>();
        for(Group group: Group.getGroups()){
            if(group.getGroupName().toLowerCase().contains(whatToFind.toLowerCase())){
                result.add(group);
                continue;

            }
            if(group.getGroupDescription().toLowerCase().contains(whatToFind.toLowerCase()))
                result.add(group);
        }
        if(result.isEmpty())
            throw new RuntimeException("Matches not found");
        return result;
    }
    public static ArrayList<Product> findProduct(String whatToFind){
        ArrayList<Product> result = new ArrayList<>();
        for(Product product: Product.getProducts()) {
            if (product.getGroupNameInProduct().toLowerCase().contains(whatToFind.toLowerCase())) {
                result.add(product);
                continue;
            }
            if (product.getProductName().toLowerCase().contains(whatToFind.toLowerCase())){
                result.add(product);
                continue;
            }
            if (product.getProductDescription().toLowerCase().contains(whatToFind.toLowerCase())){
                result.add(product);
            }
        }
        if(result.isEmpty())
            throw new RuntimeException("Matches not found");
        return result;
    }

    public static String getAllInfo() {
        StringBuilder res = new StringBuilder();
        res.append("The amount of products groups: ").append(Group.getGroups().size()).append("\n");
        res.append("The amount of products: ").append(Product.getProducts().size()).append("\n");
        res.append("The overall price: ").append(Product.valueOfProductsOnStock()).append("\n\n\n");
        int counter = 1;
        for(Group group: Group.getGroups()) {

            res.append(counter).append(") ").append(group.getGroupName()).append(":").append("\n");
            res.append("    - Descriptions: ").append(group.getGroupDescription()).append("\n");
            res.append("    - The amount of products: ").append(Product.getProducts().stream().filter(p -> p.getGroupNameInProduct().equalsIgnoreCase(group.getGroupName())).mapToInt(Product::getProductQuantityOnStock).sum()).append("\n");
            res.append("    - The overall price: ").append(Product.getProducts().stream().filter(p -> p.getGroupNameInProduct().equalsIgnoreCase(group.getGroupName())).mapToDouble(p -> p.getProductPrice() * p.getProductQuantityOnStock()).sum()).append("\n");
            res.append("    - Products:").append("\n");
            int productCounter = 1;
            for(Product product: Product.getProducts().stream().filter(p -> p.getGroupNameInProduct().equalsIgnoreCase(group.getGroupName())).collect(Collectors.toSet())) {
                res.append("      ").append(productCounter).append(") ").append(product.getProductName()).append(":").append("\n");
                res.append("        - Name: ").append(product.getProductName()).append("\n");
                res.append("        - Descriptions: ").append(product.getProductDescription()).append("\n");
                res.append("        - Producer: ").append(product.getProducer()).append("\n");
                res.append("        - The amount: ").append(product.getProductQuantityOnStock()).append("\n");
                res.append("        - The price per one: ").append(product.getProductPrice()).append("\n");
                res.append("        - The overall price: ").append(product.totalProductCost()).append("\n");
                productCounter++;
                res.append("\n");
            }
            res.append("\n\n");
            counter++;
        }
        return res.toString();
    }
}
