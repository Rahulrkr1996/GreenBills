package com.greenbills.www.greenbills.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Rahul Kumar on 6/1/2016.
 */
@Table(name = "User")
public class Bill extends Model{
    @Column(name = "person_id")
    public String person_id;

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

    @Column(name = "offer_code")
    public String offer_code;

    @Column(name = "payment_method")
    public String payment_method;

    @Column(name = "total_amount")
    public String total_amount;

    @Column(name = "card_no")
    public String card_no;

    @Column(name="bank")
    public String bank;

    @Column(name="Items")
    public Items items;
}
