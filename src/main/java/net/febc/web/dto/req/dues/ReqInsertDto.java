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
    // 입금일
    private String date;
    // 납입금액
    private Integer deposit;
    // 메모
    private String memo;

    public DuesInfo toEntity(MemberInfo memberInfo) {
        LocalDate localDate = CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, this.date);
        return DuesInfo
                .builder()
                .deposit(deposit)
                .year(localDate.getYear())
                .month(localDate.getMonth().getValue())
                .depositAT(localDate)
                .memo(memo)
                .build();
    }
}
