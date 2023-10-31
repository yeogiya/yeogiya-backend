package com.yeogiya.web.util;

import com.yeogiya.web.diary.dto.request.CalendarPageRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DateUtils {
   static public LocalDateTime startDateTime(LocalDate localDate){
        return LocalDateTime.of(localDate, LocalTime.of(0,0,0));
    }

    static public LocalDateTime endDateTime(LocalDate localDate){
        return LocalDateTime.of(localDate, LocalTime.of(23,59,59));
    }

    static public LocalDate startDate(CalendarPageRequestDTO calendarPageRequestDTO){
        return LocalDate.of(calendarPageRequestDTO.getYear(),calendarPageRequestDTO.getMonth(), 1);
    }

    static public LocalDate getDate(CalendarPageRequestDTO calendarPageRequestDTO){
        return LocalDate.of(calendarPageRequestDTO.getYear(),calendarPageRequestDTO.getMonth(), calendarPageRequestDTO.getDay());
    }
}
