package com.example.shitij.railway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainDetails implements Parcelable {

    private int no;
    private String number;
    private String name;
    private String src_departure_time;
    private String dest_arrival_time;
    private FromStation from;
    private ToStation to;
    private Days[] days;

    protected TrainDetails(Parcel in) {
        no = in.readInt();
        number = in.readString();
        name = in.readString();
        src_departure_time = in.readString();
        dest_arrival_time = in.readString();
        from = in.readParcelable(FromStation.class.getClassLoader());
        to = in.readParcelable(ToStation.class.getClassLoader());
        days = in.createTypedArray(Days.CREATOR);
    }

    public static final Creator<TrainDetails> CREATOR = new Creator<TrainDetails>() {
        @Override
        public TrainDetails createFromParcel(Parcel in) {
            return new TrainDetails(in);
        }

        @Override
        public TrainDetails[] newArray(int size) {
            return new TrainDetails[size];
        }
    };

    public int getNo() {
        return no;
    }

    public Days[] getDays() {
        return days;
    }

    public FromStation getFrom() {
        return from;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDest_arrival_time() {
        return dest_arrival_time;
    }

    public String getSrc_departure_time() {
        return src_departure_time;
    }

    public ToStation getTo() {
        return to;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeString(number);
        dest.writeString(name);
        dest.writeString(dest_arrival_time);
        dest.writeParcelable(from, flags);
        dest.writeParcelable(to, flags);
    }
}
