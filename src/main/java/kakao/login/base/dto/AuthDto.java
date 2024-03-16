package kakao.login.base.dto;

import lombok.Data;

@Data
public class AuthDto {

    private String accessToken;
    private String refreshToken;
}
