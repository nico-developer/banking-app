package bank

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json._

class Controller extends DefaultJsonProtocol with SprayJsonSupport{

  var accounts = List[Account](
    Account(
      accountNumber = "123",
      currentBalance = 9999,
      currency = "PHP",
      customerFirstName = "Lloyd",
      customerLastName = "Edano"
    )
  )

  def createAccount: Route = post {
    path("api" / "accounts" ) {
      entity(as[Account]) { account=>
        accounts = accounts :+ account
        complete(Created, "Account created".toJson )
      }
    }
  }

  def getAccountByAccountNum: Route = get {
    path ("api" / "accounts" / Segment) { accountNumber=>
      accounts.find(_.accountNumber == accountNumber) match {
        case Some(accountFound)=>
          complete(OK, accountFound.toJson )
        case None=>
          complete(NotFound,JsObject.empty)
      }
    }
  }

  def allRoutes: Route =
    createAccount ~ getAccountByAccountNum
}
