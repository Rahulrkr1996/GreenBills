package com.greenbills.www.greenbills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.greenbills.www.greenbills.Models.Items;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yogita on 14-06-2016.
 */
public class ItemInput extends AppCompatActivity implements View.OnClickListener {
    EditText itemName,itemCost,itemQty;
    Button insertItem,gotoBill;
    List<Items> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_input);
        itemName=(EditText)findViewById(R.id.II_item_name);
        itemCost=(EditText)findViewById(R.id.II_item_cost);
        itemQty=(EditText)findViewById(R.id.II_item_qty);
        insertItem=(Button)findViewById(R.id.II_insert_item);
        gotoBill=(Button)findViewById(R.id.II_goto_bill);
        insertItem.setOnClickListener(this);
        gotoBill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.II_insert_item){
            Items item=new Items(itemName.getText().toString(),Double.parseDouble(itemQty.getText().toString()),Double.parseDouble(itemCost.getText().toString()));
            item.save();
            items.add(item);
        }
        if(v.getId()==R.id.II_goto_bill){
            Class cls = null;
            try {
                cls = Class.forName("com.greenbills.www.greenbills.BillFormat");
                Intent i = new Intent(ItemInput.this,cls);
                i.putExtra("items", (Serializable) items);
                startActivity(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
