package net.febc.web.dto.req.closing;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.dto.res.closing.detail.DetailInfo;
import net.febc.web.repository.first.entity.account.ClosingInfo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class ReqInsertDto {

    // 결산 시작일
    private String startDate;
    // 결산 종료일
    private String endDate;

    /**
     * 시작일
     * @return
     */
    public LocalDate getStartDate() {
        return CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, startDate);
    }

    public LocalDate getEndDate() {
        return CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, endDate);
    }

    public ClosingInfo toEntity(ClosingInfo carryOverInfo,
                                List<DetailInfo> closingDataList,
                                LocalDate startDate,
                                LocalDate endDate) {
        // 수입 정보
        List<DetailInfo> revenueList = closingDataList.stream()
                .filter(f -> f.getType().equals(Constants.EXPENSE_TYPE_I))
                .toList();

        // 지출정보
        List<DetailInfo> expendList = closingDataList.stream()
                .filter(f -> f.getType().equals(Constants.EXPENSE_TYPE_O))
                .toList();

        int totRevenue = revenueList.stream().mapToInt(DetailInfo::getAmount).sum();
        int totExpend = expendList.stream().mapToInt(DetailInfo::getAmount).sum();
        Integer carryOver = 0;
        if (carryOverInfo != null) {
            carryOver = carryOverInfo.getBalance();
        }
        String title = "단회비 결산보고(" + CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, startDate) + "~"
                + CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, endDate) + ")";

        // 총 잔액
        int totBalance = carryOver + totRevenue - totExpend;

        return ClosingInfo
                .builder()
                .balance(totBalance)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .beforeAmount(carryOver)
                .importAmount(totRevenue)
                .totalExp(totExpend)
                .deleteFlg(Boolean.FALSE)
                .build();
    }

}
