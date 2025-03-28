package net.febc.web.repository.first.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.febc.web.dto.req.member.ReqListDto;
import net.febc.web.dto.res.member.QResListDto;
import net.febc.web.dto.res.member.ResListDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.febc.web.repository.first.entity.member.QMemberInfo.memberInfo;

@Repository
@RequiredArgsConstructor
public class MemberInfoRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    /**
     * 멤버 정보 조회
     * @param reqListDto 조회 정보
     * @return
     */
    public Page<ResListDto> getMemberInfoList(ReqListDto reqListDto) {
        PageRequest pageRequest = PageRequest.of(reqListDto.getPage(), reqListDto.getOffset());

        List<ResListDto> resListDtos = queryFactory.select(new QResListDto(memberInfo.id,
                        memberInfo.name,
                        memberInfo.generation,
                        memberInfo.state))
                .from(memberInfo)
                .where(isWhere(reqListDto.getSearchData(), reqListDto.getState()))
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset())
                .orderBy(memberInfo.generation.asc(), memberInfo.name.asc())
                .fetch();

        Long totalCnt = queryFactory.select(memberInfo.count())
                .from(memberInfo)
                .where(isWhere(reqListDto.getSearchData(), reqListDto.getState()))
                .fetchOne();

        return new PageImpl<>(resListDtos, pageRequest, totalCnt);
    }

    /**
     * 조건
     * @param name
     * @return
     */
    private BooleanExpression isWhere(String name, String state) {
        BooleanExpression expression = null;
        // 이름
        if (StringUtils.isNotBlank(name)) {
            expression = memberInfo.name.contains(name);
        }
        // 상태
        if (StringUtils.isNotBlank(state)) {
            if (expression == null) {
                expression = memberInfo.state.eq(this.isState(state));
            } else {
                expression.and(memberInfo.state.eq(this.isState(state)));
            }
        }

        return expression;
    }

    /**
     * 상태 확인
     * @param state
     * @return
     */
    private Boolean isState (String state) {
        if("Y".equals(state)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
