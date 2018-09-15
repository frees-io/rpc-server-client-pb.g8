# RPC-server-client-pb

This is a giter8 template that includes a couple of microservices that play the roles of server and client and interact with each other via RPC (serialized as Protocol Buffer). This structure is composed of three SBT modules:

- The RPC protocol, which is very simple. It exposes a service to lookup a person given a name.
- The server, which with implements an interpreter of the service defined by the protocol and it runs an RPC server.
- The client, which consumes the RPC endpoint against the server, and it uses the protocol to know the schema.

## Usage

1. Create a new project:

```bash
sbt new frees-io/rpc-server-client-pb.g8
```

or

```bash
g8 frees-io/rpc-server-client-pb.g8
```

if you have the [g8 plugin](http://www.foundweekends.org/giter8/setup.html) installed.

2. Fill the required information (you can see an example below):

```bash

...

name [Project Name]: My RPC project
projectDescription [Project Description]: A new RPC project with Freestyle-RPC
project [project-name]: my-rpc-project
package [org.mycompany]: org.mycompany
freestyleRPC [0.14.1]: 0.14.1

...

Template applied in ./my-rpc-project
```
