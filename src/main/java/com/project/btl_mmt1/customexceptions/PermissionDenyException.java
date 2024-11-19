package com.project.btl_mmt1.customexceptions;

public class PermissionDenyException extends Exception{
    public PermissionDenyException(String message) {
        super(message);
    }
}
