package com.yakx.web.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomReservationSmsMessage {

    private String teacherName;

    private String roomName;

    private LocalDate dateTime;

    private LocalTime startTime;

    private LocalTime endTime;

    private String password;
}
