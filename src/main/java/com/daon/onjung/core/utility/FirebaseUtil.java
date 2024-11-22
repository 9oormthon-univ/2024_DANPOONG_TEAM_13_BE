package com.daon.onjung.core.utility;

import com.daon.onjung.account.domain.type.ECategory;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseUtil {

    @Value("${google.firebase.config.path}")
    private String firebaseConfigPath;

    @Value("${google.access-token.request.url}")
    private String GOOGLE_ACCESS_TOKEN_URL;

    @Value("${google.notification.url}")
    private String GOOGLE_NOTIFICATION_URL;

    private static final String BEARER_PREFIX = "Bearer " ;
    private static final String JSON_BODY_KEY_TITLE = "title";
    private static final String JSON_BODY_KEY_BODY = "body";
    private static final String JSON_BODY_KEY_TOKEN = "token";
    private static final String JSON_BODY_KEY_NOTIFICATION = "notification";
    private static final String JSON_BODY_KEY_MESSAGE = "message";
    private static final String JSON_BODY_KEY_VALIDATE_ONLY = "validate_only";

    public String createFirebaseRequestUrl() {
        return UriComponentsBuilder.fromHttpUrl(GOOGLE_NOTIFICATION_URL)
                .toUriString();
    }

    public HttpHeaders createFirebaseRequestHeaders() {

        String accessToken = null;

        try {
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                    .createScoped(List.of(GOOGLE_ACCESS_TOKEN_URL));

            credentials.refreshIfExpired();

            accessToken = BEARER_PREFIX + credentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            log.error("Google Access Token을 가져오는데 실패했습니다.", e);
            throw new CommonException(ErrorCode.EXTERNAL_SERVER_ERROR);        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", accessToken);
        return headers;
    }

    public String createFirebaseRequestBody(
            String deviceToken,
            String storeName,
            String userName,
            String category,
            String address,
            String logoImgUrl,
            String expirationDate
    ) {
        JSONObject messageBody = new JSONObject();

        JSONObject notificationJson = new JSONObject();
        notificationJson.put(JSON_BODY_KEY_TITLE, "\uD83C\uDF89 1만원 금액 식권 당첨");
        notificationJson.put(JSON_BODY_KEY_BODY, "지금 바로 클릭해서 따뜻한 한 끼를 확인해 보세요!");

        JSONObject dataJson = new JSONObject();
        dataJson.put("store_name", storeName);
        dataJson.put("user_name", userName);
        dataJson.put("category", category);
        dataJson.put("address", address);
        dataJson.put("logo_img_url", logoImgUrl);
        dataJson.put("expiration_date", expirationDate);

        JSONObject messageJson = new JSONObject();
        messageJson.put(JSON_BODY_KEY_TOKEN, deviceToken);
        messageJson.put(JSON_BODY_KEY_NOTIFICATION, notificationJson);
        messageJson.put("data", dataJson);

        messageBody.put(JSON_BODY_KEY_MESSAGE, messageJson);
        messageBody.put(JSON_BODY_KEY_VALIDATE_ONLY, false);

        return messageBody.toJSONString();
    }
}
