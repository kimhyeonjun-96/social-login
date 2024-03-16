package kakao.login.base.controller;

import kakao.login.base.dto.AuthDto;
import kakao.login.base.service.AuthService;
import kakao.login.message.dto.DefaultMessageDto;
import kakao.login.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class BaseController {

    private final AuthService authService;
    private final MessageService messageService;
    private final MessageSource messageSource;

//    @GetMapping("/login/oauth2/code/kakao")
    @GetMapping("/send")
    public String setviceStart(@RequestParam("code") String code) {

//        code = messageSource.getMessage("kakao.auth.client_id", null, Locale.getDefault());
        boolean checkToken = authService.saveAuthToken(code);

        String success = messageSource.getMessage("token.issued.success", null, Locale.getDefault());
        String failed = messageSource.getMessage("token.issued.fail", null, Locale.getDefault());

        DefaultMessageDto myMsg = new DefaultMessageDto();
        myMsg.setBtnTitle("자세히보기");
        myMsg.setMobileUrl("");
        myMsg.setObjType("text");
        myMsg.setWebUrl("");
        myMsg.setText("메시지 테스트입니다.");

        if(checkToken){
            messageService.sendMessage(AuthService.authCode, myMsg);
            return success + " 메시지 전송 성공";
        }
        return failed;
    }
}
