package com.su.nuttawut.coffeepuppy.Data;

/**
 * Created by chainrongkst on 18/1/2018 AD.
 */

public class Product {
    private int Product_ID;
    private String StatusProduct;
    private String Category_ID;
    private String ProductName;
    private double Price;
    private String Picture;
    private String Discription;
    private int amountProduct;

    public Product(int product_ID, String statusProduct, String category_ID, String productName, double price, String picture, String discription) {
        this.Product_ID = product_ID;
        this.StatusProduct = statusProduct;
        this.Category_ID = category_ID;
        this.ProductName = productName;
        this.Price = price;
        this.Picture = picture;
        this.Discription = discription;
    }

    public Product(String productName, double price, int amountProduct) {
        ProductName = productName;
        Price = price;
        this.amountProduct = amountProduct;
    }

    public Product(String productName, double price, String picture) {
        this.ProductName = productName;
        this.Price = price;
        this.Picture = picture;
    }

    public int getAmountProduct() { return amountProduct; }

    public int getProduct_ID() {
        return Product_ID;
    }

    public String getStatusProduct() {
        return StatusProduct;
    }

    public String getCategory_ID() {
        return Category_ID;
    }

    public String getProductName() {
        return ProductName;
    }

    public double getPrice() {
        return Price;
    }

    public String getPicture() {
        return Picture;
    }

    public String getDiscription() {
        return Discription;
    }
}
