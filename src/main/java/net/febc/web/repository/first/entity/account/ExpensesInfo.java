package net.febc.web.repository.first.entity.account;

import lombok.*;
import net.febc.web.repository.comm.ExpensensEnum;
import net.febc.web.repository.first.entity.base.BaseTime;
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
public class ExpensesInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date",  nullable = false)
    @Comment("정산일")
    private LocalDate date;

    @Column(name = "type", nullable = false, length = 1)
    @Comment("수입(I) / 지출(O)")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "expens_type", nullable = false, length = 6)
    @Comment("교사 사례비(TS), 지방세(LOT), 소득세(INT), 선물(GIFT), 간식비(SNACK), 악보제본비(SHEET), 기타(ORTHER)")
    private ExpensensEnum expensType;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "memo", length = 256)
    private String memo;
}
