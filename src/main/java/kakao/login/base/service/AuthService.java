package kakao.login.base.service;


import kakao.login.base.dto.AuthDto;
import kakao.login.base.repository.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Slf4j
@Service
public class AuthService extends HttpCallService implements AuthServiceInterface{

    private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";
    public static String authCode;

    @Autowired MessageSource messageSource;
    @Autowired AuthRepository authRepository;

    public boolean saveAuthToken(String code) {

        AuthDto authDto = getKakaoAuthToken(code);
        return authRepository.saveAuthToken(authDto.getAccessToken()) && authRepository.saveRefreshAuthToken(authDto.getRefreshToken());
    }

    @Override
    public boolean saveAuthRefresh() {

        AuthDto authDto = getRefreshToken();
        return authRepository.saveAuthToken(authDto.getAccessToken());
    }

    @Override
    public AuthDto getKakaoAuthToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";

        String tokenFailMessage = messageSource.getMessage("token.issued.fail", null, Locale.getDefault());
        String tokenSuccessMessage = messageSource.getMessage("token.issued.success", null, Locale.getDefault());

        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        headers.set("Content-Type", APP_TYPE_URL_ENCODED);
        parameters.add("code", code);
        parameters.add("grant_type", messageSource.getMessage("kakao.auth.grant_type", null, Locale.getDefault()));
        parameters.add("client_id", messageSource.getMessage("kakao.auth.client_id", null, Locale.getDefault()));
        parameters.add("redirect_url", messageSource.getMessage("kakao.auth.redirect_url", null, Locale.getDefault()));
        parameters.add("client_secret", messageSource.getMessage("kakao.auth.client_secret", null, Locale.getDefault()));

        HttpEntity<?> requestEntity = httpClientEntity(headers, parameters);

        log.info("======================== Auth Request Start ========================");
        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());

        accessToken = jsonData.get("access_token").toString();
        refreshToken = jsonData.get("refresh_token").toString();
        System.out.println("accessToken = " + accessToken);
        System.out.println();
        System.out.println("refreshToken = " + refreshToken);


        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)) {
            throw new ServiceException(tokenFailMessage);
        }

        AuthDto authDto = new AuthDto();
        authDto.setAccessToken(accessToken);
        authCode = accessToken;
        authDto.setRefreshToken(refreshToken);

        log.info("success = {}", tokenSuccessMessage);
        log.info("======================== Auth Request End ========================");

        return authDto;
    }

    @Override
    public AuthDto getRefreshToken() {

        HttpHeaders headers = new HttpHeaders();
        String accessToken = "";
        String tokenFailMessage = messageSource.getMessage("token.issued.fail", null, Locale.getDefault());
        String tokenSuccessMessage = messageSource.getMessage("token.issued.success", null, Locale.getDefault());
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        headers.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("grant_type", messageSource.getMessage("kakao.auth.refresh_grant_type",null, Locale.getDefault()));
        parameters.add("client_id", messageSource.getMessage("kakao.auth.client_id",null, Locale.getDefault()));
        parameters.add("refresh_token", authRepository.getRefreshAuthToken());
        parameters.add("client_secret", messageSource.getMessage("kakao.auth.client_secret",null, Locale.getDefault()));

        HttpEntity<?> requestEntiry = httpClientEntity(headers, parameters);

        log.info("======================== Authrefresh Request Start ========================");
        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntiry);
        JSONObject jsonData = new JSONObject(Integer.parseInt(response.getBody()));
        accessToken = jsonData.get("access_token").toString();
        if (StringUtils.isEmpty(accessToken)) {
            throw new ServiceException(tokenFailMessage);
        }

        AuthDto authDto = new AuthDto();
        authDto.setAccessToken(accessToken);

        log.info("success = {}", tokenSuccessMessage);
        log.info("======================== Authrefresh Request End ========================");

        return authDto;
    }

}
