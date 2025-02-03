package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        // 현재 날짜 nowDate, 현재 시간 nowTime에 저장
        LocalDateTime writeDate = LocalDateTime.now();
        LocalDateTime modifyDate = writeDate;

        // 요청받은 데이터로 schedule 객체 생성
        Schedule schedule = new Schedule(dto.getPw(), dto.getWriter(), dto.getToDo(), writeDate, modifyDate);
        // DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public List<ScheduleResponseDto> findScheduleByWriter(String writer) {
        return scheduleRepository.findScheduleByWriter(writer);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleByModifyDate(LocalDateTime modifyDate) {
        return scheduleRepository.findScheduleByModifyDate(modifyDate);
    }

    @Override
    public ScheduleResponseDto findScheduleById(long id) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(long id, String writer, String toDo, String pw) {
        if (writer == null || toDo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, writer, toDo, pw, LocalDateTime.now());

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateWriter(long id, String writer, String toDo, String pw) {
        if (writer == null || toDo != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        int updatedRow = scheduleRepository.updateWriter(id, writer, pw, LocalDateTime.now());

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateToDo(long id, String writer, String toDo, String pw) {
        if (toDo == null || writer != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        int updatedRow = scheduleRepository.updateToDo(id, toDo, pw, LocalDateTime.now());

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(long id, String pw) {
        int deletedRow = scheduleRepository.deleteSchedule(id, pw);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
