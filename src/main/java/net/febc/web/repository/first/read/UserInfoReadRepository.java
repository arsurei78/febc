package net.febc.web.repository.first.read;

import net.febc.web.repository.first.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoReadRepository extends JpaRepository<UserInfo, Long> {

    /**
     * 회원정보를 조회
     * @param userId 회원ID
     * @return 조회 회원 정보 (조회 회원이 없을 경우, null을 반환)
     */
    Optional<UserInfo> findByUserId(String userId);

}
