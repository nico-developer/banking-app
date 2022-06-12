package bank

import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.util.ByteString
import spray.json.{DefaultJsonProtocol, JsObject, JsValue, enrichAny}

import scala.util.parsing.json.JSONObject
class ControllerSpec extends AnyWordSpec with Matchers with ScalatestRouteTest with SprayJsonSupport with DefaultJsonProtocol{

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

    "get an account" in {
      val getRequest = HttpRequest(
        HttpMethods.GET,
        uri = "/api/accounts/123"
      )
      val account = Account(
        accountNumber = "123",
        currentBalance = 9999,
        currency = "PHP",
        customerFirstName = "Lloyd",
        customerLastName = "Edano"
      )
      getRequest ~> controller.getAccountByAccountNum ~> check {
        status shouldEqual StatusCodes.OK
        val expected:JsValue =
          s"""{"accountNumber":"123",
             |"currency":"PHP",
             |"currentBalance":9999.0,
             |"customerFirstName":"Lloyd",
             |"customerLastName":"Edano"}""".stripMargin
            .replace("\n","")
            .replace("\r","")
            .toJson
        responseAs[String].toJson shouldEqual (expected)
      }
    }

    "failed to get an account" in {
      val getRequest = HttpRequest(
        HttpMethods.GET,
        uri = "/api/accounts/124"
      )

      getRequest ~> controller.getAccountByAccountNum ~> check {
        status shouldEqual StatusCodes.NotFound

        responseAs[JsObject] shouldEqual (JsObject.empty)
      }
    }
  }
}
