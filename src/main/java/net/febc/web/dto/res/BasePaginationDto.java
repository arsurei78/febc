package net.febc.web.dto.res;

import lombok.Getter;
import lombok.Setter;
import net.febc.cmmn.constant.Constants;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Getter
@Setter
public class BasePaginationDto<T> {

    /** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
    private int pageSize = 10;

    /** 2. 페이징된 버튼의 블럭당 최대 개수 **/
    private int blockSize = 10;

    /** 3. 현재 페이지 **/
    private int page = 1;

    /** 4. 현재 블럭 **/
    private int block = 1;

    /** 5. 총 게시글 수 **/
    private int totalListCnt;

    /** 6. 총 페이지 수 **/
    private int totalPageCnt;

    /** 7. 총 블럭 수 **/
    private int totalBlockCnt;

    /** 8. 블럭 시작 페이지 **/
    private int startPage = 1;

    /** 9. 블럭 마지막 페이지 **/
    private int endPage = 1;

    /** 10. DB 접근 시작 index **/
    private int startIndex = 0;

    /** 11. 이전 블럭의 마지막 페이지 **/
    private int prevBlock;

    /** 12. 다음 블럭의 시작 페이지 **/
    private int nextBlock;

    private List<T> contents;

    /**
     * 페이지 리스트
     * @param page 검색한 페이지 정보
     * @param pageSize 페이지 당 보여지는 게시글의 최대 개수
     * @param blockSize 페이징된 버튼의 블럭당 최대 개수
     */
    public BasePaginationDto(Page<T> page, Integer pageSize, Integer blockSize) {

        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }
        if (ObjectUtils.isEmpty(blockSize)) {
            blockSize = Constants.DEFAULT_BLOCK_SIZE;
        }
        // 총 게시물 수
        setTotalListCnt((int)page.getTotalElements());
        // 현재 페이지 (page의 경우, 0부터 시작)
        setPage(page.getNumber() + 1);
        // 전체 페이지 수를 계산
        setTotalPageCnt((int)Math.ceil(((this.totalListCnt) * 1.0) / pageSize));
        // 현재 블록
        setBlock((int) Math.ceil((this.page * 1.0)/blockSize));
        // 블록 시작 페이지
        setStartPage((this.block - 1) * blockSize + 1);
        // 블록의 마지막 페이지
        setEndPage(this.startPage + blockSize - 1);
        // 블록의 마지막 페이지가 최대 페이지보다 크면
        if(this.endPage > this.totalPageCnt){
            setEndPage(this.totalPageCnt);
        }
        // 시작 페이지가 마지막 페이지보다 크면 시작 페이지를 마지막 페이지로
        if (this.startPage > this.endPage ) {
            setEndPage(this.startPage);
        }
        // 이전 블록[<]의 값
        setPrevBlock((block * blockSize) - blockSize);
        // 이전 블록이 1보다 작으면 1로 고정
        if(this.prevBlock < 1) {
            setPrevBlock(1);
        }
        // 다음 버튼 클릭(>)시 이동하는 블록
        setNextBlock((this.block * blockSize) + 1);
        // 다음블록[>]의 사이즈가 전체 페이지 사이즈보다 크면 다음블록[>]의 값을 전체 사이즈로
        if(this.nextBlock > this.totalPageCnt) {
            setNextBlock(this.totalPageCnt);
        }
        // DB접근시 Index값 (DB는 0부터 시작)
        setStartIndex((this.page-1)*pageSize);

        setContents(page.getContent());
    }
}
