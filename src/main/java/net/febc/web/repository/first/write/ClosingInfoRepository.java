package net.febc.web.repository.first.write;

import net.febc.web.repository.first.entity.account.ClosingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosingInfoRepository extends JpaRepository<ClosingInfo, Long> {
}
