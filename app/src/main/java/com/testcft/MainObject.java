package com.testcft;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MainObject implements Parcelable {


    private Long dateUpdate;
    private List<Valute> valuteList;

    public MainObject(Long dateUpdate, List<Valute> valuteList) {
        this.dateUpdate = dateUpdate;
        this.valuteList = valuteList;
    }

    protected MainObject(Parcel in) {
        if (in.readByte() == 0) {
            dateUpdate = null;
        } else {
            dateUpdate = in.readLong();
        }
        valuteList = in.createTypedArrayList(Valute.CREATOR);
    }

    public static final Creator<MainObject> CREATOR = new Creator<MainObject>() {
        @Override
        public MainObject createFromParcel(Parcel in) {
            return new MainObject(in);
        }

        @Override
        public MainObject[] newArray(int size) {
            return new MainObject[size];
        }
    };

    @Override
    public String toString() {
        return "MainObject{" +
                "dateUpdate=" + dateUpdate +
                ", valuteList=" + valuteList +
                '}';
    }

    public Long getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Long dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public List<Valute> getValuteList() {
        return valuteList;
    }

    public void setValuteList(List<Valute> valuteList) {
        this.valuteList = valuteList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dateUpdate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(dateUpdate);
        }
        dest.writeTypedList(valuteList);
    }
}
