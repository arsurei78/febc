package net.febc.web.repository.first.write;

import net.febc.web.repository.first.entity.account.ExpensesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuesInfoRepository extends JpaRepository<ExpensesInfo, Long> {
}
