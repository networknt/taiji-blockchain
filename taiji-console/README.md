Here is the commands to generate transactions from inner chain. 

One to one transaction only once

```
cd taiji-console/build/libs
java -jar taiji-console-fat-1.0.0.jar wallet innertransfer 1-1 1 
```


One to one transaction for 10 times

```
java -jar taiji-console-fat-1.0.0.jar wallet innertransfer 1-1 10
```

One to n transaction for only once

```
java -jar taiji-console-fat-1.0.0.jar wallet innertransfer 1-N 1
```

One to n transaction for 10 times

```
java -jar taiji-console-fat-1.0.0.jar wallet innertransfer 1-N 10
```

To test locally, you need to specify the local configuration so that the console can look up the local instance of chain-writer. 

```
java -jar -Dlight-4j-config-dir=../../config/local taiji-console-fat-1.0.0.jar wallet wallet innertransfer 1-1 1 
```