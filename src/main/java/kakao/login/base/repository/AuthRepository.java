package kakao.login.base.repository;

import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    private static String authToken;
    private static String refreshAuthToken;

    public boolean saveAuthToken(String authToken) {
        AuthRepository.authToken = authToken;
        return AuthRepository.authToken.equals(authToken);
    }

    public boolean saveRefreshAuthToken(String refrashToken) {
        AuthRepository.refreshAuthToken = refrashToken;
        return AuthRepository.refreshAuthToken.equals(refrashToken);
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getRefreshAuthToken() {
        return refreshAuthToken;
    }
}
