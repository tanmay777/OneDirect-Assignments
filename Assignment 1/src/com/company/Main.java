package com.company;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Item{
    String mName,mType;
    int mPrice,mQuantity;
    float mPricePerItem;
    //mPrice is the total price of mQuantity
    //mFinalPrice is the price of single item.
    double mSalesTaxLiability,mFinalPrice;
}

public class Main{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        List<Item> items=new ArrayList<Item>();

        Item item=new Item();
        item.mName=args[0];
        for(int i=1;i<args.length-1;i++)
        {
            if(args[i].equals("raw")||args[i].equals("manufactured")||args[i].equals("imported"))
                item.mType=args[i];
            else
                item.mPrice=Integer.parseInt(args[i]);
        }

        item.mQuantity=Integer.parseInt(args[args.length-1]);
        item.mPricePerItem=(float)item.mPrice/item.mQuantity;
        items.add(item);

        char response;

        while(true) {
            System.out.println("Do you want to enter details of any other item (y/n)");
            response = sc.nextLine().charAt(0);
            if(response != 'y' && response!= 'n'){
                System.out.println("Valid input is only y and n");
                System.out.println();
            }
            else
                break;
        }

        while(response=='y'){
            System.out.println();
            System.out.println("Enter the next item details");
            Item newItem=new Item();
            newItem.mName=sc.nextLine();
            String input=sc.nextLine();
            if(input.equals("raw")||input.equals("manufactured")||input.equals("imported")) {
                newItem.mType=input;
                newItem.mPrice=Integer.parseInt(sc.nextLine());
            }
            else {
                newItem.mPrice=Integer.parseInt(input);
                newItem.mType=sc.nextLine();
            }
            newItem.mQuantity=Integer.parseInt(sc.nextLine());
            newItem.mPricePerItem=(float)newItem.mPrice/newItem.mQuantity;
            items.add(newItem);

            while(true) {
                System.out.println("Do you want to enter details of any other item (y/n)");
                response = sc.nextLine().charAt(0);
                if(response != 'y' && response!= 'n'){
                    System.out.println("Valid input is only y and n");
                    System.out.println();
                }
                else
                    break;
            }

            System.out.println();
        }

        for(int i=0;i<items.size();i++)
        {
            switch (items.get(i).mType) {
                case "raw":
                    items.get(i).mSalesTaxLiability=items.get(i).mPricePerItem*0.125;
                    items.get(i).mFinalPrice=items.get(i).mPricePerItem+items.get(i).mSalesTaxLiability;
                    break;
                case "manufactured":
                    items.get(i).mSalesTaxLiability=items.get(i).mPricePerItem*0.125+(items.get(i).mPricePerItem+items.get(i).mPricePerItem*0.125)*0.02;
                    items.get(i).mFinalPrice=items.get(i).mPricePerItem+items.get(i).mSalesTaxLiability;
                    break;
                case "imported":
                    double import_duty=items.get(i).mPricePerItem*0.01;
                    double surcharge;
                    if(import_duty + items.get(i).mPricePerItem <= 100) surcharge=5;
                    else if (import_duty +items.get(i).mPricePerItem > 100 && import_duty + items.get(i).mPricePerItem <= 200) surcharge=10;
                    else
                        surcharge=(import_duty+items.get(i).mPricePerItem)*0.05;
                    items.get(i).mSalesTaxLiability=import_duty+surcharge;
                    items.get(i).mFinalPrice=items.get(i).mPricePerItem+items.get(i).mSalesTaxLiability;
                    break;
            }

            System.out.println("Name: "+items.get(i).mName);
            System.out.println("Total Price: "+items.get(i).mPrice);
            System.out.println("Quantity: "+items.get(i).mQuantity);
            System.out.println("Price per Item: "+items.get(i).mPricePerItem);
            System.out.println("Sales Tax Liability: "+items.get(i).mSalesTaxLiability);
            System.out.println("Final Price: "+items.get(i).mFinalPrice);
            System.out.println();
        }
    }
}



