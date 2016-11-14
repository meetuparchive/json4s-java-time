package com.meetup

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, LocalTime, ZonedDateTime}

import com.meetup.JavaTimeSerializers.{InstantSerializer, LocalDateTimeSerializer, LocalTimeSerializer, ZonedDateTimeSerializer}
import org.json4s.DefaultFormats
import org.scalatest._

import org.json4s.native.Serialization.{read, write}

class FooTest extends FunSpec {

  def get =
    DateThings(
      Instant.now,
      LocalTime.now,
      LocalDateTime.now,
      ZonedDateTime.now
    )

  def getJsonObjString(
    instant: String,
    localTime: String,
    localDateTime: String,
    zonedDateTime: String) =
    s"""{
        |"instant": "$instant",
        |"localTime": "$localTime",
        |"localDateTime": "$localDateTime",
        |"zonedDateTime": "$zonedDateTime"
        |}""".stripMargin

  def getJsonObjStringWithTimes(
    instant: Instant = Instant.now,
    localTime: LocalTime = LocalTime.now,
    localDateTime: LocalDateTime = LocalDateTime.now,
    zonedDateTime: ZonedDateTime = ZonedDateTime.now,
    instantDf: DateTimeFormatter = InstantSerializer.format,
    localTimeDf: DateTimeFormatter = LocalTimeSerializer.format,
    localDateTimeDf: DateTimeFormatter = LocalDateTimeSerializer.format,
    zonedDateTimeDf: DateTimeFormatter = ZonedDateTimeSerializer.format
  ) =
    getJsonObjString(
      instantDf.format(instant),
      localTimeDf.format(localTime),
      localDateTimeDf.format(localDateTime),
      zonedDateTimeDf.format(zonedDateTime)
    )

  describe("Default java.time support") {
    JavaTimeSerializers.withFormats(
      instantDf = DateTimeFormatter.ofPattern("hh:mm:ss")
    )
  }

  describe("Default java.time support") {

    implicit val formats = DefaultFormats ++ JavaTimeSerializers.defaults

    it("should deserialize an Instant field") {
      val instant = Instant.now
      val json = getJsonObjStringWithTimes(instant = instant)
      val d = read[DateThings](json)
      assert(instant == d.instant)
    }

    it("should deserialize a LocalTime field") {
      val localTime = LocalTime.now
      val json = getJsonObjStringWithTimes(localTime = localTime)
      val d = read[DateThings](json)
      assert(localTime == d.localTime)
    }

    it("should deserialize a LocalDateTime field") {
      val localDateTime = LocalDateTime.now
      val json = getJsonObjStringWithTimes(localDateTime = localDateTime)
      val d = read[DateThings](json)
      assert(localDateTime == d.localDateTime)
    }

    it("should deserialize a ZonedDateTime field") {
      val zonedDateTime = ZonedDateTime.now
      val json = getJsonObjStringWithTimes(zonedDateTime = zonedDateTime)
      val d = read[DateThings](json)
      assert(zonedDateTime == d.zonedDateTime)
    }

    it("should result in equivalent instances when serialized and deserialized") {
      val d0 = get
      val d1 = read[DateThings](write(d0))
      assert(d0 == d1)
    }

    it("should fail to deserialize invalid date/time values") {
      val emptyTimes = getJsonObjString("", "", "", "")
      try {
        read[DateThings](emptyTimes)
        fail("Should not have been able to deserialize!")
      } catch {
        case _: Exception => ()
      }
    }

  }

}

case class DateThings(
  instant: Instant,
  localTime: LocalTime,
  localDateTime: LocalDateTime,
  zonedDateTime: ZonedDateTime
)
