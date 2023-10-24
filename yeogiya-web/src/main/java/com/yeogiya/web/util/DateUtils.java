package com.yeogiya.web.util;

import com.yeogiya.web.diary.dto.request.CalendarPageRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DateUtils {
    public LocalDateTime startDateTime(LocalDate localDate){
        return LocalDateTime.of(localDate, LocalTime.of(0,0,0));
    }

    public LocalDateTime endDateTime(LocalDate localDate){
        return LocalDateTime.of(localDate, LocalTime.of(23,59,59));
    }

    public LocalDate startDate(CalendarPageRequestDTO calendarPageRequestDTO){
        return LocalDate.of(calendarPageRequestDTO.getYear(),calendarPageRequestDTO.getMonth(), 1);
    }

    public LocalDate getDate(CalendarPageRequestDTO calendarPageRequestDTO){
        return LocalDate.of(calendarPageRequestDTO.getYear(),calendarPageRequestDTO.getMonth(), calendarPageRequestDTO.getDay());
    }
}
