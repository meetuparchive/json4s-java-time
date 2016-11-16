package com.meetup.json4s

import java.time.{Instant, LocalDateTime, LocalTime, ZonedDateTime}

case class DateThings(
  instant: Instant,
  localTime: LocalTime,
  localDateTime: LocalDateTime,
  zonedDateTime: ZonedDateTime
)
