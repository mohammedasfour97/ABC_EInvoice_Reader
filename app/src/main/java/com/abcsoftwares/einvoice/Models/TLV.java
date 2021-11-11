package com.abcsoftwares.einvoice.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TLV {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("TagEn")
    @Expose
    private String tagEn;
    @SerializedName("TagAr")
    @Expose
    private String tagAr;
    @SerializedName("DataValue")
    @Expose
    private String dataValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagEn() {
        return tagEn;
    }

    public void setTagEn(String tagEn) {
        this.tagEn = tagEn;
    }

    public String getTagAr() {
        return tagAr;
    }

    public void setTagAr(String tagAr) {
        this.tagAr = tagAr;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

}
