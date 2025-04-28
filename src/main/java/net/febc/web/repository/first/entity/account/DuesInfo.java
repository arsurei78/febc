package net.febc.web.repository.first.entity.account;

import lombok.*;
import net.febc.web.repository.first.entity.member.MemberInfo;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_DUES_INFO")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class DuesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MemberInfo memberInfo;
    
    @Column(name = "date", nullable = false)
    @Comment("납입해당일")
    private LocalDate date;

    @Column(name = "deposit_at", nullable = false)
    @Comment("실제 입금일")
    private LocalDate depositAT;

    @Column(name = "standard_account", nullable = false)
    @Comment("기준금액")
    private Integer standardAccount;

    @Column(name = "account", nullable = false)
    @Comment("실제 입금액")
    private Integer deposit;

    @Column(name = "memo", length = 256)
    private String memo;
}
