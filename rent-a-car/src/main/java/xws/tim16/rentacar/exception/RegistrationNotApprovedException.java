package xws.tim16.rentacar.exception;

public class RegistrationNotApprovedException extends RuntimeException {
    public RegistrationNotApprovedException() {
    }

    public RegistrationNotApprovedException(String message) {
        super(message);
    }
}
