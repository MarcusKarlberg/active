package se.mk.active.service.exception;

import org.slf4j.Logger;

public final class ErrorMsg {
    public static final String PROVIDER_NOT_FOUND = "Provider with id %s was not found...";

    public static final String USER_NOT_FOUND = "User with id %s was not found...";

    public static final String VENUE_NOT_FOUND = "Venue with id %s was not found...";

    public static final String EVENT_NOT_FOUND = "Event with id %s was not found...";

    public static final String FILE_NOT_FOUND = "File with id %s was not found...";

    public static final String CONTAINS_NO_FILES = "No files are connected to this entity";

    public static String createErrorMsgAndLog(String msg, Long id, Logger LOG) {
        String errorMsg = String.format(msg, id);
        LOG.error(errorMsg);

        return errorMsg;
    }
}
