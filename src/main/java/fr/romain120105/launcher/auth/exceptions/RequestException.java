package fr.romain120105.launcher.auth.exceptions;

import fr.romain120105.launcher.auth.responses.ErrorResponse;

public class RequestException extends Exception {

  private ErrorResponse error;

  public RequestException(ErrorResponse error) {
    this.error = error;
  }

  public ErrorResponse getResponse() {
    return this.error;
  }

  public String getError() {
    return this.error.getError();
  }

  public String getErrorMessage() {
    return this.error.getErrorMessage();
  }

  public String getErrorCause() {
    return this.error.getCause();
  }

}
