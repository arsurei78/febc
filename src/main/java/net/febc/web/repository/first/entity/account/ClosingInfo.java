package net.febc.web.repository.first.entity.account;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_EXPENSE_INFO")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class ClosingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    @Comment("정산일")
    private LocalDate date;

    @Column(name = "import_amount", nullable = false)
    @Comment("총 수입익")
    private Integer importAmount;

    @Column(name = "before_amount", nullable = false)
    @Comment("전월 금액")
    private Integer beforeAmount;

    @Column(name = "total_exp", nullable = false)
    @Comment("총 지출금액")
    private Integer totalExp;

    @Column(name = "balance", nullable = false)
    @Comment("잔액")
    private Integer balance;
}
