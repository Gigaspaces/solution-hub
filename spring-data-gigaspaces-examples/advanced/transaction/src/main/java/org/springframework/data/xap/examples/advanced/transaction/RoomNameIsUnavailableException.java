package org.springframework.data.xap.examples.advanced.transaction;

/**
 * @author Anna_Babich.
 */
public class RoomNameIsUnavailableException extends RuntimeException {
    public RoomNameIsUnavailableException(String message) {
        super(message);
    }
}
