package kakao.login.domain.service;

import kakao.login.base.dto.AuthDto;
import kakao.login.domain.User;
import kakao.login.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void addToken(AuthDto authDto, String username) {

        User findUser = userRepository.findByName(username).get();
        findUser.updateToken(authDto);
    }

}
