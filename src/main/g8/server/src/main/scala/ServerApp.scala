package $package$.server

import cats.effect._
import cats.syntax.flatMap._
import cats.syntax.functor._
import $package$.protocol.services._
import fs2.{Stream, StreamApp}
import io.chrisdavenport.log4cats.Logger
import freestyle.rpc.server._

class ServerProgram[F[_]: Effect] extends ServerBoot[F] {

  override def serverStream(
      implicit L: Logger[F]): Stream[F, StreamApp.ExitCode] = {

    implicit val PS: PeopleService[F] = new PeopleServiceHandler[F]

    val port = 19683

    val grpcConfigs: List[GrpcConfig] = List(
      AddService(PeopleService.bindService[F]))

    Stream.eval(for {
      server <- GrpcServer.default[F](port, grpcConfigs)
      _ <- L.info(s"Starting server at localhost:\$port")
      exitCode <- GrpcServer.server(server).as(StreamApp.ExitCode.Success)
    } yield exitCode)

  }
}

object ServerApp extends ServerProgram[IO]
