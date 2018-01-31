package com.su.nuttawut.coffeepuppy.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pokemon on 1/30/2018.
 */

public class CartDetail extends RealmObject{

    @PrimaryKey
    private int order_id;


    private int food_id;
    private String food_name;
    private double food_price;
    private int food_count;
    private String food_ingradiant1;
    private String food_ingradiant2;
    private String food_ingradiant3;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public double getFood_price() {
        return food_price;
    }

    public void setFood_price(double food_price) {
        this.food_price = food_price;
    }

    public int getFood_count() {
        return food_count;
    }

    public void setFood_count(int food_count) {
        this.food_count = food_count;
    }

    public String getFood_ingradiant1() {
        return food_ingradiant1;
    }

    public void setFood_ingradiant1(String food_ingradiant1) {
        this.food_ingradiant1 = food_ingradiant1;
    }

    public String getFood_ingradiant2() {
        return food_ingradiant2;
    }

    public void setFood_ingradiant2(String food_ingradiant2) {
        this.food_ingradiant2 = food_ingradiant2;
    }

    public String getFood_ingradiant3() {
        return food_ingradiant3;
    }

    public void setFood_ingradiant3(String food_ingradiant3) {
        this.food_ingradiant3 = food_ingradiant3;
    }
}
