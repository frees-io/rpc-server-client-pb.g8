package $package$.client

import cats.effect._
import cats.syntax.applicative._
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import io.grpc.{CallOptions, ManagedChannel}
import monix.execution.Scheduler
import $package$.protocol._

import scala.concurrent.duration._

trait PeopleServiceClient[F[_]] {

  def getPerson(name: String): F[Person]

}

object PeopleServiceClient {

  def apply[F[_]: Effect](clientF: F[PeopleService.Client[F]])(
      implicit L: Logger[F]
  ): PeopleServiceClient[F] = new PeopleServiceClient[F] {
    override def getPerson(name: String): F[Person] =
      for {
        client <- clientF
        _      <- L.info(s"Request: \$name")
        result <- client.getPerson(PeopleRequest(name))
        _      <- L.info(s"Result: \$result")
      } yield result.person
  }

  def createClient[F[_]: Effect](
      hostname: String,
      port: Int,
      sslEnabled: Boolean = false,
      tryToRemoveUnusedEvery: FiniteDuration = 30.minutes,
      removeUnusedAfter: FiniteDuration = 1.hour)(
      implicit L: Logger[F],
      TM: Timer[F],
      S: Scheduler): fs2.Stream[F, PeopleServiceClient[F]] = {

    def fromChannel(channel: ManagedChannel): PeopleService.Client[F] =
      PeopleService.clientFromChannel(channel, CallOptions.DEFAULT)

    ClientRPC
      .clientCache(
        (hostname, port).pure[F],
        sslEnabled,
        tryToRemoveUnusedEvery,
        removeUnusedAfter,
        fromChannel)
      .map(cache => PeopleServiceClient(cache.getClient))
  }
}
