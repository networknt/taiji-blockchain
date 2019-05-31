# taiji-blockchain
taiji blockchain core, plugins and client

[Developer Chat](https://gitter.im/networknt/taiji-blockchain) |
[Documentation](https://doc.taiji.io) |
[Contribution Guide](CONTRIBUTING.md) |

### Introduction

Over the years, we are working on large-scale distributed light-4j platform with high throughput and low latency as our goals. As our major customers are banks and financial institutions, the large amount of data handled by distributed services and data consistency across services are crucial. Given our experience, we think that the current blockchain technology has a lot of room to improve in term of throughput. We have been building systems with millions of requests per second and still has room to grow linearly. We think that the techniques we are using can be combined with blockchain technology to build a chain that can handle millions of transactions per second. 

Another reason that we want to invest in blockchain is to use it as an event store for light-eventuate-4j event sourcing and CQRS framework. Currently, we are using a database as an event store and a change data capture (CDC) service to trailing the transaction log to guarantee at least once message delivery on Kafka. What if we can build a blockchain as an event store and then push the blocks to IPFS so that users can query, verify and replay the events whenever they want. It opens a new door to do business for enterprise customers. 

### Build

```
./gradlew clean build shadowJar
```
