package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/schedules") // Prefix
public class ScheduleController {
    private final ScheduleService scheduleService;


    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule (@RequestBody ScheduleRequestDto dto) {
        // Service Layer 호출, 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleService.findAllSchedules();
    }

    @GetMapping("/findWriter/{writer}")
    public List<ScheduleResponseDto> findScheduleByWriter(@PathVariable String writer) {
        return scheduleService.findScheduleByWriter(writer);
    }

    @GetMapping("/findDate/{date}")
    public List<ScheduleResponseDto> findScheduleByModifyDate(@PathVariable String date) {
        LocalDate modifyDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return scheduleService.findScheduleByModifyDate(LocalDateTime.of(modifyDate, LocalTime.MIN));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule (
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getWriter(), dto.getToDo(), dto.getPw()), HttpStatus.OK);
    }

    @PatchMapping("/writer/{id}")
    public ResponseEntity<ScheduleResponseDto> updateWriter (
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateWriter(id, dto.getWriter(), dto.getToDo(), dto.getPw()), HttpStatus.OK);
    }

    @PatchMapping("/toDo/{id}")
    public ResponseEntity<ScheduleResponseDto> updateTodo(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateToDo(id, dto.getWriter(), dto.getToDo(), dto.getPw()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        scheduleService.deleteSchedule(id, dto.getPw());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}