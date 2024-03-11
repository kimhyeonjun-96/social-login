package kakao.login.domain.contorller;

import kakao.login.config.auth.PrincipalDetails;
import kakao.login.domain.User;
import kakao.login.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        User user = principalDetails.getUser();
        model.addAttribute("user", user);
        return "user";
    }
}
