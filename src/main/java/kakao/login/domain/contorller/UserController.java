package kakao.login.domain.contorller;

import kakao.login.config.auth.PrincipalDetails;
import kakao.login.config.oauth.provider.KakaoUserInfo;
import kakao.login.domain.KakaoApi;
import kakao.login.domain.User;
import kakao.login.domain.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final KakaoApi kakaoApi;

    @GetMapping("/login")
    public String loginForm(Model model) {

        log.info("카카오 로그인 요청");
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
        log.info("카카오 클라이언트 아이디 = {}, 카카오 리다이렉트 uri = {}", kakaoApi.getKakaoApiKey(), kakaoApi.getKakaoRedirectUri());
        return "login";
    }

    @GetMapping("/user")
    public String moveUserPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        User user = principalDetails.getUser();
        log.info("move to user page = {}", user.toString());
        model.addAttribute("user", user);
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        return "user";
    }

    @GetMapping("/addOption")
    public String addOptionForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){

        User user = principalDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        return "addOption";
    }
}
