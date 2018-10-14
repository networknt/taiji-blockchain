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

