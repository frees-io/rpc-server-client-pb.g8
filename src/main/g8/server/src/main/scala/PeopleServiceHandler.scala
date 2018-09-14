package $package$.server

import cats.effect.Sync
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import $package$.protocol._

class PeopleServiceHandler[F[_]: Sync: Logger: PeopleRepository] extends PeopleService[F] {
  val serviceName = "PeopleService"

  override def getPerson(request: PeopleRequest): F[PeopleResponse] =
    for {
      _      <- Logger[F].info(s"\$serviceName - Request: \$request")
      person <- PeopleRepository[F].getPerson(request.name)
      _      <- Logger[F].info(s"\$serviceName - Sending response: \$person")
    } yield PeopleResponse(person)
}
