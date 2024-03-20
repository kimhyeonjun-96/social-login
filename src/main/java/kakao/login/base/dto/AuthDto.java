package kakao.login.base.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AuthDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;

    private String accessToken;
    private String refreshToken;
}
