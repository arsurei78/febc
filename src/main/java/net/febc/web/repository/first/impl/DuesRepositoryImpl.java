package net.febc.web.repository.first.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.febc.web.dto.req.dues.ReqListDto;
import net.febc.web.dto.res.dues.QResListDto;
import net.febc.web.dto.res.dues.ResListDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.febc.web.repository.first.entity.account.QDuesInfo.duesInfo;
import static net.febc.web.repository.first.entity.account.QExpensesInfo.expensesInfo;
import static net.febc.web.repository.first.entity.member.QMemberInfo.memberInfo;


@Repository
@RequiredArgsConstructor
public class DuesRepositoryImpl {

    private final JPAQueryFactory queryFactory;

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
                        duesInfo.year,
                        duesInfo.month,
                        memberInfo.generation,
                        memberInfo.dues,
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

        BooleanExpression expression = expensesInfo.date.goe(dto.getStartDate()).and(expensesInfo.date.loe(dto.getEndDate()));
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
}
