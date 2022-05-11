package source;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataIO {
    private static File groupList = new File("src/resources/groupList.txt");
    private static File productList = new File("src/resources/productList.txt");

    /**
     * Method that read groups and info from file
     * */
    public static void readGroups(){
        try{
            Scanner scanner = new Scanner(groupList);
            while(scanner.hasNext()){
                String[] groupInfo = scanner.nextLine().split("[#%]");
                Group.addGroup(groupInfo[1],groupInfo[2]);
            }
        } catch(IOException e){
            System.err.println("IO failed");
            e.printStackTrace();
        }
    }
    public static void readProducts(){
        try{
            Scanner scanner = new Scanner(productList);
            while(scanner.hasNext()){
                String[] productInfo = scanner.nextLine().split("[#%]");
                try{
                    Product.addProduct(chooseGroup(Group.getGroups()), productInfo[1], productInfo[2], productInfo[3],
                            Integer.parseInt(productInfo[4]), Double.parseDouble(productInfo[5]));
                } catch(IllegalArgumentException e){
                    System.err.println("Wrong type when add product)");
                    e.printStackTrace();
                }
            }
        } catch(IOException e){
            System.err.println("IO failed");
        }
    }
    /**
     * !!!!!!!!!!!!!!!!!!!!
     * стасссссс сюда треба впихнути картінку, типу щоб чел вибрав з списку в яку групу добаавить товар
     * !!!!!!!!!!!!!!!!!!!!
     * */
    public static String chooseGroup(ArrayList<Group> groups){
        return groups.get(0).getGroupName();
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
            System.err.println("File writing failed");
            e.printStackTrace();
        }
    }
    public static void writeProducts(){
        try{
            StringBuilder result;
            BufferedWriter bw = new BufferedWriter(new FileWriter(productList));
            for(Product product: Product.getProducts()){
                result = new StringBuilder("#");
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
            System.err.println("File writing failed");
            e.printStackTrace();
        }
    }


    /**
     * !!!!!!!!!!!
     * Стааас тут main() нахуй не нужон , просто показую тобі як він работає
     * !!!!!!!!!!!
     * */
    public static void main(String[] args){
        readGroups();
        System.out.println(Group.getGroups());
        Group.addGroup("Додана фігня","Опис доданої фігні");
        System.out.println(Group.getGroups());

        readProducts();
        System.out.println(Product.getProducts());
        Product.addProduct(chooseGroup(Group.getGroups()),"Ряжанка","Смачна","Простоквашино",5,40);
        System.out.println(Product.getProducts());

        writeGroups();
        writeProducts();
    }
}
