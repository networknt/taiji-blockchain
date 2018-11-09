The blockchain technology can be used for public and private. For the public chain, there is a native currency which is call taiji coin. The chain is called Taiji Chain. 

### Unit

For Taiji coin, the smallest unit is called shell and one taiji is 100,000,000 shells to support all programming languages to save it as four bytes long, the max number of taiji in a system would be 92233720368.54775807 which is 92 billion taiji for max transaction amount. 

### Fee

We have a flat fee that is based on the USD. For each simple transaction, the fee will be about 100th of 1 cent for each inner chain entry in a transaction. Let's call this the smallest fee unit. 

For a transaction with one debit and one credit in the same chain, the fee is 2 units. That means that one cent can perform 50 transactions like this. 

For an credit entry on another chain, it is 4 units, so typical one debit and one credit transaction across two chains will cost 5 units fee. That means you can perform 20 transactions with 1 cent. 

For any smart contract creation and execution, the fee can be queried from the smart contract api. It all depends on the operation complexity. 

For each debit entry or credit entry, you can put a note. For the first 8 characters, it is 2 fee units
for the next 8 chars, it is 4 fee units. next 8 chars will be 16 units and next will be 256 units. This is to force users to write shorter notes if possible.

The same data field is for smart contract as well. The same fee will be applied based on the smart contract size for the first time creation.  

### Taiji Supply

There will be 92233720368(just over 92 billion) Taiji coins in circulation. All coins will be created in the genesis block across 10 addresses. There might be more addresses if some investors buy coins before the mainnet is alive. Otherwise all supply will be coming from these 10 accounts. 

All capital raised will be used to support the future development of the light platform and the blockchain technology. 

As there is no mining, we don't pay coins for miners. All partner nodes and validator nodes are supported by the transaction fees. As we can support millions transactions per second, the amount of the fee should be good enough once we reach the critical mass. 


All Taiji coins are generated and owned by the following entities:

- Network New Technologies Inc. who owns the light platform including light-blockchain and infrastructure. 
- Enterprise Blockchain Lab who is a non-profit organization focusing on research and development.
- Taiji Chain Foundation who is a commercial arm to promote and market the public chain to partners.

The percentage is 50%, %15 and %35 for above entities. 


### Mining

Bitcoin is using proof-of-work to award miners, but at the same time, it burns a lot of electricity. Eventually, the mining power concentrated at several big companies.  It is not the way to distribute the control to more entities. 

Taiji chain is based on the private blockchain technology from Network New Technologies Inc., and it links all partners with a fully distributed public chain. Partner nodes are not miners but managers for their customers. They make money based on the transaction fees. The more people attracted to their node, the more transactions they will handle, and the more fees will be earned. Each partner has a separate chain of their own, and all chains between partners are linked together. 

All available coins 92.233 billion are distributed to 10 head addresses in Genesis block. These will be distributed to miners based on the proof of work protocol.  It is the real work that includes contributing to the open source projects in https://github.com/networknt. In the beginning, all contributions will be evaluated by the core team members and decide how much Taiji should be paid. You don't need to wait until the mainnet is up and running. When using the testnet, we record distributions in distribution.txt in https://github.com/taiji-chain/contribution repository. Every contribution will have a record in the issue of this repository for tracking purpose during this transition period. 


### Voting

Once the mainnet is up and running, we are going to build a voting application for the community to evaluate proof of work by members. Once a piece of work is submitted to the voting system, all members are eligible for voting a number on it for a period of time. Until the result is revealed, nobody knows other people's numbers. The voting is calculated based on the weight of the stake. Any numbers that are very close to the final result will be rewarded. It is a way to encourage the community to give correct valuation for any piece of work submitted by members. 


### Auction

Voting is usually for volunteer task. There are some big-ticket projects available within the community, and we can build an application to auction out these projects for the community members to work on. Once the work is done and approved by members with voting, the auctioned amount will be paid. 


### Lottery

Once the mainnet is up and running, we are going to build a lottery application for members to have fun. People can buy lottery tickets with a number in the contract. On the day the lottery is opened, all tickets with the right number will split the amount from all ticket minus a little bit fee to cover the cost. 


### Distributed Organization

The taiji chain connects all community members to form a highly distributed organization. Everybody is working for the community and get paid by the community. 

### Multiple currencies

With the distributed blockchain, we can have multiple currencies deployed on it. And every currency can be scaled with multiple subchains. The partner nodes can be acted as a bank in the traditional financial system, and currency exchange can happen there. If a real bank is involved, then fiat currency can be moved to the chain for payment and exchange. 

### Smart contract

webassembly based smart contracts


### Credentials

Loading encrypted credentials from the JSON file is very slow and CPU intensive. For most cases, an application should have only one or several addresses. It is recommended to cache it once it is loaded.

### Address validation

We need to come up a schema which enable the address validation so that invalid addresses will be rejected. On the testnet, people can just type any random numbers and trasfer the fund to it without retrieving it. 

### Token support

Need to support ERC20 tokens with built-in contract. 

### 




