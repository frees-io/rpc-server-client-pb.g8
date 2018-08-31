package $package$.client

import cats.effect._
import fs2.Stream
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import monix.execution.Scheduler

object ClientApp {

  implicit val S: Scheduler = monix.execution.Scheduler.Implicits.global

  implicit val TM = Timer.derive(Effect[IO], IO.timer(S))

  def main(args: Array[String]): Unit = {
    (for {
      logger <- Stream.eval(Slf4jLogger.fromName[IO]("Client"))
      client <- {
        implicit val l = logger
        PeopleServiceClient.createClient[IO]("localhost", 19683)
      }
      getPersonResponse <- Stream.eval(client.getPerson("foo")).as(println)
    } yield (getPersonResponse)).compile.toVector.unsafeRunSync()

  }

}
