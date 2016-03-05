package com.mlysiu.temp.service

import akka.actor.Actor.Receive
import spray.routing.HttpService
import akka.actor.{ActorRefFactory, Actor}
import spray.http._
import MediaTypes._

class MyServiceActor extends Actor with MyService {
  def receive: Receive = runRoute(myRoute)

  def actorRefFactory: ActorRefFactory = context
}

trait MyService extends HttpService {

  val myRoute =
    path("somePath") {
      get {
        respondWithMediaType(`text/html`) {
          complete {
            <html>
              <body>
                <h1>Say hello to
                  <i>spray-routing</i>
                  on
                  <i>spray-can</i>
                  !</h1>
              </body>
            </html>
          }
        }
      }
    }
}
