package com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloseSmartHomePayload extends Payload {
    //电器名字
    private String deviceName = "";

    public CloseSmartHomePayload(@JsonProperty("device")String deviceName) {
        this.deviceName = deviceName;

    }

    public CloseSmartHomePayload(Parcel in)
    {
        super(in);
        deviceName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(deviceName);
    }

    public static final Creator<CloseSmartHomePayload> CREATOR = new Creator<CloseSmartHomePayload>() {
        @Override
        public CloseSmartHomePayload createFromParcel(Parcel in) {
            return new CloseSmartHomePayload(in);
        }

        @Override
        public CloseSmartHomePayload[] newArray(int size) {
            return new CloseSmartHomePayload[size];
        }
    };

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getDeviceName() {

        return deviceName;
    }

}
