package net.febc.web.repository.first.entity.user;

import net.febc.cmmn.convert.GrantedAuthorityConverter;
import net.febc.web.repository.first.entity.base.BaseTime;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "T_USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
public class UserInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 20, nullable = false, unique = true)
    @Comment("사원번호")
    private String userId;

    @Column(name = "username", length = 200, nullable = false)
    @Comment("이름(암호화)")
    private String username;

    @Column( name = "password", nullable = false, length = 128)
    @Comment("패스워드")
    private String password;

    @Comment("권한 설정 ROLE_USER:유저 / ROLE_ADMIN:관리자")
    @Column(name = "authorities", length = 32, nullable = false)
    @Convert(converter = GrantedAuthorityConverter.class )
    private GrantedAuthority authorities;

    @Column(name = "api_token", length = 40)
    @Comment("관리자 로그인 토큰(관리자 사이트용) ")
    private String apiToken;

    @Column(name = "refresh_token", length = 256)
    @Comment("리프레쉬 토큰 정보")
    private String refreshToken;

    @Column(name = "mobile", length = 200)
    @Comment("핸드폰(암호화)")
    private String mobile;
}
