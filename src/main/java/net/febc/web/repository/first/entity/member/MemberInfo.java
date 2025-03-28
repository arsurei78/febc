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
    @Comment("이름")
    private String name;

    @Column(name = "sex", nullable = false)
    @Comment("성별")
    private String sex;

    @Column(name = "generation", nullable = false)
    @Comment("기수")
    private Integer generation;
    
    @Column(name = "dues", nullable = false)
    @Comment("회비")
    private Integer dues;

    @Column(name = "join_at", nullable = false)
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
