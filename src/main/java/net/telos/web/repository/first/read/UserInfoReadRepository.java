package net.telos.web.repository.first.read;

import net.telos.web.repository.comm.BaseReadOnlyRepository;
import net.telos.web.repository.first.entity.user.UserInfo;

import java.util.Optional;

public interface UserInfoReadRepository extends BaseReadOnlyRepository<UserInfo, Long> {

    /**
     * 회원정보를 조회
     * @param userId 회원ID
     * @return 조회 회원 정보 (조회 회원이 없을 경우, null을 반환)
     */
    Optional<UserInfo> findByUserId(String userId);
}
