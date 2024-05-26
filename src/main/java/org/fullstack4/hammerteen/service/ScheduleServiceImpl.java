package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.ScheduleEntity;
import org.fullstack4.hammerteen.dto.ScheduleDTO;
import org.fullstack4.hammerteen.repository.ScheduleRepository;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleServiceIf{
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Override
    public int registSchedule(ScheduleDTO scheduleDTO) {
        int scheduleIdx = scheduleRepository.save(modelMapper.map(scheduleDTO, ScheduleEntity.class)).getScheduleIdx();
        return scheduleIdx;
    }

    @Override
    public int modifySchedule(ScheduleDTO scheduleDTO) {
        int scheduleIdx = scheduleRepository.save(modelMapper.map(scheduleDTO, ScheduleEntity.class)).getScheduleIdx();
        return scheduleIdx;
    }

    @Override
    public void deleteSchedule(int scheduleIdx) {
        scheduleRepository.deleteById(scheduleIdx);
    }

    @Override
    public List<ScheduleDTO> list(String userId, String year, String month) {
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.findAllByUserIdAndStartDateMonthOrEndDateMonth(userId, CommonUtil.parseInt(year), CommonUtil.parseInt(month));
        List<ScheduleDTO> scheduleDTOList = null;
        if(scheduleEntityList != null) {
            scheduleDTOList = scheduleEntityList.stream().map(vo->modelMapper.map(vo, ScheduleDTO.class)).collect(Collectors.toList());
            scheduleDTOList.forEach(dto->dto.setDateTimeToString());
        }
        return scheduleDTOList;
    }

    @Override
    public ScheduleDTO view(int scheduleIdx) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleIdx).get();
        ScheduleDTO scheduleDTO = null;
        if(scheduleEntity != null) {
            scheduleDTO = modelMapper.map(scheduleEntity, ScheduleDTO.class);
            scheduleDTO.setDateTimeToString();
        }
        return scheduleDTO;
    }
}
