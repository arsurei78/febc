package net.febc.web.repository.first.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.utils.CommonUtils;
import net.febc.web.dto.req.dues.ReqDuesDto;
import net.febc.web.dto.req.dues.ReqListDto;
import net.febc.web.dto.res.dues.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static net.febc.web.repository.first.entity.account.QDuesInfo.duesInfo;
import static net.febc.web.repository.first.entity.account.QExpensesInfo.expensesInfo;
import static net.febc.web.repository.first.entity.member.QMemberInfo.memberInfo;


@Repository
@RequiredArgsConstructor
public class DuesRepositoryImpl {

    private final JPAQueryFactory queryFactory;


    /**
     * 회비 정보를 조회
     * @param duesId
     * @return
     */
    public ResDetailDto getDuesInfo(Long duesId) {
        return this.queryFactory
                .select(new QResDetailDto(duesInfo.id,
                        memberInfo.id,
                        memberInfo.name,
                        memberInfo.dues,
                        duesInfo.deposit,
                        duesInfo.depositAT,
                        duesInfo.date,
                        duesInfo.memo
                        ))
                .from(duesInfo)
                .leftJoin(duesInfo.memberInfo, memberInfo)
                .where(duesInfo.id.eq(duesId))
                .fetchOne();
    }

    /**
     * 마지막 납일월 정보를 가져옴
     * @param memberId
     * @return
     */
    public String lastPaymentDate(Long memberId, Integer add) {
        LocalDate duesDate = this.queryFactory
                .select(duesInfo.date)
                .from(duesInfo)
                .where(duesInfo.memberInfo.id.eq(memberId))
                .orderBy(duesInfo.date.desc())
                .limit(1)
                .fetchOne();
        // 값이 없으면 현재 달
        if (duesDate == null) {
            return CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM, LocalDate.now());
        }
        return CommonUtils.localDateFormat(Constants.DATE_FORMAT_YYYYMM, duesDate);
    }

    /**
     * 회비 납부 정보 조회
     * @param reqListDto 조회 정보
     * @return
     */
    public Page<ResListDto> getDuesInfoList(ReqListDto reqListDto) {
        PageRequest pageRequest = PageRequest.of(reqListDto.getPage(), reqListDto.getOffset());
        // 검색 결과
        List<ResListDto> result = this.queryFactory.select(new QResListDto(duesInfo.id,
                        duesInfo.depositAT,
                        duesInfo.date,
                        memberInfo.generation,
                        duesInfo.deposit,
                        memberInfo.name))
                .from(duesInfo)
                .leftJoin(duesInfo.memberInfo, memberInfo)
                .where(isWhere(reqListDto))
                .orderBy(duesInfo.depositAT.desc())
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset())
                .fetch();
        // 전체 검색 건수
        Long totalCnt = this.queryFactory.select(duesInfo.id.count())
                .from(duesInfo)
                .leftJoin(duesInfo.memberInfo, memberInfo)
                .where(isWhere(reqListDto))
                .fetchOne();

        return new PageImpl<>(result, pageRequest, totalCnt);
    }

    /**
     * 검색 조건
     * @param dto
     * @return
     */
    private BooleanExpression isWhere(ReqListDto dto) {

        BooleanExpression expression = duesInfo.date.between(dto.getStartDate(), dto.getEndDate());
        // 검색어
        if (StringUtils.isNotEmpty(dto.getSearchData())) {
            expression.and(memberInfo.name.contains(dto.getSearchData()));
        }

        // 납부 / 미납 타입
        if (StringUtils.isNotEmpty(dto.getType())) {
            expression.and(duesInfo.deposit.eq(duesInfo.standardAccount));
        }
        return expression;
    }

    /**
     * 회원의 납입 달의 정보를 조회
     * @param duesDto
     * @return
     */
    public ResDuesMonthDto getMemberByMonth(ReqDuesDto duesDto) {
        LocalDate date = CommonUtils.strToLocalDate(Constants.DATE_FORMAT_YYYYMMDD, duesDto.getMonth());
        return this.queryFactory
                .select(new QResDuesMonthDto(duesInfo.id,
                        duesInfo.standardAccount,
                        duesInfo.deposit,
                        duesInfo.memo,
                        duesInfo.date
                        ))
                .from(duesInfo)
                .leftJoin(duesInfo.memberInfo, memberInfo)
                .where(memberInfo.id.eq(Long.valueOf(duesDto.getMemberId())),
                        duesInfo.date.eq(date))
                .fetchOne();
    }

    /**
     * 지정 기간중 납입한 회비를 가져옴
     * @param start
     * @param end
     * @return
     */
    public Integer getTotalDues(LocalDate start, LocalDate end) {
        return this.queryFactory
                .select(duesInfo.deposit.sum())
                .from(duesInfo)
                .where(duesInfo.depositAT.goe(start),
                        duesInfo.depositAT.loe(end))
                .fetchOne();

    }

    /**
     * 현재 납입월 정보를 전부 가져옴.
     * @return
     */
    public List<LocalDate> getduesList(){
        LocalDate localDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return this.queryFactory
                .select(duesInfo.date)
                .distinct()
                .from(duesInfo)
                .where(duesInfo.date.lt(localDate))
                .fetch();
    }


    /**
     * 납입한 회원 정보를 조회
     * 
     * @return
     */
    public List<PaymentInfo> getPaymentMember() {
        LocalDate localDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return this.queryFactory
                .select(new QPaymentInfo(memberInfo.id,
                        duesInfo.date))
                .from(duesInfo)
                .leftJoin(duesInfo.memberInfo, memberInfo)
                .where(duesInfo.date.lt(localDate))
                .fetch();
    }
}
