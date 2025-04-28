package net.febc.web.repository.first.entity.account;

import lombok.*;
import net.febc.cmmn.convert.BooleanConverter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_CLOSING_INFO")
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

    @Column(name = "s_date")
    @Comment("정산일(시작)")
    private LocalDate startDate;

    @Column(name = "e_date")
    @Comment("정산일(종료)")
    private LocalDate endDate;

    @Column(name = "title", nullable = false, length = 512)
    @Comment("타이틀")
    private String title;

    @Column(name = "import_amount", nullable = false)
    @Comment("총 수입")
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

    @Convert(converter = BooleanConverter.class)
    @Column(name = "delete_flg", nullable = false)
    @Comment("삭제 플러그")
    private Boolean deleteFlg;
}
