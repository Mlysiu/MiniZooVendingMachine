package com.mlysiu.temp.service

import org.scalatest.{Matchers, WordSpec}
import spray.http.StatusCodes._
import spray.http._
import spray.testkit.ScalatestRouteTest

class MyServiceTest extends WordSpec with ScalatestRouteTest with MyService with Matchers {
  def actorRefFactory = system

  "MyService" should {

    "return a greeting for GET requests to the root path" in {
      Get("/somePath") ~> myRoute ~> check {
        responseAs[String] should include("Say hello")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/kermit") ~> myRoute ~> check {
        handled shouldBe false
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put("/somePath") ~> sealRoute(myRoute) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}

