package com.example.androiddevelopmentmidtermproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Note implements Parcelable, Serializable {
    private String title;
    private String body;

    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(body);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Note(Parcel in) {
        title = in.readString();
        body = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString(){
        return getTitle();
    }

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Note> CREATOR
            = new Parcelable.Creator<Note>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

}

