package fr.romain120105.launcher.auth.exceptions;

import fr.romain120105.launcher.auth.responses.ErrorResponse;


public class UserMigratedException extends RequestException {

  public UserMigratedException(ErrorResponse error) {
    super(error);
  }

}
