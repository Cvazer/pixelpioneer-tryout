package com.github.cvazer.tryout.pixelpioneer.api;

public class DateParsingException extends RuntimeException {

    public static final String TEMPLATE = "Unable to parse date from string [%s]. Must conform to pattern [%s]";

    public DateParsingException(String source, String pattern) {
        super(String.format(TEMPLATE, source, pattern));
    }
}
