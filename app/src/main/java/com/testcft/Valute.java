package com.testcft;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Valute implements Parcelable {

    @SerializedName("ID")
    private String id;
    @SerializedName("NumCode")
    private String numCode;
    @SerializedName("CharCode")
    private String charCode;
    @SerializedName("Nominal")
    private Integer nominal;
    @SerializedName("Name")
    private String name;
    @SerializedName("Value")
    private Double value;
    @SerializedName("Previous")
    private Double previous;

    public Valute(String id, String numCode, String charCode, Integer nominal, String name, Double value, Double previous) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previous = previous;
    }

    protected Valute(Parcel in) {
        id = in.readString();
        numCode = in.readString();
        charCode = in.readString();
        if (in.readByte() == 0) {
            nominal = null;
        } else {
            nominal = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readDouble();
        }
        if (in.readByte() == 0) {
            previous = null;
        } else {
            previous = in.readDouble();
        }
    }

    public static final Creator<Valute> CREATOR = new Creator<Valute>() {
        @Override
        public Valute createFromParcel(Parcel in) {
            return new Valute(in);
        }

        @Override
        public Valute[] newArray(int size) {
            return new Valute[size];
        }
    };

    @Override
    public String toString() {
        return "Valute{" +
                "id='" + id + '\'' +
                ", numCode='" + numCode + '\'' +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", previous=" + previous +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPrevious() {
        return previous;
    }

    public void setPrevious(Double previous) {
        this.previous = previous;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(numCode);
        dest.writeString(charCode);
        if (nominal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(nominal);
        }
        dest.writeString(name);
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(value);
        }
        if (previous == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(previous);
        }
    }
}
