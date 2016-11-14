package com.meetup

import java.time.format.DateTimeFormatter
import java.time.temporal.{TemporalAccessor, TemporalQuery}
import java.time.{Instant, LocalDateTime, LocalTime, ZonedDateTime}

import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

object JavaTimeSerializers {
  val defaults =
    InstantSerializer ::
      LocalTimeSerializer ::
      LocalDateTimeSerializer ::
      ZonedDateTimeSerializer ::
      Nil

  def instant(df: DateTimeFormatter) = new InstantSerializer(df)

  def localTime(df: DateTimeFormatter) = new LocalTimeSerializer(df)

  def localDateTime(df: DateTimeFormatter) = new LocalDateTimeSerializer(df)

  def zonedDateTime(df: DateTimeFormatter) = new ZonedDateTimeSerializer(df)

  def withFormats(
    instantDf: DateTimeFormatter = InstantSerializer.format,
    localTimeDf: DateTimeFormatter = LocalTimeSerializer.format,
    localDateTimeDf: DateTimeFormatter = LocalDateTimeSerializer.format,
    zonedDateTimeDf: DateTimeFormatter = ZonedDateTimeSerializer.format
  ) =
    instant(instantDf) ::
      localTime(localTimeDf) ::
      localDateTime(localDateTimeDf) ::
      zonedDateTime(zonedDateTimeDf) ::
      Nil

  sealed class InstantSerializer private[JavaTimeSerializers] (val format: DateTimeFormatter) extends CustomSerializer[Instant](_ => (
    {
      case JString(s) => format.parse(s, asQuery(Instant.from))
    },
    {
      case t: Instant => JString(format.format(t))
    }
  ))
  object InstantSerializer extends InstantSerializer(DateTimeFormatter.ISO_INSTANT)

  sealed class LocalTimeSerializer private[JavaTimeSerializers] (val format: DateTimeFormatter) extends CustomSerializer[LocalTime](_ => (
    {
      case JString(s) => format.parse(s, asQuery(LocalTime.from))
    },
    {
      case t: LocalTime => JString(format.format(t))
    }
  ))
  object LocalTimeSerializer extends LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME)

  sealed class LocalDateTimeSerializer private[JavaTimeSerializers] (val format: DateTimeFormatter) extends CustomSerializer[LocalDateTime](_ => (
    {
      case JString(s) => format.parse(s, asQuery(LocalDateTime.from))
    },
    {
      case t: LocalDateTime => JString(format.format(t))
    }
  ))
  object LocalDateTimeSerializer extends LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

  sealed class ZonedDateTimeSerializer private[JavaTimeSerializers] (val format: DateTimeFormatter) extends CustomSerializer[ZonedDateTime](_ => (
    {
      case JString(s) => format.parse(s, asQuery(ZonedDateTime.from))
    },
    {
      case t: ZonedDateTime => JString(format.format(t))
    }
  ))
  object ZonedDateTimeSerializer extends ZonedDateTimeSerializer(DateTimeFormatter.ISO_ZONED_DATE_TIME)

  def asQuery[A](f: TemporalAccessor => A): TemporalQuery[A] =
    new TemporalQuery[A] {
      override def queryFrom(temporal: TemporalAccessor): A = f(temporal)
    }

}