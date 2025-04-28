package net.febc.web.dto.res.closing;

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
public class ResDetailDto {

    private Long id;
    // 이월 금액
    private String carryOverMon;
    // 이월 산정 기간
    private String carryOverDate;
    // 결산 기간
    private String closingDate;
    // 총 수입
    private String totRevenue;
    // 총 지출
    private String totExpenditure;
    // 총 잔액
    private String balance;
    // 수입 정보
    private List<DetailInfo> revenueList;
    // 지출 정보
    private List<DetailInfo> expendList;
    // 결산 시작일
    private String closingStrDate;
    // 결산 종료일
    private String closingEndDate;

    /**
     * 결산 정보
     * @param carryOverInfo 이월 금액
     * @param closingDataList 수입 / 지출
     */
    public ResDetailDto(ClosingInfo carryOverInfo,
                        List<DetailInfo> closingDataList,
                        LocalDate startDate,
                        LocalDate endDate) {
        // 수입 정보
        this.revenueList = closingDataList.stream()
                .filter(f -> f.getType().equals(Constants.EXPENSE_TYPE_I))
                .toList();
        this.revenueList.stream()
                .sorted(Comparator.comparing(DetailInfo::getAmount).reversed());
        
        // 지출정보
        this.expendList = closingDataList.stream()
                .filter(f -> f.getType().equals(Constants.EXPENSE_TYPE_O))
                .toList();
        this.expendList.stream()
                .sorted(Comparator.comparing(DetailInfo::getAmount).reversed());

        int totRevenue = this.revenueList.stream().mapToInt(DetailInfo::getAmount).sum();
        int totExpend = this.expendList.stream().mapToInt(DetailInfo::getAmount).sum();
        Integer carryOver = 0;
        if (carryOverInfo != null) {
            carryOver = carryOverInfo.getBalance();
            this.carryOverDate = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, carryOverInfo.getStartDate()) + "~"
                    + CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, carryOverInfo.getEndDate());
        }

        // 총 잔액
        int totBalance = carryOver + totRevenue - totExpend;
        // 총 잔액
        this.balance = CommonUtils.makeMoney(totBalance);
        // 이월금액
        this.carryOverMon = CommonUtils.makeMoney(carryOver);
        // 총 수입
        this.totRevenue = CommonUtils.makeMoney(totRevenue);
        // 총 지출
        this.totExpenditure = CommonUtils.makeMoney(totExpend);
        this.closingDate = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, startDate) + "~"
                + CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYMMDD, endDate);
        this.closingStrDate = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMMDD, startDate);
        this.closingEndDate = CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMMDD, endDate);


    }
}
