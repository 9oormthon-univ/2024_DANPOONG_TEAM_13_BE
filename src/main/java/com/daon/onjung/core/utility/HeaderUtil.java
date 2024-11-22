package com.daon.onjung.core.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class HeaderUtil {

    public static Optional<String> refineHeader(HttpServletRequest request, String header, String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            return Optional.empty();
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }

}
