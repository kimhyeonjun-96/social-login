package kakao.login.base.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class HttpCallService {

    protected static final String APP_TYPE_URL_ENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    protected static final String APP_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * Http 요청 클라이언트 객체 생성 메서드
     *
     * @param headers : HttpHeader 정보
     * @param params : Httpbody wjdqh
     * @return HttpEntity : 생성된 HttpClient 객체 정보 반환
     */
    public HttpEntity<?> httpClientEntity(HttpHeaders headers, Object params) {

        HttpHeaders requestHeaders = headers;
        if (params == null || "".equals(params)) {
            return new HttpEntity<>(requestHeaders);
        } else return new HttpEntity<>(params, requestHeaders);
    }

    /**
     * Http 요청 메서드
     *
     * @param url : 요청 URL 정보
     * @param method : 요청 메서드 정보
     * @param entity : 요청 EntityClient 객체 정보
     * @return : 생성된 HttpClient 객체 정보 반환
     */
    public ResponseEntity<String> httpRequest(String url, HttpMethod method, HttpEntity<?> entity) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity, String.class);
    }

    /**
     * Http 요청 메서드
     *
     * @param url : 요청 URL 정보
     * @param method : 요청 메서드 정보
     * @param entity : 요청 EntityClient 객체 정보
     * @return : 생성된 HttpClient 객체 정보 반환
     */
    public ResponseEntity<String> httpRequest(URI url, HttpMethod method, HttpEntity<?> entity) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity, String.class);
    }
}
