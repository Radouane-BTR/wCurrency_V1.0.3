package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Currency implements Parcelable, Comparator<Currency> {

    @SerializedName("currencyName")
    private String currencyName;

    @SerializedName("currencySymbol")
    private String currencySymbol;

    @SerializedName("id")
    private String id;

    private int img;

    private boolean isSelected;

    public Currency(String currencyName, String currencySymbol, String id, int img) {
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.id = id;
        this.img = img;
    }



    public Currency() {
    }

    protected Currency(Parcel in) {
        currencyName = in.readString();
        currencySymbol = in.readString();
        id = in.readString();
        img = in.readInt();
        isSelected = in.readByte() != 0;
    }


    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public int getImg() { return img; }

    public void setImg(int img) { this.img = img; }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  id+" - "+currencyName;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currencyName);
        dest.writeString(currencySymbol);
        dest.writeString(id);
        dest.writeInt(img);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }


    @Override
    public int compare(Currency c1, Currency c2) {
        return c1.getId().compareTo(c2.getId());
    }
}
