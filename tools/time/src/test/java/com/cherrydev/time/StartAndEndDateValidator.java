package com.cherrydev.time;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;


public class StartAndEndDateValidator {

    @ParameterizedTest
    @MethodSource("provideDates")
    public void checkStartDate(LocalDate nowDate, LocalTime nowTime, LocalTime startTime, LocalTime endTime, LocalDateTime expected) {

        LocalDate startDate = CommonTimeUtils.getStartDate(startTime, endTime, nowTime, nowDate);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        Assert.assertEquals(expected, startDateTime);
    }


    private static Stream<Arguments> provideDates() {
        return Stream.of(
                //should return yesterday because current time is between start and end time, with end today, start yesterday, now.day also today.
                Arguments.of(
                        LocalDate.of(2021, 6, 4), LocalTime.of(0, 30), //now
                        LocalTime.of(22, 0), LocalTime.of(6, 0),  //start, end
                        LocalDateTime.of(2021, 6, 3, 22, 0) //expected startDateTime
                ),

                // should return today because current time is between start and end and day for start and and end is also today
                Arguments.of(
                        LocalDate.of(2021, 6, 20), LocalTime.of(15, 0), //now
                        LocalTime.of(8, 30), LocalTime.of(17, 30),  //start, end
                        LocalDateTime.of(2021, 6, 20, 8, 30) //expected startDateTime
                ),

                //should return next day as start and end time have passed
                Arguments.of(
                        LocalDate.of(2021, 6, 4), LocalTime.of(10, 0), //now
                        LocalTime.of(9, 0), LocalTime.of(9, 30),  //start, end
                        LocalDateTime.of(2021, 6, 5, 9, 0) //expected startDateTime
                ),

                //should return today because startTime is in the future
                Arguments.of(
                        LocalDate.of(2021, 6, 4), LocalTime.of(18, 0), //now
                        LocalTime.of(18, 30), LocalTime.of(19, 30),  //start, end
                        LocalDateTime.of(2021, 6, 4, 18, 30) //expected startDateTime
                ),

                //should return today because startTime is in the future
                Arguments.of(
                        LocalDate.of(2021, 6, 4), LocalTime.of(10, 0), //now
                        LocalTime.of(9, 0), LocalTime.of(17, 0),  //start, end
                        LocalDateTime.of(2021, 6, 4, 9, 0) //expected startDateTime
                ),

                //should return monday as first closest start day
                Arguments.of(
                        LocalDate.of(2021, 11, 6), LocalTime.of(10, 0), //now
                        LocalTime.of(8, 30), LocalTime.of(17, 30),  //start, end
                        LocalDateTime.of(2021, 11, 6, 8, 30) //expected startDateTime
                )

        );
    }


    @ParameterizedTest
    @MethodSource("provideDatesForEndCheck")
    public void checkEndDate(LocalDate startDate, LocalTime startTime, LocalTime endTime, LocalDateTime expected) {

        LocalDate endDate = startDate;
        endDate = CommonTimeUtils.adjustDate(endDate, endTime, startTime);  //so the end is in the future if we have eg start 22, end 04
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        Assert.assertEquals(expected, endDateTime);
    }


    private static Stream<Arguments> provideDatesForEndCheck() {
        return Stream.of(
                //should return yesterday because current time is between start and end time, with end today, start yesterday, now.day also today.
                Arguments.of(
                        LocalDate.of(2021, 6, 3), //startDate
                        LocalTime.of(22, 0), LocalTime.of(6, 0),  //start, end
                        LocalDateTime.of(2021, 6, 4, 6, 0) //expected endDateTime
                ),

                //should return next day as start and end time have passed
                Arguments.of(
                        LocalDate.of(2021, 6, 5), //startDate
                        LocalTime.of(9, 0), LocalTime.of(9, 30),  //start, end
                        LocalDateTime.of(2021, 6, 5, 9, 30) //expected endDateTime
                ),

                //should return today because startTime is in the future
                Arguments.of(
                        LocalDate.of(2021, 6, 4), //startDate
                        LocalTime.of(18, 30), LocalTime.of(19, 30),  //start, end
                        LocalDateTime.of(2021, 6, 4, 19, 30) //expected endDateTime
                ),

                //should return today because startTime is in the future
                Arguments.of(
                        LocalDate.of(2021, 6, 4), //startDate
                        LocalTime.of(9, 0), LocalTime.of(17, 0),  //start, end
                        LocalDateTime.of(2021, 6, 4, 17, 0) //expected endDateTime
                ),

                //should return monday as first closest start day
                Arguments.of(
                        LocalDate.of(2021, 11, 8), //startDate
                        LocalTime.of(8, 30), LocalTime.of(17, 30),  //start, end
                        LocalDateTime.of(2021, 11, 8, 17, 30) //expected endDateTime
                )


        );
    }

}
