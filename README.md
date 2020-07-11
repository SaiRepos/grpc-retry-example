# grpc-retry-example

A simple example to demo the retry mechanism in gRPC java.The project has below two modules
- [grpc-server](https://github.com/SaiRepos/grpc-retry-example/tree/master/grpc-server) - simple grpc server that responds with `UNAVIALABLE` status to make `grpc-retry-client` to retry the call to server
- [grpc-retry-client](https://github.com/SaiRepos/grpc-retry-example/tree/master/grpc-retry-client) - simple grpc client to demo the grpc java retry

## Building

To build both the modules,run `maven clean install` in `grpc-retry-example`
