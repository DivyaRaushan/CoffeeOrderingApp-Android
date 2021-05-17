 package com.example.quantityorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrders(View view)
    {
        EditText text = (EditText) findViewById( R.id.name_edit_text);
        String name = text.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCheckBox = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolateCheckBox);
        // Display the order summary on the screen
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolateCheckBox, name);
        Intent intent = new Intent( Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        displayPrice(priceMessage);

    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolateCheckBox is whether or not the user wants chocolate topping
     * @return total price
     */
    public int calculatePrice(boolean hasWhippedCream, boolean hasChocolateCheckBox)
    {
        //price of 1 cup of coffee
        int basePrice = 5;

        //Add Rs2 if the user wants whipped cream
        if(hasWhippedCream)
        {
            basePrice = basePrice + 2 ;
        }

        //Add Rs3 if the user wants chocolate cream
        if (hasChocolateCheckBox)
        {
            basePrice = basePrice + 3  ;
        }

        //it calculates the total price by multiplying updated base price & quantity
        return basePrice*quantity;
    }

    /**
     * Create summary of the order.
     *
     * @param name is the name of the customer or user
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolateCheckBox is whether or not the user wants chocolate topping
     * @param totalPrice of the order
     * @return text summary
     */
    public String createOrderSummary( int totalPrice, boolean hasWhippedCream, boolean hasChocolateCheckBox, String name )
    {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate cream? " + hasChocolateCheckBox;
        priceMessage += "\nTotal: Rs" + totalPrice;
        priceMessage = priceMessage + "\nThank you !!";
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_LONG).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_LONG).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    /**
     * This method displays the given text on the screen.
     */
    public void displayPrice(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("" + message);
    }


}