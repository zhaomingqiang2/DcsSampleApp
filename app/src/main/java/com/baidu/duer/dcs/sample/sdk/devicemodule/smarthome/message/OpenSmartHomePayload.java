package com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenSmartHomePayload extends Payload {
    //电器名字
    private String deviceName = "";
    //房间
    private String roomName = "";

    public OpenSmartHomePayload(@JsonProperty("device")String deviceName,@JsonProperty("room")String roomName) {
        this.deviceName = deviceName;
        this.roomName = roomName;

    }

    public OpenSmartHomePayload(Parcel in)
    {
        super(in);
        deviceName = in.readString();
        roomName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(deviceName);
        parcel.writeString(roomName);
    }

    public static final Creator<OpenSmartHomePayload> CREATOR = new Creator<OpenSmartHomePayload>() {
        @Override
        public OpenSmartHomePayload createFromParcel(Parcel in) {
            return new OpenSmartHomePayload(in);
        }

        @Override
        public OpenSmartHomePayload[] newArray(int size) {
            return new OpenSmartHomePayload[size];
        }
    };

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getDeviceName() {

        return deviceName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {

        return roomName;
    }
}
