package $package$.server

import cats.effect.Effect
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import fs2._
import monix.execution.Scheduler

abstract class ServerBoot[F[_]: Effect] extends StreamApp[F] {

  implicit val S: Scheduler = monix.execution.Scheduler.Implicits.global

  override def stream(args: List[String],
                      requestShutdown: F[Unit]): Stream[F, StreamApp.ExitCode] =
    for {
      logger <- Stream.eval(Slf4jLogger.fromName[F]("Server"))
      exitCode <- serverStream(logger)
    } yield exitCode

  def serverStream(implicit L: Logger[F]): Stream[F, StreamApp.ExitCode]
}
