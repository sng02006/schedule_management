package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String pw;
    private String writer;
    private String toDo;
    private LocalDateTime writeDate;
    private LocalDateTime modifyDate;

    public Schedule(String pw, String writer, String toDo, LocalDateTime writeDate, LocalDateTime modifyDate) {
        this.pw = pw;
        this.writer = writer;
        this.toDo = toDo;
        this.writeDate = writeDate;
        this.modifyDate = modifyDate;
    }
}
