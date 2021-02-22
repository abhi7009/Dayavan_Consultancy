package com.abhishek.dayavanconsultancy;

public class Model {
    String id;
    String vehicle_no;

    public Model() {
    }

    public Model(String id,String vehicle_no2) {
        this.id = id;
        this.vehicle_no = vehicle_no2;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", vehicle_no='" + vehicle_no + '\'' +
                '}';
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_no() {
        return this.vehicle_no;
    }

    public void setVehicle_no(String vehicle_no2) {
        this.vehicle_no = vehicle_no2;
    }
}


