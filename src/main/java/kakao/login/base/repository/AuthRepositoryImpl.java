package kakao.login.base.repository;

import jakarta.persistence.EntityManager;
import kakao.login.base.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl {

    private final EntityManager em;
    private static String authToken;
    private static String refreshAuthToken;


    public boolean saveAuthToken(String authToken) {
        AuthRepositoryImpl.authToken = authToken;
        return AuthRepositoryImpl.authToken.equals(authToken);
    }

    public void saveAuthDto(AuthDto authDto) {
        em.persist(authDto);
    }


    public boolean saveRefreshAuthToken(String refrashToken) {
        AuthRepositoryImpl.refreshAuthToken = refrashToken;
        return AuthRepositoryImpl.refreshAuthToken.equals(refrashToken);
    }

    public String getAuthToken(Long authDtoId) {
        AuthDto authDto = em.find(AuthDto.class, authDtoId);
//        return authToken;
        return authDto.getAccessToken();
    }

    public String getRefreshAuthToken() {
        return refreshAuthToken;
    }
}
