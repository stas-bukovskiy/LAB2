package source;

import java.io.*;
import java.util.Scanner;

public class DataIO {
    private final static File groupList = new File("src/resources/groupList.txt");
    private final static File productList = new File("src/resources/productList.txt");

    /**
     * Method that read groups and info from file(use when program begin)
     * */
    public static void readInfoFromFile(){
        readGroups();
        readProducts();
    }
    public static void readGroups(){
        try{
            Scanner scanner = new Scanner(groupList);
            while(scanner.hasNext()){
                String[] groupInfo = scanner.nextLine().split("[#%]");
                Group.addGroup(groupInfo[1],groupInfo[2]);
            }
        } catch(IOException e){
            throw new RuntimeException("IO failed");
        }
    }
    public static void readProducts(){
        try{
            Scanner scanner = new Scanner(productList);
            while(scanner.hasNext()){
                String[] productInfo = scanner.nextLine().split("[#%]");
                try{
                    Product.addProduct(productInfo[1], productInfo[2], productInfo[3], productInfo[4],
                            Integer.parseInt(productInfo[5]), Double.parseDouble(productInfo[6]));
                } catch(IllegalArgumentException e){
                        throw new RuntimeException("Wrong type when add product)");
                }
            }
        } catch(IOException e){
            throw new RuntimeException("IO failed");
        }
    }

    /**
     * Use at the end of program to write info to file
     * */
    public static void writeInfoToFile(){
        writeGroups();
        writeProducts();
    }
    public static void writeGroups(){
        try{
            StringBuilder result;
            BufferedWriter bw = new BufferedWriter(new FileWriter(groupList));
            for(Group group : Group.getGroups()){
                result = new StringBuilder("#");
                result.append(group.getGroupName());
                result.append("%");
                result.append(group.getGroupDescription());
                result.append("\n");
                bw.write(String.valueOf(result));
            }
            bw.close();
        }catch(IOException e){
            throw new RuntimeException("File writing failed");
        }
    }
    public static void writeProducts(){
        try{
            StringBuilder result;
            BufferedWriter bw = new BufferedWriter(new FileWriter(productList));
            for(Product product: Product.getProducts()){
                result = new StringBuilder("#");
                result.append(product.getGroupNameInProduct());
                result.append("%");
                result.append(product.getProductName());
                result.append("%");
                result.append(product.getProductDescription());
                result.append("%");
                result.append(product.getProducer());
                result.append("%");
                result.append(product.getProductQuantityOnStock());
                result.append("%");
                result.append(product.getProductPrice());
                result.append("\n");
                bw.write(String.valueOf(result));
            }
            bw.close();
        }catch(IOException e){
            throw new RuntimeException("File writing failed");
        }
    }
}
