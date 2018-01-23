
package com.joaop.mvpdemonstration.network.MoreInfoApi.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Rating {

    @SerializedName("Source")
    private String mSource;
    @SerializedName("Value")
    private String mValue;

    public String getSource() {
        return mSource;
    }

    public void setSource(String Source) {
        mSource = Source;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String Value) {
        mValue = Value;
    }

}
