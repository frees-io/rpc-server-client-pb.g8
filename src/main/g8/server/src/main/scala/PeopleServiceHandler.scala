package $package$.server

import cats.effect.Sync
import cats.syntax.functor._
import $package$.protocol.services._
import io.chrisdavenport.log4cats.Logger

class PeopleServiceHandler[F[_]: Sync](implicit L: Logger[F])
    extends PeopleService[F] {

  override def getPerson(request: GetPersonRequest): F[GetPersonResponse] =
    L.info(s"PeopleService - Request: \$request")
      .as(GetPersonResponse(Person(request.name, 10)))

}
