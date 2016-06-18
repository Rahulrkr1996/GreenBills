package com.greenbills.www.greenbills.Models;

import android.content.ClipData;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Rahul Kumar on 6/1/2016.
 */
@Table(name = "Bill")
public class Bill extends Model{

    //@Column(name = "person_id")
    //public int person_id;

    @Column(name = "name")
    public String name;

    @Column(name = "address")
    public String address;

    @Column(name = "email")
    public String email;

    @Column(name = "contact")
    public String contact;

    @Column(name = "coupon_code")
    public String coupon_code;

    @Column(name="offer_code")
    public String offer_code;

    @Column(name = "date")
    public String date;

    @Column(name = "payment_method")
    public String payment_method;

    @Column(name = "total_amount")
    public String total_amount;

    @Column(name = "card_no")
    public String card_no;

    @Column(name="bank")
    public String bank;

    @Column(name="items")
    public List<Items> items;

    /*public List<Items> items() {
        return getMany(Items.class, "bill");
    }*/

    public Bill(String name,String address, String email, String contact, String coupon_code, String offer_code,
    String date, String payment_method,String total_amount, String card_no, String bank, List<Items> items){
        this.name=name;
        this.address=address;
        this.email=email;
        this.contact=contact;
        this.coupon_code=coupon_code;
        this.offer_code=offer_code;
        this.date=date;
        this.payment_method=payment_method;
        this.total_amount=total_amount;
        this.card_no=card_no;
        this.bank=bank;
        this.items=items;
    }
}
