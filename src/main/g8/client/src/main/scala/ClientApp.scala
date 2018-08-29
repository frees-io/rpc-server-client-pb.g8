package $package$.client

import cats.effect._
import cats.syntax.functor._
import fs2.{Stream, StreamApp}
import io.chrisdavenport.log4cats.Logger

import scala.language.postfixOps
import scala.concurrent.duration._

class ClientProgram[F[_]: Effect] extends ClientBoot[F] {

  def peopleServiceClient(host: String, port: Int)(
      implicit L: Logger[F]): Stream[F, PeopleServiceClient[F]] =
    PeopleServiceClient.createClient(host,
                                     port,
                                     sslEnabled = false,
                                     30 minutes,
                                     1 hour)

  override def serverStream(
      implicit L: Logger[F]): Stream[F, StreamApp.ExitCode] = {
    for {
      peopleClient <- peopleServiceClient("localhost", 19683)
      exitCode <- Stream.eval(
        peopleClient.getPerson("foo").as(StreamApp.ExitCode.Success))
    } yield exitCode
  }
}

object ClientApp extends ClientProgram[IO]
