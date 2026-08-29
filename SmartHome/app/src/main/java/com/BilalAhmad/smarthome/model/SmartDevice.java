package com.BilalAhmad.smarthome.model;

public class SmartDevice {
    private String deviceId;
    private String name; //Ex: "Ceiling fan", "Main light"
    private String room; //Ex: "Living room", "Kitchen"
    private String deviceType; //Ex: "Switch", "Slider"
    private String mqttTopic; //Ex: "home/livingroom/light"
    private boolean isSwitchedOn;
    private int value; //for fan speed value

    //Empty constructor is required
    public SmartDevice(){}

    public SmartDevice(String deviceId, String name, String room, String deviceType, String mqttTopic){
        this.deviceId = deviceId;
        this.name = name;
        this.room = room;
        this.deviceType = deviceType;
        this.mqttTopic = mqttTopic;
        this.isSwitchedOn = false;
        this.value = 0;
    }

    //Getters and Setters
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public String getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getMqttTopic() {
        return mqttTopic;
    }
    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }
    public boolean isSwitchedOn() {
        return isSwitchedOn;
    }
    public void setSwitchedOn(boolean switchedOn) {
        isSwitchedOn = switchedOn;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }


}
