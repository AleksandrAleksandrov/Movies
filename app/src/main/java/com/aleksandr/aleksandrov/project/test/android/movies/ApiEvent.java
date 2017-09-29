package com.aleksandr.aleksandrov.project.test.android.movies;

/**
 * Created by aleksandr on 9/29/17.
 */

public class ApiEvent {

    private String event;
    private boolean success;
    private String errorMessage;

    public ApiEvent(String event, boolean success, String errorMessage) {
        this.event = event;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
