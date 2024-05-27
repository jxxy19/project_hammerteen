package org.fullstack4.hammerteen.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureDetailDTO {
    private int lectureDetailIdx;
    private int lectureIdx;
    private String detailTitle;
    private String VideoDirectory;
    private String VideoFile;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;


}
