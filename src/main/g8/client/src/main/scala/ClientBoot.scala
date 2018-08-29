package $package$.client

import cats.effect._
import fs2.Stream
import fs2.StreamApp
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import monix.execution.Scheduler

abstract class ClientBoot[F[_]: Effect] extends StreamApp[F] {

  implicit val S: Scheduler = monix.execution.Scheduler.Implicits.global

  implicit val TM: Timer[F] = Timer.derive[F](Effect[F], IO.timer(S))

  override def stream(args: List[String],
                      requestShutdown: F[Unit]): Stream[F, StreamApp.ExitCode] =
    for {
      logger <- Stream.eval(Slf4jLogger.fromName[F]("Client"))
      exitCode <- serverStream(logger)
    } yield exitCode

  def serverStream(implicit L: Logger[F]): Stream[F, StreamApp.ExitCode]
}
