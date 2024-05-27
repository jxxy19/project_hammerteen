package org.fullstack4.hammerteen.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyLectureDTO {
    private int myLectureIdx;
    @NotNull
    private int lectureIdx;
    @NotEmpty
    private String userId;
    private String status;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    // 데이터 조회용
    private MemberDTO memberDTO;
    private LectureDTO lectureDTO;
    private LectureScoreDTO lectureScoreDTO;
    private String regDateToString;
    private String modifyDateToString;

    public void setDateToString() {
        this.regDateToString = this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(modifyDate != null) {
            this.modifyDateToString = this.modifyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
