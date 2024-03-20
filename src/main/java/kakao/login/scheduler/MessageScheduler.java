package kakao.login.scheduler;

import kakao.login.base.repository.AuthRepositoryImpl;
import kakao.login.base.service.AuthService;
import kakao.login.domain.User;
import kakao.login.domain.repository.UserRepository;
import kakao.login.message.dto.DefaultMessageDto;
import kakao.login.message.dto.ListMessageDto;
import kakao.login.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageScheduler {

    private final MessageSource messageSource;
    private final MessageService messageService;
    private final AuthService authService;
    private final AuthRepositoryImpl authRepositoryImpl;
    private final UserRepository userRepository;

    /**
     * 테스트 메시지를 매 분마다 전달하기
     */
//    @Scheduled(cron = "0 * * * * ?")
    public void testMessage(){

        log.info("====== testMessage 메서드 실행 ======");

        String authToken;
        ListMessageDto msgDto = new ListMessageDto();
        msgDto.setHeaderTitle("자세히 보기");
        msgDto.setWebUrl("");
        msgDto.setMobileUrl("");

        List<ListMessageDto> msgDtoItemList = new ArrayList<>();
        int idx = 1;
        List<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println("user.toString() = " + user.toString());

            authToken = user.getAuthToken();
            System.out.println("authToken = " + authToken);
            if (StringUtils.hasText(authToken)){
                DefaultMessageDto myMsg = new DefaultMessageDto();
                myMsg.setBtnTitle("자세히보기");
                myMsg.setMobileUrl("");
                myMsg.setObjType("text");
                myMsg.setWebUrl("");
                myMsg.setText("메시지 테스트입니다.");
                messageService.sendMessage(AuthService.authCode, myMsg);
            }else{
                System.out.println("발급된 토큰이 없습니다.");
            }
        }
        log.info("====== testMessage 메서드 종료 ======");
    }
}
