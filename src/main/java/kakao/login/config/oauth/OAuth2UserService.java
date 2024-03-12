package kakao.login.config.oauth;

import com.fasterxml.jackson.core.JsonParser;
import kakao.login.config.auth.PrincipalDetails;
import kakao.login.config.oauth.provider.KakaoUserInfo;
import kakao.login.config.oauth.provider.OAuth2UserInfo;
import kakao.login.domain.User;
import kakao.login.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        // 유저 정보 조회
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        // 권한 부여
//        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
//
//        // nameAttributeKey
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails()
//                .getUserInfoEndpoint()
//                .getUserNameAttributeName();
//
//        // DB 저장 로직이 필요하면 추가
//
//        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
//    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // code를 통해 구성한 정보
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청~");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }else {
            System.out.println("지원하는 로그인이 없어요!");
        }

        Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String dummyPassword = encoder.encode(UUID.randomUUID().toString());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.updatePassword(dummyPassword);
            user.updateEmail(oAuth2UserInfo.getEmail());
            userRepository.save(user);
        }else{
            user = User.builder()
                    .name(oAuth2UserInfo.getName())
                    .password(dummyPassword)
                    .email(oAuth2UserInfo.getEmail())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
