package com.trueview.camera;

/**
 * Simple container for visual camera offsets and modifiers.
 */
public class CameraState {
    public float offsetX = 0f;
    public float offsetY = 0f;
    public float offsetZ = 0f;
    public float roll = 0f; // degrees
    public float extraFov = 0f; // degrees

    public CameraState copy() {
        CameraState c = new CameraState();
        c.offsetX = offsetX;
        c.offsetY = offsetY;
        c.offsetZ = offsetZ;
        c.roll = roll;
        c.extraFov = extraFov;
        return c;
    }

    public void setTo(CameraState other) {
        offsetX = other.offsetX;
        offsetY = other.offsetY;
        offsetZ = other.offsetZ;
        roll = other.roll;
        extraFov = other.extraFov;
    }

    public void reset() {
        offsetX = offsetY = offsetZ = roll = extraFov = 0f;
    }
}
