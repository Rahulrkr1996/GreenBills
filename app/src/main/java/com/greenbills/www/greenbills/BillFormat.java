package com.greenbills.www.greenbills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.greenbills.www.greenbills.Models.Bill;
import com.greenbills.www.greenbills.Models.Items;

import org.w3c.dom.Text;

import java.util.List;

public class BillFormat extends AppCompatActivity implements View.OnClickListener{

    TextView BFDateInput,BFTimeInput, BFCouponInput, BFOfferInput, BFTotalInput,BFPaymentMethod,BFCardInput,BFBankInput;
    EditText BFNameInput, BFEmailInput, BFAddressInput, BFContactInput;
    ListView listView;
    List<Items> items;
    Button BFDoneButton;
    ArrayAdapter adapter = new ArrayAdapter<Items>(this,R.layout.bill_content,items);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_format);
        items=(List<Items>) getIntent().getSerializableExtra("items");
        BFDateInput=(TextView) findViewById(R.id.BF_dateInput);
        BFTimeInput=(TextView)findViewById(R.id.BF_timeInput);
        BFCouponInput=(TextView)findViewById(R.id.BF_CouponInput);
        BFOfferInput=(TextView)findViewById(R.id.BF_appliedCouponInput);
        BFTotalInput=(TextView)findViewById(R.id.BF_paymentTotalInput);
        BFPaymentMethod=(TextView)findViewById(R.id.BF_paymentMethodInfo);
        BFCardInput=(TextView)findViewById(R.id.BF_bank_cardNoInput);
        BFBankInput=(TextView)findViewById(R.id.BF_bank_nameInput);
        BFNameInput=(EditText)findViewById(R.id.BF_nameInput);
        BFEmailInput=(EditText)findViewById(R.id.BF_emailInput);
        BFAddressInput=(EditText)findViewById(R.id.BF_addressInput);
        BFContactInput=(EditText)findViewById(R.id.BF_contactInput);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        BFDoneButton=(Button)findViewById(R.id.BF_doneButton);
        BFDoneButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.BF_doneButton){
            Bill bill=new Bill(BFNameInput.getText().toString(),BFAddressInput.getText().toString(),BFEmailInput.getText().toString(),BFContactInput.getText().toString(),
                    BFCouponInput.getText().toString(),BFOfferInput.getText().toString(),BFDateInput.getText().toString()+" "+BFTimeInput.getText().toString(),BFPaymentMethod.getText().toString(),
                    BFTotalInput.getText().toString(),BFCardInput.getText().toString(),BFBankInput.getText().toString(),items);
            bill.save();
        }
    }
}
