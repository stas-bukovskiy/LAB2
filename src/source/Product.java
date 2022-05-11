package source;

import java.util.ArrayList;

/**
 *  В кожній групі товарів існують конкретні товари (наприклад: борошно, гречка ...).
 *  У кожного товару є наступні властивості - назва, опис, виробник, кількість на складі, ціна за одиницю.
 * */
public class Product {

    private String productName;
    private String productDescription;
    private String producer;
    private int productQuantityOnStock;
    private double productPrice;

    private ArrayList<Product> products = new ArrayList<>();

    private Product(String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice) {
        this.productName=productName;
        this.productDescription=productDescription;
        this.producer=producer;
        this.productQuantityOnStock=productQuantityOnStock;
        this.productPrice=productPrice;
    }
    public void addProduct(String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice){
        products.add(new Product(productName,productDescription,producer,productQuantityOnStock,productPrice));
    }
    public void editProduct(String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice){
        setProductName(productName);
        setProductDescription(productDescription);
        setProducer(producer);
        setProductQuantityOnStock(productQuantityOnStock);
        setProductPrice(productPrice);
    }
    public void delete(int index){
        products.remove(index);
    }

    public String toString(){
        return "#"+productName+
                "\nОпис: "+productDescription+
                "\nВиробник: "+producer+
                "\nНа складі: "+productQuantityOnStock+
                "\nЦіна за штуку: "+productPrice;
    }




    /**
     * getters and setter to fields
     * */
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
