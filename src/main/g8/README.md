
# $name$

$projectDescription$

## How to run:

Run the server:

```bash
sbt runServer
```

And the log will show:

```bash
INFO  - seedServer - Starting server at localhost:19683
```

then, run the client:

```bash
sbt runClient
```

The client should log:

```bash
INFO  - Created new RPC client for (localhost,19683)
INFO  - Request: foo
INFO  - Result: PeopleResponse(Person(foo,10))
INFO  - Removed 1 RPC clients from cache.
```

And the server:

```bash
INFO  - PeopleService - Request: PeopleRequest(foo)
```

[comment]: # (Start Copyright)
# Copyright

Freestyle-RPC is designed and developed by 47 Degrees

Copyright (C) 2017 47 Degrees. <http://47deg.com>

[comment]: # (End Copyright)
