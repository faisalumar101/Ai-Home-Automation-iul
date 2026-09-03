package com.BilalAhmad.smarthome.model;

public class Room {
    private String roomId;
    private String roomName;
    private String roomStatus; // Ex: "Presence Detected" ya "No Presence"
    private boolean isPresenceDetected;
    private boolean isMasterOn;

    public Room(String roomId, String roomName, String roomStatus, boolean isPresenceDetected, boolean isMasterOn) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.isPresenceDetected = isPresenceDetected;
        this.isMasterOn = isMasterOn;
    }

    public String getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public String getRoomStatus() { return roomStatus; }
    public boolean isPresenceDetected() { return isPresenceDetected; }
    public boolean isMasterOn() { return isMasterOn; }
    public void setMasterOn(boolean masterOn) { isMasterOn = masterOn; }
}