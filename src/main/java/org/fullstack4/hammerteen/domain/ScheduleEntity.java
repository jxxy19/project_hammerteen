package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_schedule")
public class ScheduleEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int ScheduleIdx;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false) //스케줄 설명
    private String description;

    @Column(nullable = false) //스케줄 시작일
    private LocalDateTime startDate;

    @Column(nullable = false) //스케줄 종료일
    private LocalDateTime endDate;

    @Column(nullable = true) //스케줄 알림 (알림기능 넣을 시)
    private LocalDateTime notificationTime;

    public void modify(String title , String description ,LocalDateTime startDate ,LocalDateTime endDate ,LocalDateTime notificationTime){

        this.title=title;
        this.description=description;
        this.startDate=startDate;
        this.endDate=endDate;
        this.notificationTime=notificationTime;

    }


}

