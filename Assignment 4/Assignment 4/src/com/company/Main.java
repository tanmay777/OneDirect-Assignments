/*
ASSIGNMENT #4
Topics Covered: Java Multithreading.

Develop a multi-threaded java program where
-> One thread reads the data from the database say, details of an Item from an mysql table. This thread builds an in-memory
object, stores it in a collection.

-> Simultaneously another thread should fetch already created Item objects from this collection and calculate the tax as per
rules detailed in assignment#1 update the tax value in appropriate Item attribute and store it in a different collection.

-> Finally print out the item details to console as detailed in assignment #1.

Implement such that the performance is optimal and thread race/dead lock is avoided.

Real Life Scenario:
Producer consumer mechanism.

Key Points:
Please make sure your database is setup and you are able to access it before starting with implementation of this assignment.
Use Java’s multithreading support for implementation.
Proper validation / info messages should be thrown on console where ever required.
Do appropriate exception handling where ever required.
Where ever required please write comments in the code to make it more understandable.
TDD methodology should be used
 */


package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class Item {
    int mId;
    String mName, mType;
    int mPrice, mQuantity;
}

class PriceOfItem{
    int mId;
    float mPricePerItem;
    //mPrice is the total price of mQuantity
    //mFinalPrice is the price of single item.
    double mSalesTaxLiability, mFinalPrice;
}

public class Main {


    public static void main(String[] args) {
        List<Item> items = new ArrayList<Item>();
        List<PriceOfItem> priceOfItems = new ArrayList<PriceOfItem>();

        Thread readDataThread = new Thread() {
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/goods", "root", "");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from Item");
                    while (resultSet.next()) {
                        Item item = new Item();

                        item.mId=resultSet.getInt(1);
                        item.mName = resultSet.getString(2);
                        item.mType = resultSet.getString(3);
                        item.mPrice = resultSet.getInt(4);
                        item.mQuantity = resultSet.getInt( 5);
                        items.add(item);
                    }
                    connection.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        Thread updateTaxDataThread = new Thread() {
            public void run() {
                try {
                    PriceOfItem priceOfItem = new PriceOfItem();
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/goods", "root", "");
                    Statement statement=connection.createStatement();
                    for (int i = 0; i < items.size(); i++) {
                        switch (items.get(i).mType) {
                            case "raw": {
                                priceOfItem.mId = items.get(i).mId;
                                priceOfItem.mPricePerItem = (float) items.get(i).mPrice / items.get(i).mQuantity;
                                priceOfItem.mSalesTaxLiability = priceOfItem.mPricePerItem * 0.125;
                                priceOfItem.mFinalPrice = priceOfItem.mPricePerItem + priceOfItem.mSalesTaxLiability;
                                statement.executeUpdate("insert into PriceOfItem values("+items.get(i).mId +", "+String.format(java.util.Locale.US,"%.2f", priceOfItem.mPricePerItem) +" , " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mSalesTaxLiability )+ " , " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mFinalPrice )+")");
                                break;
                            }
                            case "manufactured": {
                                priceOfItem.mId = items.get(i).mId;
                                priceOfItem.mPricePerItem = (float) items.get(i).mPrice / items.get(i).mQuantity;
                                priceOfItem.mSalesTaxLiability = priceOfItem.mPricePerItem * 0.125 + (priceOfItem.mPricePerItem + priceOfItem.mPricePerItem * 0.125) * 0.02;
                                priceOfItem.mFinalPrice = priceOfItem.mPricePerItem + priceOfItem.mSalesTaxLiability;
                                statement.executeUpdate("insert into PriceOfItem values("+items.get(i).mId +", "+String.format(java.util.Locale.US,"%.2f", priceOfItem.mPricePerItem) +" , " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mSalesTaxLiability )+ " , " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mFinalPrice )+")");
                                break;
                            }
                            case "imported": {
                                priceOfItem.mId = items.get(i).mId;
                                priceOfItem.mPricePerItem = (float) items.get(i).mPrice / items.get(i).mQuantity;
                                double import_duty = priceOfItem.mPricePerItem * 0.01;
                                double surcharge;
                                if (import_duty + priceOfItem.mPricePerItem <= 100) surcharge = 5;
                                else if (import_duty + priceOfItem.mPricePerItem > 100 && import_duty + priceOfItem.mPricePerItem <= 200)
                                    surcharge = 10;
                                else
                                    surcharge = (import_duty + priceOfItem.mPricePerItem) * 0.05;
                                priceOfItem.mSalesTaxLiability = import_duty + surcharge;
                                priceOfItem.mFinalPrice = priceOfItem.mPricePerItem + priceOfItem.mSalesTaxLiability;
                                statement.executeUpdate("insert into PriceOfItem values("+items.get(i).mId +", "+String.format(java.util.Locale.US,"%.2f", priceOfItem.mPricePerItem) +" , " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mSalesTaxLiability )+ " , " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mFinalPrice )+")");
                                break;
                            }
                        }

                        priceOfItems.add(priceOfItem); //This will not be used. But its for the sake convention.

                        System.out.println("Name: " + items.get(i).mName);
                        System.out.println("Total Price: " + items.get(i).mPrice);
                        System.out.println("Quantity: " + items.get(i).mQuantity);
                        System.out.println("Price per Item: " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mPricePerItem));
                        System.out.println("Sales Tax Liability: " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mSalesTaxLiability));
                        System.out.println("Final Price: " + String.format(java.util.Locale.US,"%.2f", priceOfItem.mFinalPrice));
                        System.out.println();
                    }

                    connection.close();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };

        try {
            readDataThread.start();
            readDataThread.join();
            updateTaxDataThread.start();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
