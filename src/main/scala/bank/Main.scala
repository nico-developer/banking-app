package bank

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import bank.swagger.SwaggerDocService
import com.typesafe.config.{Config, ConfigFactory}
import akka.http.scaladsl.server.Directives._

object Main extends App {

  println("Welcome to Banking app")

  val controller = new Controller

  implicit val actorSystem = ActorSystem()

  val conf = ConfigFactory.load()
  val appHost = conf.getString("banking-app.host")
  val appPort = conf.getInt("banking-app.port")

  Http().newServerAt(appHost,appPort).bind(SwaggerDocService.routes ~ controller.allRoutes)
}
