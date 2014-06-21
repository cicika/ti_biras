package org.mojdan.md_backend.http.auth

import akka.actor._
import akka.actor.ActorSystem
import akka.pattern._
import akka.util._

import com.typesafe.config.ConfigFactory

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Q.interpolation

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.{Success, Failure}

import spray.http.{GenericHttpCredentials, HttpCredentials, HttpRequest, HttpHeaders, HttpChallenge}
import spray.routing.authentication._
import spray.routing.{RequestContext, AuthenticationFailedRejection}

import org.mojdan.md_backend.model._
import org.mojdan.md_backend.storage.UserStorage
import org.mojdan.md_backend.util._

trait TBApiAuthenticator extends HttpAuthenticator[String]{
   implicit val actorSystem = ActorSystem()
   def params(ctx: RequestContext) = Map.empty
   implicit def executionContext = actorSystem.dispatcher
   def scheme: String
   def realm: String
   def params: Map[String, String] = Map.empty
   def getChallengeHeaders(httpRequest: HttpRequest) = List(HttpHeaders.`WWW-Authenticate`(HttpChallenge(scheme, realm, params)))

}

class TBAuthAuthenticator extends TBApiAuthenticator with Tables 
                                                     with Config
                                                     with UserStorage{
  def realm = "TiBiras Auth Realm"
  def scheme = "TBAuth"
  def authenticate(credentials: Option[HttpCredentials], ctx: RequestContext) = credentials match {
    case Some(creds) => 
      if(creds.toString.startsWith(scheme)) { 
        val accessToken = creds.toString.drop(scheme.length + 1)  
        Future{ checkToken(accessToken) }
      }
      else Future { None }
    case None => Future { None }
    }
}

trait UserAuthentication{

	val tbAuthenticator = new TBAuthAuthenticator

}
