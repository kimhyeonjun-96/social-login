package kakao.login.base.service;

import kakao.login.base.dto.AuthDto;

public interface AuthServiceInterface {

    public boolean saveAuthToken(String code);

    public boolean saveAuthRefresh();

    public AuthDto getKakaoAuthToken(String code);

    public AuthDto getRefreshToken();
}
