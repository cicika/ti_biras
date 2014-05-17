package tb_backend.app

import akka.actor._
import akka.event.Logging

import tb_backend.model._
import tb_backend.storage._

class UserActor extends Actor {
	
	private val log = Logging(context.system, this)
	log.debug("Starting...")
	def receive = {
		case Login(user, pass) =>
			log.info("Received Login request...")
			sender ! LoginResponse(22, "asdf")
			/*
					}*/
		case Register =>
		case Terminated(_) => log.error("terminated...")
		case _ => log.info("Unknown message received....")
	}
}