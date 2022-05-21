package bank

import akka.actor.ActorSystem
import akka.http.scaladsl.Http


object Main extends App {

  println("Welcome to Banking app")

  val controller = new Controller

  implicit val actorSystem = ActorSystem()

  Http().newServerAt("localhost",9000).bind(controller.allRoutes)
}
