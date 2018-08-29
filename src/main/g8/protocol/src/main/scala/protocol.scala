package $package$.protocol

import freestyle.rpc.protocol._

object services {

  final case class Person(name: String, age: Int)

  @message
  final case class GetPersonRequest(name: String)

  @message
  final case class GetPersonResponse(person: Person)

  @service(Protobuf) trait PeopleService[F[_]] {

    def getPerson(request: GetPersonRequest): F[GetPersonResponse]

  }

}
