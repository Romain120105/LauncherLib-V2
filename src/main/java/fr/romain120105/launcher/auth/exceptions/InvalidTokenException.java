package fr.romain120105.launcher.auth.exceptions;

import fr.romain120105.launcher.auth.exceptions.RequestException;
import fr.romain120105.launcher.auth.responses.ErrorResponse;

/**
 * Thrown when the provided token is invalid or expired.
 */
public class InvalidTokenException extends RequestException {

  public InvalidTokenException(ErrorResponse error) {
    super(error);
  }

}
