package net.febc.web.service.impl;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.web.dto.req.closing.ReqInsertDto;
import net.febc.web.dto.req.closing.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.closing.ResDetailDto;
import net.febc.web.dto.res.closing.ResListDto;
import net.febc.web.dto.res.closing.detail.DetailInfo;
import net.febc.web.repository.first.entity.account.ClosingInfo;
import net.febc.web.repository.first.impl.ClosingInfoRepositoryImpl;
import net.febc.web.repository.first.impl.DuesRepositoryImpl;
import net.febc.web.repository.first.write.ClosingInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ClosingServiceImpl {

    private final ClosingInfoRepositoryImpl repositoryImpl;
    private final DuesRepositoryImpl duesRepositoryImpl;
    private final ClosingInfoRepository writeRepository;

    
    /**
     * 리스트
     * @param reqListDto
     * @return
     */
    public BasePaginationDto getList(ReqListDto reqListDto) {
        Page<ResListDto> closingList = repositoryImpl.getClosingList(reqListDto);
        // 멤버 리스트 반환
        return new BasePaginationDto<>(closingList, reqListDto.getOffset(), Constants.PAGE_BLOCK_SIZE);
    }

    /**
     * 신규 결산 정보를 작성
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    public ResDetailDto makeViewClosing(ReqInsertDto dto) {
        // 전 정산 잔액
        ClosingInfo closingInfo = repositoryImpl.lastClosingInfo();
        // 납입 회비
        Integer totalDues = duesRepositoryImpl.getTotalDues(dto.getStartDate(), dto.getEndDate());
        // 수입/지출 정보를조회
        List<DetailInfo> closingDataList = repositoryImpl.getClosingData(dto.getStartDate(), dto.getEndDate());
        if (totalDues != null) {
            closingDataList.add(new DetailInfo(totalDues));
        }
        return new ResDetailDto(closingInfo, closingDataList, dto.getStartDate(), dto.getEndDate());
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public ResDetailDto closingDetail(Long id) {
        // 전 정산 잔액
        ClosingInfo closingInfo = repositoryImpl.getClosingInfo(id);
        // 납입 회비
        Integer totalDues = duesRepositoryImpl.getTotalDues(closingInfo.getStartDate(), closingInfo.getEndDate());
        // 수입/지출 정보를조회
        List<DetailInfo> closingDataList = repositoryImpl.getClosingData(closingInfo.getStartDate(), closingInfo.getEndDate());
        if (totalDues != null) {
            closingDataList.add(new DetailInfo(totalDues));
        }
        return new ResDetailDto(closingInfo, closingDataList, closingInfo.getStartDate(), closingInfo.getEndDate());
    }

    @Transactional
    public Long makeClosing(ReqInsertDto dto) {

        // 전 정산 잔액
        ClosingInfo lastClosingInfo = repositoryImpl.lastClosingInfo();
        // 납입 회비
        Integer totalDues = duesRepositoryImpl.getTotalDues(dto.getStartDate(), dto.getEndDate());
        // 수입/지출 정보를조회
        List<DetailInfo> closingDataList = repositoryImpl.getClosingData(dto.getStartDate(), dto.getEndDate());
        if (totalDues != null) {
            closingDataList.add(new DetailInfo(totalDues));
        }
        ClosingInfo entity = dto.toEntity(lastClosingInfo, closingDataList, dto.getStartDate(), dto.getEndDate());

        ClosingInfo save = writeRepository.save(entity);

        return save.getId();
    }

    @Transactional
    public void deleteClosing(Long id) {
        ClosingInfo closingInfo = writeRepository.findById(id).orElse(null);
        closingInfo.setDeleteFlg(Boolean.TRUE);
    }
}
