package com.example.shitij.railway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FromStation implements Parcelable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    protected FromStation(Parcel in) {
        name = in.readString();
        code = in.readString();
    }

    public static final Creator<FromStation> CREATOR = new Creator<FromStation>() {
        @Override
        public FromStation createFromParcel(Parcel in) {
            return new FromStation(in);
        }

        @Override
        public FromStation[] newArray(int size) {
            return new FromStation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(code);
    }
}
