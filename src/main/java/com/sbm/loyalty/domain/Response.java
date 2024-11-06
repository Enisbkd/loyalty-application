package com.sbm.loyalty.domain;

import java.time.LocalDate;

public class Response {
    private String message;
    private Integer points;
    private LocalDate validUntil;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Response(String message, Integer points, LocalDate validUntil) {
        this.message = message;
        this.points = points;
        this.validUntil = validUntil;
    }

    @Override
    public String toString() {
        return "Response{" +
            "message='" + message + '\'' +
            ", points=" + points +
            ", validUntil='" + validUntil + '\'' +
            '}';
    }
}
