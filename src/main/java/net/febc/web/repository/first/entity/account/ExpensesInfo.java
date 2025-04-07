package net.febc.web.repository.first.entity.account;

import lombok.*;
import net.febc.web.repository.first.entity.base.BaseTime;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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

    @Column(name = "item", nullable = false, length = 256)
    @Comment("항목")
    private String item;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "memo", nullable = false, length = 256)
    private String memo;
}
