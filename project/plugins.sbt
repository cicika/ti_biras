addSbtPlugin("com.twitter" %% "sbt-package-dist" % "1.0.6-SNAPSHOT" from "file://" + Path("embedded-repo").absolutePath + "/com.twitter/sbt-package-dist/scala_2.10/sbt_0.13/1.0.6-SNAPSHOT/jars/sbt-package-dist.jar")

addSbtPlugin("io.spray" % "sbt-twirl" % "0.7.0")

resolvers += "spray repo" at "http://repo.spray.io"