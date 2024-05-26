package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleServiceIf {
    int registSchedule(ScheduleDTO scheduleDTO);
    int modifySchedule(ScheduleDTO scheduleDTO);
    void deleteSchedule(int scheduleIdx);
    public List<ScheduleDTO> list(String userId, String year, String month);
    ScheduleDTO view(int scheduleIdx);
}
