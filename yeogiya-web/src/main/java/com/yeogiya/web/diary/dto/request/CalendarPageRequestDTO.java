package com.yeogiya.web.diary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalendarPageRequestDTO {
    private int year;
    private int month;
    private int day;
}
