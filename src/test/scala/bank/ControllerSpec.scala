package bank

import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.util.ByteString
class ControllerSpec extends AnyWordSpec with Matchers with ScalatestRouteTest{

  "Banking app" should{
    val controller = new Controller
    "create an account" in {
      val account = ByteString((
        "{\"accountNumber\":\"123\"," +
        "\"currency\":\"PHP\"," +
        "\"currentBalance\":9999.0," +
        "\"customerFirstName\":\"Nico\"," +
        "\"customerLastName\":\"Oracion\"}").stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/api/accounts",
        entity = HttpEntity(MediaTypes.`application/json`,account)
      )
      postRequest ~> controller.createAccount ~> check {
        status shouldEqual StatusCodes.Created
        responseAs[String] shouldEqual "\"Account created\""
      }
    }
  }
}
