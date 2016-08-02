package com.example.shitij.railway.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shitij on 19/11/15.
 */
public class AllTrainDetails implements Parcelable {
    private int response_code;
    private int total;
    private TrainDetails[] train;

    protected AllTrainDetails(Parcel in) {
        response_code = in.readInt();
        total = in.readInt();
    }

    public static final Creator<AllTrainDetails> CREATOR = new Creator<AllTrainDetails>() {
        @Override
        public AllTrainDetails createFromParcel(Parcel in) {
            return new AllTrainDetails(in);
        }

        @Override
        public AllTrainDetails[] newArray(int size) {
            return new AllTrainDetails[size];
        }
    };

    public int getResponse_code() {
        return response_code;
    }

    public int getTotal() {
        return total;
    }

    public TrainDetails[] getTrain() {
        return train;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(response_code);
        dest.writeInt(total);
        dest.writeTypedArray(train, flags);
    }
}

class Days implements Parcelable{
    private String runs;
    private String day_code;

    protected Days(Parcel in) {
        runs = in.readString();
        day_code = in.readString();
    }

    public static final Creator<Days> CREATOR = new Creator<Days>() {
        @Override
        public Days createFromParcel(Parcel in) {
            return new Days(in);
        }

        @Override
        public Days[] newArray(int size) {
            return new Days[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(runs);
        dest.writeString(day_code);
    }

}
