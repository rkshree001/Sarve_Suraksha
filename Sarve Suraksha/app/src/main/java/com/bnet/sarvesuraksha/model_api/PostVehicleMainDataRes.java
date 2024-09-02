package com.bnet.sarvesuraksha.model_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostVehicleMainDataRes {
    @SerializedName("vehicleDetail")
    @Expose
    private PostVehicleDetail vehicleDetail;
    @SerializedName("vehicleBasicDetail")
    @Expose
    private PostVehicleBasicDetail1 vehicleBasicDetail;
    @SerializedName("vehicleType")
    @Expose
    private String vehicleType;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("insuranceDetail")
    @Expose
    private List<GetVehicleInsuranceDetail> insuranceDetail;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public PostVehicleDetail getVehicleDetail() {
        return vehicleDetail;
    }

    public void setVehicleDetail(PostVehicleDetail vehicleDetail) {
        this.vehicleDetail = vehicleDetail;
    }

    public PostVehicleBasicDetail1 getVehicleBasicDetail() {
        return vehicleBasicDetail;
    }

    public void setVehicleBasicDetail(PostVehicleBasicDetail1 vehicleBasicDetail) {
        this.vehicleBasicDetail = vehicleBasicDetail;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GetVehicleInsuranceDetail> getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(List<GetVehicleInsuranceDetail> insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
