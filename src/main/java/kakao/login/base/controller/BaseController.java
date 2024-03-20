package kakao.login.base.controller;

import kakao.login.base.service.AuthService;
import kakao.login.config.auth.PrincipalDetails;
import kakao.login.domain.KakaoApi;
import kakao.login.domain.User;
import kakao.login.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequiredArgsConstructor
public class BaseController {

    private final AuthService authService;
    private final MessageService messageService;
    private final MessageSource messageSource;
    private final KakaoApi kakaoApi;

    // 테스트 목적으로 작성
    @GetMapping("/setCode")
    public String sendMessage(@RequestParam String code, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        log.info("code = {}", code);
        authService.saveAuthToken(code, principalDetails);
        return "redirect:/user";

//        boolean checkToken = authService.saveAuthToken(code);
//
//        String success = messageSource.getMessage("token.issued.success", null, Locale.getDefault());
//        String failed = messageSource.getMessage("token.issued.fail", null, Locale.getDefault());
//
//        DefaultMessageDto myMsg = new DefaultMessageDto();
//        myMsg.setBtnTitle("자세히보기");
//        myMsg.setMobileUrl("");
//        myMsg.setObjType("text");
//        myMsg.setWebUrl("");
//        myMsg.setText("메시지 테스트입니다.");
//
//        if (checkToken) {
//            messageService.sendMessage(AuthService.authCode, myMsg);
//            return success + " 메시지 전송 성공";
//        }
//        return failed;
    }
}
