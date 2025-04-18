package net.febc.web.service.impl;

import lombok.RequiredArgsConstructor;
import net.febc.cmmn.constant.Constants;
import net.febc.cmmn.web.BaseResponse;
import net.febc.web.dto.req.dues.ReqListDto;
import net.febc.web.dto.res.BasePaginationDto;
import net.febc.web.dto.res.dues.ResListDto;
import net.febc.web.repository.first.impl.DuesRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuesServiceImpl {

    private final DuesRepositoryImpl repositoryImpl;
    /**
     * 조회
     * @param dto
     * @return
     */
    public BaseResponse<BasePaginationDto> getList(ReqListDto dto) {

        if (dto == null) {
            dto = new ReqListDto();
        }
        Page<ResListDto> accountInfoList = repositoryImpl.getDuesInfoList(dto);
        // 멤버 리스트 반환
        return new BaseResponse<>(new BasePaginationDto<>(accountInfoList, dto.getOffset(), Constants.PAGE_BLOCK_SIZE));
    }
}
