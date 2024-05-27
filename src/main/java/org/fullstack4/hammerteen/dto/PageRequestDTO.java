package org.fullstack4.hammerteen.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    @Builder.Default
    private int total_count = 0;
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int page_size = 10;
    @Builder.Default
    private int total_page = 1;
    @Builder.Default
    private int page_skip_count = 0;
    @Builder.Default
    private int page_block_size = 10;
    @Builder.Default
    private int page_block_start = 1;
    @Builder.Default
    private int page_block_end = 10;

    private String search_type; //t=제목 c=내용 u=아이디
    private String[] search_types;
    private String search_word;
    private String location;
    private String category1;
    private String category2;
    private String linkParams;
    private String orderby;
    private String userId;

    private LocalDate reg_date_start;
    private LocalDate reg_date_end;

    public void setTotal_count(int total_count){this.total_count=total_count; }
    public int getPage_skip_count() {return (this.page-1)*this.page_size; }

    public String[] getSearch_types(){
        if(search_type == null || search_type.isEmpty()){
            return null;
        }
        return search_type.split(",");
    }
    public String getOrderby(){
        if(orderby == null || orderby.isEmpty()){
            return null;
        }
        return orderby.split(",")[0];
    }
    public PageRequest getPageable(String...props){
        return PageRequest.of(this.page-1,this.page_size, Sort.by(props).descending());
    }
    public String getLinkParams(){
        if(this.linkParams == null || linkParams.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("&page_size="+this.page_size);

            if(search_type !=null && !search_type.isEmpty()){
                sb.append("&search_type="+this.search_type);
            }
            if(search_word!=null && !search_word.isEmpty()){
                try {
                    sb.append("&search_word="+ URLEncoder.encode(search_word,"UTF-8"));
                } catch (UnsupportedEncodingException e) {

                }
            }
            if(orderby !=null && !orderby.isEmpty())
            {
                sb.append("&orderby=" + getOrderby());
            }
            if(reg_date_start !=null){
                sb.append("&reg_date_start="+this.reg_date_start);
            }
            if(reg_date_end !=null){
                sb.append("&reg_date_end="+this.reg_date_end);
            }
            if(location !=null){
                sb.append("&location="+this.location);
            }
            linkParams = sb.toString();
        }
        return linkParams;
    }


}
