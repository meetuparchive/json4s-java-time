resolvers += Resolver.url(
  "meetup-sbt-plugins",
  new java.net.URL("https://dl.bintray.com/meetup/sbt-plugins/")
)(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-dirty-money" % "0.2.0")

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.4")

