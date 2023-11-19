package exceptions;

import entities.User;

public class InvalidUserInfoException extends Throwable {

    private final User user;

    public InvalidUserInfoException(User user) {
        this.user = user;
    }

    @Override
    public String getMessage() {
        return "User:" + this.user.toString() + "information is invalid" ;
    }
}
