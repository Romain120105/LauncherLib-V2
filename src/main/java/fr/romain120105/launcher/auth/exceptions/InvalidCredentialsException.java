package fr.romain120105.launcher.auth.exceptions;


import com.sun.applet2.preloader.event.ErrorEvent;
import fr.romain120105.launcher.auth.responses.ErrorResponse;

/**
 * Thrown when the provided username/password pair is wrong or empty.
 */
public class InvalidCredentialsException extends RequestException {

  public InvalidCredentialsException(ErrorResponse error) {
    super(error);
  }

}
