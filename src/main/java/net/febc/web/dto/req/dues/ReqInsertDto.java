package net.febc.web.dto.req.dues;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.repository.first.entity.account.DuesInfo;
import net.febc.web.repository.first.entity.member.MemberInfo;

import java.time.LocalDate;

@Getter
@Setter
public class ReqInsertDto {
    // 멤버 번호
    private Long memberId;
    // 입금일
    private String date;
    // 납입금액
    private Integer payment;
    // 메모
    private String memo;

    public DuesInfo toEntity(MemberInfo memberInfo) {
        LocalDate duesDate = CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, this.date + "-01");
        return DuesInfo
                .builder()
                .deposit(payment)
                .date(duesDate)
                .depositAT(LocalDate.now())
                .memberInfo(memberInfo)
                .standardAccount(memberInfo.getDues())
                .memo(memo)
                .build();
    }
}
