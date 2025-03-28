package net.febc.web.repository.first.write;

import net.febc.web.repository.first.entity.member.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

    boolean existsByIdAndState(Long id, Boolean state);
}
