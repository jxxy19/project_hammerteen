package org.fullstack4.hammerteen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Data
public class LPageResponseDTO<E> {
    private int total_count;
    private int page;
    private int page_size;
    private int total_page;
    private int page_skip_count;
    private int page_block_size;
    private int page_block_start;
    private int page_block_end;

    private boolean prev_page_flag;
    private boolean next_page_flag;

    private String title;
    private String teacherName;
    private String search_word;
    private String categoryIdx;
    private String linkParams;
    private String orderby;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<E> dtoList;

    LPageResponseDTO(){}

    @Builder(builderMethodName = "withAll")
    public LPageResponseDTO(LPageRequestDTO lpageRequestDTO, List<E> dtoList, int total_count){
        log.info("=================================");
        log.info("PageResponseDTO Start");
        this.total_count = total_count;
        this.page = lpageRequestDTO.getPage();
        this.page_size=lpageRequestDTO.getPage_size();
        this.total_page = (this.total_count > 0? (int)Math.ceil(this.total_count/(double)this.page_size):1);
        this.page_skip_count = (this.page-1)*this.page_size;
        this.page_block_size =lpageRequestDTO.getPage_block_size();
        this.page_block_start =lpageRequestDTO.getPage_block_start();
        this.page_block_end =lpageRequestDTO.getPage_block_end();
        setPage_block_start();
        setPage_block_end();
        this.prev_page_flag = (this.page_block_start > 1);
        this.next_page_flag = (this.total_page >this.page_block_end);
        this.dtoList = dtoList;
        this.orderby = lpageRequestDTO.getOrderby();
        this.title = lpageRequestDTO.getTitle();
        this.teacherName = lpageRequestDTO.getTeacherName();
        this.categoryIdx = lpageRequestDTO.getCategoryIdx();
        this.startDate = lpageRequestDTO.getStartDate();
        this.endDate = lpageRequestDTO.getEndDate();
        this.search_word = lpageRequestDTO.getSearch_word();
        this.linkParams = lpageRequestDTO.getLinkParams();

        log.info("lpageRequestDTO : " + lpageRequestDTO);
        log.info("dtoList : " + dtoList);
        log.info("total_count : " + total_count);
        log.info("prev_page_flag : " + prev_page_flag);
        log.info("next_page_flag : " + next_page_flag);
        log.info("linkParams : " + linkParams);
        log.info("PageResponseDTO END");
        log.info("=================================");
    }
    public int getTotal_page(){ return (this.total_count>0?(int)Math.ceil(this.total_count/(double)this.page_size):1);}
    public int getPage_skip_count(){ return (this.page-1)*this.page_size;}

    public void setPage_block_start(){this.page_block_start = (
            (int)Math.ceil(this.page/(double)this.page_block_size)-1) *this.page_block_size +1;}

    public void setPage_block_end(){
        this.page_block_end = (int)(Math.ceil(this.page/(double)this.page_block_size)*this.page_block_size);
        this.page_block_end = (this.page_block_end > this.total_page ? this.total_page :this.page_block_end);
    }

}
