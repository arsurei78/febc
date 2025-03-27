package net.febc.web.repository.first.entity.member;

import lombok.*;
import net.febc.cmmn.convert.BooleanConverter;
import net.febc.web.repository.first.entity.base.BaseTime;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_MEMBER_INFO")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class MemberInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "join_at")
    @Comment("입단일")
    private LocalDate joinAt;

    @Column(name = "withdraw_at")
    @Comment("퇴단일")
    private LocalDate withdrawAt;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "state", nullable = false)
    @Comment("상태 (Y:유지중, N:퇴단)")
    private Boolean state;
}
