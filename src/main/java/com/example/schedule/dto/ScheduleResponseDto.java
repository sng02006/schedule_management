package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String writer;
    private String toDo;
    private LocalDate writeDate;
    private LocalDate modifyDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.writer = schedule.getWriter();
        this.toDo = schedule.getToDo();
        this.writeDate = LocalDate.of(schedule.getWriteDate().getYear(), schedule.getWriteDate().getMonthValue(), schedule.getWriteDate().getDayOfMonth());
        this.modifyDate = LocalDate.of(schedule.getModifyDate().getYear(), schedule.getModifyDate().getMonthValue(), schedule.getModifyDate().getDayOfMonth());
    }
}
