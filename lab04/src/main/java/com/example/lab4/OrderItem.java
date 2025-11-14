package com.example.lab4;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
    private String name;
    private int price;
    private int quantity;

    public OrderItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    protected OrderItem(Parcel in) {
        name = in.readString();
        price = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(quantity);
    }
}
