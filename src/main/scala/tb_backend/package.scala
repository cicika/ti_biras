package tb_backend


import com.typesafe.config.ConfigFactory

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Q.interpolation

import java.sql.Timestamp
import java.util.Date
import java.util.Calendar

trait Config {
	val config = ConfigFactory.load()
	
	
	val dbUrl = "jdbc:postgresql://localhost/%s" format config.getString("db.database")
	val dbDriver = "org.postgresql.Driver"
	val dbUser = config.getString("db.username")
	val dbPassword = config.getString("db.password")
	val db = Database.forURL(dbUrl, driver = dbDriver, user = dbUser, password = dbPassword)
}

trait TimeUtils {
	def now = {
		val currTime = new Date()
		currTime.getTime()
	}

	def currentHour = {
		val currTime = new Date()
		currTime.getHours()
	}

	def weekdayNow = {
		val currTime = Calendar.getInstance()
		currTime.get(Calendar.DAY_OF_WEEK)
	}

	def weekday(timestamp: Timestamp) = {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.MILLISECOND, timestamp.getTime().toInt)
		calendar.get(Calendar.DAY_OF_WEEK)
	}

	def weekdayWithDelta(timestamp: Timestamp, delta: Int) = {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.MILLISECOND, timestamp.getTime().toInt)
		calendar.roll(Calendar.DAY_OF_WEEK, delta)
		calendar.get(Calendar.DAY_OF_WEEK)
	}
}