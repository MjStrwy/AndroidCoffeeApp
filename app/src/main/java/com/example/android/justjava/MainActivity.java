package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity{

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameText = (EditText) findViewById(R.id.edt_Name);
        String name = nameText.getText().toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.chx_WhippedCream);
        boolean cream = whippedCream.isChecked();

        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chx_Chocolate);
        boolean  chocolate = hasChocolate.isChecked();

        int price  = calculatePrice(cream, chocolate);
        displayMessage(createOrderSummary(price, cream, chocolate, name));
        sendReceipt(name, createOrderSummary(price,cream,chocolate,name));
    }

    /**
     * This method takes the order message and emails it as a receipt to the user
     *
     * @param message
     */
    private void sendReceipt(String name, String message){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method take the price of an order and returns an order summary string
     *
     * @param price of order
     * @param cream ordered
     * @param chocolate is whether they want chocolate topping
     * @param name of the person ordering
     * @return orderSummary
     */
    private String createOrderSummary(int price, boolean cream, boolean chocolate, String name){
        String orderSummary = "Name: "+name+"\n" +
                "Add whipped cream? " + cream +"\n"+
                "Add chocolate? " + chocolate + "\n" +
                "Quantity: "+
                quantity +
                "\nTotal: $" + price +
                "\n" + getString(R.string.thankyou) + "!";
        return orderSummary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method calculates the price of the order based on the current quantity
     *
     * @return price
     */
    private int calculatePrice(boolean hasWhipped, boolean hasChocolate){
        return quantity * (5 + (hasWhipped ? 1 : 0) + (hasChocolate ? 2 : 0));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the number one in the quantity text field
     * @param view
     */
    public void decrement(View view){
        if(quantity == 1) {
            Toast t = Toast.makeText(this, "You cannot order 0 coffees", Toast.LENGTH_LONG);
            t.show();
            return;
        }//could be error if ever set not to a positive number

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the number three in the quantity text field
     * @param view
     */
    public void increment(View view) {
        if(quantity == 100){
            Toast t = Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_LONG);
            t.show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }
}
