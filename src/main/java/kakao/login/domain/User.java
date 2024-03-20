package kakao.login.domain;

import jakarta.persistence.*;
import kakao.login.base.dto.AuthDto;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;

    private String name;
    private String password;
    private String email;

    private String provider;
    private String providerId;

    @OneToOne
    @JoinColumn(name = "auth_id")
    private AuthDto authDto;

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.name;
    }

    public void updateToken(AuthDto authDto) {
        this.authDto = authDto;
    }

    public String getAuthToken(){
        return authDto.getAccessToken();
    }
}
