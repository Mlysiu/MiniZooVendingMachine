# The Vending Machine

In order to launch the Vending Machine one has to:

1. Run it from Intellij (this is the most simple way)

2. Build it using ```sbt clean assembly``` and then run with ```java -jar <name_of_the_assembled_jar>```

I have assumed that I'm trying to create a set of endpoints that may work with some kind of frontend UI/real machine. At current state it is putting out two endpoints to operate with.

1. ```localhost:8080/machine/products``` - which at simplest form return to the caller full list of all products (in .json manner) that are currently hold in the vending machine. As a result you will get:
   ```
   {
      "currentStorage": [
         {
            "name": "Lion",
            "price": 2,
            "quantity": 5
         }
      ]
   }
   ```

2. ```localhost:8080/machine/products/<Product_name>?cash=<amount_of_money_you_put_to_machine>```  - this endpoint allows user to buy some product and pass the amount of cash that he put into machine. As a result you will get a json object:
   ```
   {
       "productName": "Snickers",
       "change": 3.8
   }
   ```

   It also handles situations like:
   * Passing not enough cash
   * Trying to buy product that does not exist
   * Trying to buy product that is out of stock

Implementation notes:
* Each request is creating unique set of actors. That means that at any point I can easily move freely on actor hierarchy by calling ```ActorRef.parent``` or ```ActorRef.children``` because I'm assured that each hierarchy is unique in terms of request.
* BuyProduct endpoint is using scalaz's Disjunction as I want to handle multiple 'invalid' situations
* Typical flow of the Request is:
```Request -> MachinesRoutes -> *RequestActor -> MachineActor -> BuyProductActor/ListProductsActor -> ProductsStorage```

List of improvements:
* Logging - use proper logging framework, not ```println``` ;)
* Improve storage
* Error handling
* Transactions in terms of buying a new product
* More operations that would model a Vending Machine in a better way

