package net.febc.web.repository.first.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.febc.web.dto.req.account.ReqListDto;
import net.febc.web.dto.res.account.QResListDto;
import net.febc.web.dto.res.account.ResListDto;
import net.febc.web.repository.comm.ExpensensEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.febc.web.repository.first.entity.account.QExpensesInfo.expensesInfo;

@Repository
@RequiredArgsConstructor
public class AccountInfoRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    /**
     * 멤버 정보 조회
     * @param reqListDto 조회 정보
     * @return
     */
    public Page<ResListDto> getAccountInfoList(ReqListDto reqListDto) {
        PageRequest pageRequest = PageRequest.of(reqListDto.getPage(), reqListDto.getOffset());
        // 검색
        List<ResListDto> resListDtos = queryFactory.select(new QResListDto(expensesInfo.id,
                        expensesInfo.type,
                        expensesInfo.expensType,
                        expensesInfo.date,
                        expensesInfo.amount))
                .from(expensesInfo)
                .where(isWhere(reqListDto))
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset())
                .orderBy(expensesInfo.date.asc(), expensesInfo.id.asc())
                .fetch();

        Long totalCnt = queryFactory.select(expensesInfo.count())
                .from(expensesInfo)
                .where(isWhere(reqListDto))
                .fetchOne();

        return new PageImpl<>(resListDtos, pageRequest, totalCnt);

     }

    /**
     * 조건
     * @param dto
     * @return
     */
    private BooleanExpression isWhere(ReqListDto dto) {

        // 검색 기간 (설정이 없으면 당월 데이터 감색)
        BooleanExpression expression = expensesInfo.date.goe(dto.getStartDate()).and(expensesInfo.date.loe(dto.getEndDate()));

        // 타입
        if (StringUtils.isNotBlank(dto.getType())) {
            expression.and(expensesInfo.type.eq(dto.getType())) ;
        }
        // 지출 타입
        if (StringUtils.isNotBlank(dto.getExpensensType())) {
            expression.and(expensesInfo.expensType.eq(ExpensensEnum.valueOf(dto.getExpensensType())));
        }
        // 메모
        if (StringUtils.isNotBlank(dto.getSearchData())) {
            expression.and(expensesInfo.memo.contains(dto.getSearchData()));
        }


        return expression;
    }
}
