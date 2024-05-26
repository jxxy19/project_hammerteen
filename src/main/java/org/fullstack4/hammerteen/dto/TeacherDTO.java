package org.fullstack4.hammerteen.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDTO {
    @NotNull
    private int teacherIdx;

    @NotEmpty
    private String categoryName;
    @NotEmpty
    private String education;

    @NotEmpty
    private String name;

    private String profile;
    private String writing;

    private LocalDateTime regDate;


    private LocalDateTime modifyDate;

    //회원권한
    @Builder.Default
    private String role="user";
}
