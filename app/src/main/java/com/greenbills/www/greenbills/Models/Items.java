package com.greenbills.www.greenbills.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Rahul Kumar on 6/1/2016.
 */
@Table(name = "Items")
public class Items extends Model {

    @Expose
    @Column(name = "item_id")
    public int item_id;

    @Expose
    @Column(name = "item_name")
    public String item_name;

    @Expose
    @Column(name = "item_qty")
    public double item_qty;

    @Expose
    @Column(name = "item_cost")
    public double item_cost;

    @Expose
    @Column(name = "item_amount")
    public double item_amount;

    public Items(String item_name, double item_qty, double item_cost) {
        this.item_name = item_name;
        this.item_qty = item_qty;
        this.item_cost = item_cost;
        this.item_amount = item_qty * item_cost ;
    }

    public Items(int item_id, String item_name, double item_qty, double item_cost) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_qty = item_qty;
        this.item_cost = item_cost;
        this.item_amount = item_qty * item_cost ;
    }

    public double getItem_amount(double item_qty,double item_cost) {
        return item_amount ;
    }

    public String getItem_name() {
        return item_name;
    }

    public double getItem_qty() {
        return item_qty;
    }

    public double getItem_cost() {
        return item_cost;
    }

}
