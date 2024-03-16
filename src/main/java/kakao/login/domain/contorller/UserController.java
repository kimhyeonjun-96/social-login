package kakao.login.domain.contorller;

import kakao.login.config.auth.PrincipalDetails;
import kakao.login.domain.User;
import kakao.login.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/user")
    public String moveUserPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        User user = principalDetails.getUser();
        System.out.println("moveUserPage = " + user.toString());
        model.addAttribute("user", user);
        return "user";
    }
}
