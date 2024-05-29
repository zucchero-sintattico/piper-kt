# Benchmarking

Benchmarking is the process of measuring the performance of a system.
It is a critical step in the development of any software system, as it allows developers to identify bottlenecks and performance issues early in the development process.
Benchmarking can also be used to compare the performance of different systems or different versions of the same system.

## HTTP Benchmarking

The following tests are performed in order to stress the system to verify that horizontal scalability brings some of the expected results: increasing the number of requests handled over a given period.

This test consists of running the same client-side workload by varying the number of replicas of the service that must handle them.

### Tools & Setup

The scenario involves a client making HTTP requests to the server, where the Piper-kt software is running, inside a Kubernetes cluster provided by Minikube.

#### Hardware

| Machine | O.S          | CPU                       | RAM  | Link        |
|---------|--------------|---------------------------|------|-------------|
| Client  | Ubuntu 22.04 | Intel Core i7-8700 6 core | 48GB | 1Gbps (LAN) |
| Server  | macOS 14.5   | Apple M1 Pro              | 16GB | 1Gbps (LAN) |

#### Client Software

The tool used to make client-side HTTP requests is [wrk](https://github.com/wg/wrk), a benchmarking software that allows significant workloads to be generated, taking advantage of a single multi-core CPU.

Microservice under test is `users-service`, focussing on /auth/login route, tested with the credentials of an already registered user, through the following configuration:

```lua
-- resources/benchmark-scripts/post.lua
wrk.method = "POST"
wrk.body = '{"username": "manuandru", "password": "12341234"}'
wrk.headers["Content-Type"] = "application/json"
```

```bash
$ wrk -t10 -c150 -d30s http://<server-ip>/auth/login -s ./post.lua
```

- `-t10`: 10 threads
- `-c150`: 150 simultaneous connections
- `-d30s`: 30 seconds of test

### Results

