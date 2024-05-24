package org.fullstack4.hammerteen.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {

    private int ScheduleIdx;

   @NotEmpty
    private String userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;


    private LocalDateTime startDate;


    private LocalDateTime endDate;


    private LocalDateTime notificationTime;
}
