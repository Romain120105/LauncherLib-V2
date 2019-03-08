package fr.romain120105.launcher.auth.exceptions;

import fr.romain120105.launcher.auth.responses.ErrorResponse;

/**
 * Thrown when the authentication servers are unreachable.
 */
public class AuthenticationUnavailableException extends Exception {

  public AuthenticationUnavailableException(ErrorResponse error) {

  }

}
