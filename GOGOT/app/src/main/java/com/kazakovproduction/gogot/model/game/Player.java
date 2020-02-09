package com.kazakovproduction.gogot.model.game;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private int place;
    private int points;
    private int pictureId;

    public Player(int place, int points, int pictureId) {
        this.place = place;
        this.points = points;
        this.pictureId = pictureId;
    }

    public int getPlace() {
        return place;
    }

    public int getPoints() {
        return points;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public Player(int place, int points) {
        this.place = place;
        this.points = points;
    }

    public Player(Parcel in) {
        place = in.readInt();
        points = in.readInt();
        pictureId = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(place);
        dest.writeInt(points);
        dest.writeInt(pictureId);
    }
}
