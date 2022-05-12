package source;

import java.util.ArrayList;

/**
 *  В кожній групі товарів існують конкретні товари (наприклад: борошно, гречка ...).
 *  У кожного товару є наступні властивості - назва, опис, виробник, кількість на складі, ціна за одиницю.
 * */
public class Product {

    private String groupNameInProduct;
    private String productName;
    private String productDescription;
    private String producer;
    private int productQuantityOnStock;
    private double productPrice;

    private static ArrayList<Product> products = new ArrayList<>();

    private Product(String groupNameInProduct, String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice) {
        this.groupNameInProduct=groupNameInProduct;
        this.productName=productName;
        this.productDescription=productDescription;
        this.producer=producer;
        this.productQuantityOnStock=productQuantityOnStock;
        this.productPrice=productPrice;
    }
    public static void addProduct(String groupNameInProduct, String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice){
        boolean unique = true;
        for(Product product:products){
            if(product.getProductName().equals(groupNameInProduct)){
                unique = false;
                System.err.println("Try to add non unique product");
                break;
            }
        }
        if(unique)
            products.add(new Product(groupNameInProduct,productName,productDescription,producer,productQuantityOnStock,productPrice));
    }
    public void editProduct( String groupNameInProduct, String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice){
        boolean unique = true;
        for(Product product: products){
            if(product.getProductName().equals(groupNameInProduct)){
                unique = false;
                System.err.println("Try to change product name to non unique");
                break;
            }
        }
        setGroupNameInProduct(groupNameInProduct);
        if(unique)
            setProductName(productName);
        setProductQuantityOnStock(productQuantityOnStock);
        setProducer(producer);
        setProductDescription(productDescription);
        setProductPrice(productPrice);
    }
    public void delete(int index){
        products.remove(index);
    }

    /**toString() in Product.class*/
    public String toString(){
        return "\n#"+groupNameInProduct+
                "\nНазва: "+productName+
                "\nОпис: "+productDescription+
                "\nВиробник: "+producer+
                "\nНа складі: "+productQuantityOnStock+
                "\nЦіна за штуку: "+productPrice;
    }
    private static void sortByName(){
        Product current;
        Product previous;
        for(int i=0; i<products.size(); i++){
            for(int j=1;j< products.size() - i; j++){
                current = products.get(j);
                previous = products.get(j-1);
                int res = previous.getProductName().toLowerCase().compareTo(current.getProductName().toLowerCase());
                if(res > 0){
                    Product temp = previous;
                    products.set(j-1,current);
                    products.set(j,previous);
                }
            }
        }
    }

    /**
     * getters and setter to fields
     * */
    public static ArrayList<Product> getProducts(){
        sortByName();
        return products;
    }
    public static void setProducts(ArrayList<Product> newProducts){products = newProducts;}

    public String getGroupNameInProduct() {return groupNameInProduct;}
    public void setGroupNameInProduct(String groupNameInProduct) {this.groupNameInProduct = groupNameInProduct;}

    public String getProductName() {return productName;}
    public void setProductName(String productName) {this.productName = productName;}

    public String getProductDescription() {return productDescription;}
    public void setProductDescription(String productDescription) {this.productDescription = productDescription;}

    public String getProducer() {return producer;}
    public void setProducer(String producer) {this.producer = producer;}

    public int getProductQuantityOnStock() {return productQuantityOnStock;}
    public void setProductQuantityOnStock(int productQuantityOnStock) {this.productQuantityOnStock = productQuantityOnStock;}

    public double getProductPrice() {return productPrice;}
    public void setProductPrice(double productPrice) {this.productPrice = productPrice;}

}
