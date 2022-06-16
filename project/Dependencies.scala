import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11"

  val AkkaVersion = "2.6.19"
  val AkkaHttpVersion = "10.2.9"

  val AkkaActors = "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
  val AkkaStreams = "com.typesafe.akka" %% "akka-stream" % AkkaVersion
  val AkkaHttp = "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
  val AkkaSpray = "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion

  val TypeSafeConfig =  "com.typesafe" % "config" % "1.2.1"
  val SprayVersion = "1.3.6"
  val SprayJson = "io.spray" %%  "spray-json" % SprayVersion

  val AkkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion
  val AkkaHttpTestKit =  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion

  val Swagger = List(
    "jakarta.ws.rs" % "jakarta.ws.rs-api" % "3.0.0",
    "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.7.0",
    "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.6.0",
    "com.github.swagger-akka-http" %% "swagger-enumeratum-module" % "2.4.0",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.2",
    "io.swagger.core.v3" % "swagger-jaxrs2-jakarta" % "2.2.0",
    "org.webjars" % "webjars-locator" % "0.45",
    "org.webjars" % "swagger-ui" % "4.6.2",
    "pl.iterators" %% "kebs-spray-json" % "1.9.4",
    "ch.megard" %% "akka-http-cors" % "1.1.3"
  )
}
