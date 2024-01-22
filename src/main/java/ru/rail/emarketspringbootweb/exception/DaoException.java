package ru.rail.emarketspringbootweb.exception;

import lombok.extern.log4j.Log4j;



public class DaoException extends RuntimeException {

    public DaoException(String message, Throwable cause) {
        super(message, cause);

    }

    public DaoException(Throwable cause) {
        super(cause);

    }
}