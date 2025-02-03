package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("pw", schedule.getPw());
        parameters.put("writer", schedule.getWriter());
        parameters.put("toDo", schedule.getToDo());
        parameters.put("writeDate", schedule.getWriteDate());
        parameters.put("modifyDate", schedule.getModifyDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        LocalDate writeDate = LocalDate.of(schedule.getWriteDate().getYear(), schedule.getWriteDate().getMonthValue(), schedule.getWriteDate().getDayOfMonth());
        LocalDate modifyDate = LocalDate.of(schedule.getModifyDate().getYear(), schedule.getModifyDate().getMonthValue(), schedule.getModifyDate().getDayOfMonth());

        return new ScheduleResponseDto(key.longValue(), schedule.getWriter(), schedule.getToDo(), writeDate, modifyDate);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule order by modifyDate desc", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findScheduleByWriter(String writer) {
        return jdbcTemplate.query("select * from schedule where writer = ?", scheduleRowMapper(), writer);
    }

    @Override
    public List<ScheduleResponseDto> findScheduleByModifyDate(LocalDateTime modifyDate) { //"2025-02-03 00:00:00"
//        return jdbcTemplate.query("select * from schedule where DATE(modifyDate)= ? order by modifyDate desc", scheduleRowMapper(), modifyDate);
        return jdbcTemplate.query("select * from schedule where date_format(modifyDate, '%Y-%m-%d') between ? and ? + 1 order by modifyDate desc", scheduleRowMapper(), modifyDate, modifyDate);
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateSchedule(long id, String writer, String toDo, String pw, LocalDateTime modifyDate) {
        return jdbcTemplate.update("update schedule set writer = ?, toDo = ?, modifyDate = ? where id = ? && pw = ?", writer, toDo, modifyDate, id, pw);
    }

    @Override
    public int updateWriter(long id, String writer, String pw, LocalDateTime modifyDate) {
        return jdbcTemplate.update("update schedule set writer = ?, modifyDate = ? where id = ? && pw = ?", writer, modifyDate, id, pw);
    }

    @Override
    public int updateToDo(long id, String toDo, String pw, LocalDateTime modifyDate) {
        return jdbcTemplate.update("update schedule set toDo = ?, modifyDate = ? where id = ? && pw = ?", toDo, modifyDate, id, pw);
    }

    @Override
    public int deleteSchedule(long id, String pw) {
        return jdbcTemplate.update("delete from schedule where id = ? && pw = ?", id, pw);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("writer"),
                        rs.getString("toDo"),
                        rs.getTimestamp("writeDate").toLocalDateTime().toLocalDate(),
                        rs.getTimestamp("modifyDate").toLocalDateTime().toLocalDate()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("pw"),
                        rs.getString("writer"),
                        rs.getString("toDo"),
                        rs.getTimestamp("writeDate").toLocalDateTime(),
                        rs.getTimestamp("modifyDate").toLocalDateTime()
                );
            }
        };
    }
}
