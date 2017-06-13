package ru.nsu.ccfit.boltava.model.server;

import java.util.HashMap;

import static ru.nsu.ccfit.boltava.model.server.Errors.ErrorName.*;

abstract class Errors {

    public static HashMap<ErrorName, String> ErrorMessages = new HashMap<>();
    public static HashMap<ErrorName, Integer> ErrorCodes = new HashMap<>();

    static {
        ErrorMessages.put(UsernameIsTaken, "Username is already taken");
        ErrorMessages.put(InvalidUsernameFormat, "Invalid username format");
        ErrorMessages.put(UsernameMismatch, "Registered username doesn't match message sender's username");
        ErrorMessages.put(SessionIdMismatch, "Given out session id doesn't match message sender's session id");
    }

    static {
        int code = 0;
        for (ErrorName name : ErrorName.values()) {
            ErrorCodes.put(name, code);
            code++;
        }
    }

    public enum ErrorName {
        UsernameIsTaken,
        InvalidUsernameFormat,
        UsernameMismatch,
        SessionIdMismatch
    }

}
