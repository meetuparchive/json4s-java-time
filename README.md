# json4s-java-time

This library adds minimal `java.time` support for [json4s] [1] by way of its support for [custom serialization] [2].
Assuming familiarity with `json4s`, simply add the serializers provided via `JavaTimeSerializers.defaults` to the
implicit Formats in use enable support for handling [ISO-8061] [3] strings.

```scala
import com.meetup.json4s.JavaTimeSerializers
import org.json4s.DefaultFormats

implicit val formats = DefaultFormats ++ JavaTimeSerializers.defaults
```

[1]: https://github.com/json4s/json4s                                   "json4s"
[2]: https://github.com/json4s/json4s#serializing-non-supported-types   "CustomSerializers"
[3]: https://en.wikipedia.org/wiki/ISO_8601                             "ISO-8061"