package bank

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.{Config, ConfigFactory}


object Main extends App {

  println("Welcome to Banking app")

  val controller = new Controller

  implicit val actorSystem = ActorSystem()

  val conf = ConfigFactory.load()
  val appHost = conf.getString("banking-app.host")
  val appPort = conf.getInt("banking-app.port")

  Http().newServerAt(appHost,appPort).bind(controller.allRoutes)
}
