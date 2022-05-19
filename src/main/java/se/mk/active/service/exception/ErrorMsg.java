package se.mk.active.service.exception;

import org.slf4j.Logger;

public final class ErrorMsg {
    public static final String PROVIDER_NOT_FOUND = "Provider with id %s was not found...";

    public static final String USER_NOT_FOUND = "User with id %s was not found...";

    public static final String VENUE_NOT_FOUND = "Venue with id %s was not found...";

    public static final String EVENT_NOT_FOUND = "Event with id %s was not found...";

    public static String createErrorMsgAndLog(String msg, Long id, Logger LOG) {
        String errorMsg = String.format(msg, id);
        LOG.error(errorMsg);

        return errorMsg;
    }
}
