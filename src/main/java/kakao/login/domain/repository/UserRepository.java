package kakao.login.domain.repository;

import kakao.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
