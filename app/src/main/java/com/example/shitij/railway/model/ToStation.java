package com.example.shitij.railway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToStation implements Parcelable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    protected ToStation(Parcel in) {
        name = in.readString();
        code = in.readString();
    }

    public static final Creator<ToStation> CREATOR = new Creator<ToStation>() {
        @Override
        public ToStation createFromParcel(Parcel in) {
            return new ToStation(in);
        }

        @Override
        public ToStation[] newArray(int size) {
            return new ToStation[size];
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
