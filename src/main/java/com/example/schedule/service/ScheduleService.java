package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findScheduleByWriter(String writer);
    List<ScheduleResponseDto> findScheduleByModifyDate(LocalDateTime modifyDate);
    ScheduleResponseDto findScheduleById(long id);
    ScheduleResponseDto updateSchedule(long id, String writer, String toDo, String pw);
    ScheduleResponseDto updateWriter(long id, String writer, String toDo, String pw);
    ScheduleResponseDto updateToDo(long id, String writer, String toDo, String pw);
    void deleteSchedule(long id, String pw);
}