package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findScheduleByWriter(String writer);
    List<ScheduleResponseDto> findScheduleByModifyDate(LocalDateTime modifyDate);
    Schedule findScheduleByIdOrElseThrow(long id);
    int updateSchedule(long id, String writer, String toDo, String pw, LocalDateTime modifyDate);
    int updateWriter(long id, String writer, String pw, LocalDateTime modifyDate);
    int updateToDo(long id, String toDo, String pw, LocalDateTime modifyDate);
    int deleteSchedule(long id, String pw);


}
