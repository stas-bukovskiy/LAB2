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
            if(product.getProductName().equals(productName)){
                unique = false;
                throw new RuntimeException("Try to add non unique product");
            }
        }
        if(unique && productPrice>0 && productQuantityOnStock>=0)
            products.add(new Product(groupNameInProduct,productName,productDescription,producer,productQuantityOnStock,productPrice));
        else
            throw new RuntimeException("Try to add product with (lower than 0 price or negative quantity)");
    }
    public void editProduct( String groupNameInProduct, String productName, String productDescription, String producer, int productQuantityOnStock, double productPrice){
        boolean uniqueName = true;
        boolean groupExist = false;
        for(Product product: products){
            if(product.getProductName().equals(productName)){
                uniqueName = false;
                throw new RuntimeException("Try to change product name to non unique");
            }
        }
        if(uniqueName)
            setProductName(productName);
        for(Group group: Group.getGroups()){
            if(group.getGroupName().equals(groupNameInProduct)){
                groupExist = true;
                break;
            }
        }
        if(groupExist)
            setGroupNameInProduct(groupNameInProduct);
        else
            throw new RuntimeException("Try to change to non existing group");
        if(productQuantityOnStock>0)
            setProductQuantityOnStock(productQuantityOnStock);
        else
            throw new RuntimeException("Try to set incorrect quantity to product");
        setProducer(producer);
        setProductDescription(productDescription);
        if(productPrice>0)
            setProductPrice(productPrice);
        else
            throw new RuntimeException("Try to set incorrect price");

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
                "\nЦіна за штуку: "+productPrice+
                "\nЗагальна вартість: "+totalProductCost();
    }
    /**
     * Method to add product on stock(change quantity)
     * */
    public void addToStock(String productNameToAdd,int quantity){
        for(int i=0;i<getProducts().size();i++){
            if(productNameToAdd.equals(products.get(i).getProductName())){
                products.get(i).setProductQuantityOnStock(getProductQuantityOnStock()+quantity);
                break;
            }
        }
    }
    /**
     * Method to get product from stock(change quantity)
     * */
    public void writeOffFromStock(String productNameToWriteOff, int quantity){
        for(int i=0;i<getProducts().size();i++){
            if(productNameToWriteOff.equals(products.get(i).getProductName())){
                if(products.get(i).getProductQuantityOnStock() >= quantity)
                    products.get(i).setProductQuantityOnStock(getProductQuantityOnStock()+quantity);
                else
                    throw new RuntimeException("Try to write off "+quantity+" product(On stock:"+products.get(i).getProductQuantityOnStock()+")");
                break;
            }
        }
    }

    /**
     * For count total product cost(one specific product)
     * */
    public double totalProductCost(){
        return getProductQuantityOnStock()*getProductPrice();
    }

    /**
     * For count total product value on stock(All products value)
     * */
    public double valueOfProductsOnStock(){
        double total = 0.0;
        for(Product product: getProducts()){
            total += product.getProductPrice()*product.getProductQuantityOnStock();
        }
        return total;
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
