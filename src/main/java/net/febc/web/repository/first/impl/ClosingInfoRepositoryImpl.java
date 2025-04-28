package net.febc.web.repository.first.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.febc.web.dto.req.closing.ReqInsertDto;
import net.febc.web.dto.req.closing.ReqListDto;
import net.febc.web.dto.res.closing.QResListDto;
import net.febc.web.dto.res.closing.ResListDto;
import net.febc.web.dto.res.closing.detail.DetailInfo;
import net.febc.web.dto.res.closing.detail.QDetailInfo;
import net.febc.web.repository.first.entity.account.ClosingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static net.febc.web.repository.first.entity.account.QClosingInfo.closingInfo;
import static net.febc.web.repository.first.entity.account.QExpensesInfo.expensesInfo;

@Repository
@RequiredArgsConstructor
public class ClosingInfoRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    /**
     * 멤버 정보 조회
     * @param reqListDto 조회 정보
     * @return
     */
    public Page<ResListDto> getClosingList(ReqListDto reqListDto) {
        PageRequest pageRequest = PageRequest.of(reqListDto.getPage(), reqListDto.getOffset());
        // 검색
        List<ResListDto> resListDtos = queryFactory.select(new QResListDto(closingInfo.id,
                        closingInfo.title,
                        closingInfo.startDate,
                        closingInfo.endDate,
                        closingInfo.deleteFlg))
                .from(closingInfo)
                .where(closingInfo.id.ne(1L))
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset())
                .orderBy(closingInfo.startDate.asc(), closingInfo.id.asc())
                .fetch();

        Long totalCnt = queryFactory.select(closingInfo.count())
                .from(closingInfo)
                .where(closingInfo.id.ne(1L))
                .fetchOne();

        return new PageImpl<>(resListDtos, pageRequest, totalCnt);
    }

    /**
     * 마지막 결산 정보를 가져옴
     * 결산은 중복이 안된다는 전제로.
     * @return
     */
    public ClosingInfo lastClosingInfo() {
        return this.queryFactory
                .select(closingInfo)
                .from(closingInfo)
                .where(closingInfo.deleteFlg.eq(Boolean.FALSE))
                .orderBy(closingInfo.startDate.desc(), closingInfo.endDate.desc())
                .limit(1)
                .fetchOne();
    }

    /**
     * 지정 결산 정보를 가져옴
     *
     * @param id 결산 고유 ID
     * @return
     */
    public ClosingInfo getClosingInfo(Long id) {
        return this.queryFactory
                .select(closingInfo)
                .from(closingInfo)
                .where(closingInfo.deleteFlg.eq(Boolean.FALSE),
                        closingInfo.id.eq(id))
                .fetchOne();
    }

    /**
     * 지정 정산일의 수입과지출 정보를 가져옴
     * @return
     */
    public List<DetailInfo> getClosingData(LocalDate startDate, LocalDate endDate) {
        return this.queryFactory
                .select(new QDetailInfo(expensesInfo.expensType,
                        expensesInfo.amount,
                        expensesInfo.memo,
                        expensesInfo.type))
                .from(expensesInfo)
                .where(expensesInfo.date.goe(startDate),
                        expensesInfo.date.goe(endDate))
                .fetch();
    }
}
