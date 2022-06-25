package bank

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.{Operation, Parameter}
import io.swagger.v3.oas.annotations.media.{Content, Schema}
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.{Consumes, GET, POST, Path, Produces}
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

  @POST
  @Path("/api/accounts")
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(description = "Create Customer Account",
    requestBody = new RequestBody(required = true,
      content = Array(new Content(schema = new Schema(implementation = classOf[Account])))),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Account created"),
      new ApiResponse(responseCode = "500", description = "Internal server error"))
  )
  def createAccount: Route = post {
    path("api" / "accounts" ) {
      entity(as[Account]) { account=>
        accounts = accounts :+ account
        complete(Created, "Account created".toJson )
      }
    }
  }

  @GET
  @Path("/api/accounts/{accountNumber}")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Operation(summary = "Find an account by account number",
    description = "Get Customer Account",
    parameters = Array(
      new Parameter(name = "accountNumber", in = ParameterIn.PATH,required = true, description = "Account Number",
        content = Array(new Content(schema = new Schema(implementation = classOf[String]))))
    ),
    responses = Array(
      new ApiResponse(responseCode = "200", description = "Customer's Account",
        content = Array(new Content(schema = new Schema(implementation = classOf[Account])))),
      new ApiResponse(responseCode = "400", description = "Invalid account number supplied"),
      new ApiResponse(responseCode = "404", description = "Account not found"))
  )
  def getAccountByAccountNum: Route = get {
    path ("api" / "accounts" / Segment) { accountNumber=>
      accounts.find(_.accountNumber == accountNumber) match {
        case Some(accountFound)=>
          complete(OK, accountFound.toJson)
        case None=>
          complete(NotFound, s"{\"message\":Account number $accountNumber was not found}".toJson)
      }
    }
  }

  def allRoutes: Route =
    createAccount ~ getAccountByAccountNum
}
