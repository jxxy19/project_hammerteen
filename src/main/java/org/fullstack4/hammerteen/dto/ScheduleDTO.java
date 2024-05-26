package org.fullstack4.hammerteen.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
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
public class ScheduleDTO {
    private int ScheduleIdx;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private LocalDateTime startDate;
    private String startDateTimeToString;
    private String startDateToString;
    private LocalDateTime endDate;
    private String endDateTimeToString;
    private String endDateToString;
    private LocalDateTime notificationTime;
    public void setDateTimeToString() {
        this.startDateToString = this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.startDateTimeToString = this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.endDateToString = this.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endDateTimeToString = this.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
