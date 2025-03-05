
# Change-2 : FlightOrderBuilder

### Problem
The 'FlightOrder' class has multiple dependencies and attributes that need to be set during its construction. The constructor of 'FlightOrder' can become complex and difficult to manage, especially if the number of parameters increases.

### Solution

**Builder Pattern** is used to construct a complex object step by step. The 'FlightOrderBuilder' which implements 'Builder' Interface is created to build the 'FlightOrder' object. 'Director' class Constructs the 'FlightOrderBuilder' object and sets the attributes of 'FlightOrder' object using the 'FlightOrderBuilder' object.


### Before Refactoring

```plantuml
@startuml : Refactoring

class Order {
    - UUID id
    - double price
    - boolean isClosed
    + getId(): UUID
    + getPrice(): double
    + setPrice(double price): void
    + isClosed(): boolean
    + setClosed(): void
}

class FlightOrder {
    - List<ScheduledFlight> flights
    - Customer customer
    - List<Passenger> passengers
    + getScheduledFlights(): List<ScheduledFlight>
    + setCustomer(Customer customer): void
    + setPassengers(List<Passenger> passengers): void
    + processOrderWithCreditCard(CreditCard creditCard): boolean
    + processOrderWithPayPal(String email, String password): boolean
}


class Customer {
    - String email
    - String name
    - List<Order> orders
    + createOrder(List<String> passengerNames, List<ScheduledFlight> flights, double price): FlightOrder
}

class Passenger {
    + Passenger(String name)
}

class ScheduledFlight {
    + getAvailableCapacity(): int
    + addPassengers(List<Passenger> passengers): void
}


Customer o-- Order
Customer o-- FlightOrder
Order o-- Passenger
Order <|-- FlightOrder
FlightOrder o-- ScheduledFlight

@enduml

```

Here 'Customer' class creates 'Order' and 'FlightOrder' objects. 'FlightOrder' class has multiple dependencies like 'ScheduledFlight', 'Customer' and 'Passenger' objects. The 'createOrder' method in 'Customer' class is responsible for creating the 'FlightOrder' object and setting its attributes.

```Java
public FlightOrder createOrder(List<String> passengerNames, List<ScheduledFlight> flights, double price) {
        if (!isOrderValid(passengerNames, flights)) {
            throw new IllegalStateException("Order is not valid");
        }

        FlightOrder order = new FlightOrder(flights);
        order.setCustomer(this);
        order.setPrice(price);
        List<Passenger> passengers = passengerNames
                .stream()
                .map(Passenger::new)
                .collect(Collectors.toList());
        order.setPassengers(passengers);
        order.getScheduledFlights().forEach(scheduledFlight -> scheduledFlight.addPassengers(passengers));
        orders.add(order);
        return order;
    }
```
### After Refactoring

```plantuml
@startuml

class Order {
    - UUID id
    - double price
    - boolean isClosed
    - Customer customer
    - List<Passenger> passengers
    + getId(): UUID
    + getPrice(): double
    + setPrice(double price): void
    + getCustomer(): Customer
    + setCustomer(Customer customer): void
    + getPassengers(): List<Passenger>
    + setPassengers(List<Passenger> passengers): void
    + isClosed(): boolean
    + setClosed(): void
}

class FlightOrder {
    - List<ScheduledFlight> flights
    + getScheduledFlights(): List<ScheduledFlight>
    + setFlights(List<ScheduledFlight> flights): void
    + processOrderWithCreditCard(CreditCard creditCard): boolean
    + processOrderWithPayPal(String email, String password): boolean
}

class Customer {
    - String email
    - String name
    - List<Order> orders
    + createOrder(List<String> passengerNames, List<ScheduledFlight> flights, double price): FlightOrder
    + getEmail(): String
    + setEmail(String email): void
    + getName(): String
    + setName(String name): void
    + getOrders(): List<Order>
    + setOrders(List<Order> orders): void
}

class Passenger {
    + getAvailableCapacity(): int
    + addPassengers(List<Passenger> passengers): void
}

class ScheduledFlight {
    + getAvailableCapacity(): int
    + addPassengers(List<Passenger> passengers): void
}

interface Builder {
    + buildFlights(List<ScheduledFlight> flights): void
    + buildCustomer(Customer customer): void
    + buildPrice(double price): void
    + buildPassengers(List<String> passengers): void
}

class FlightOrderBuilder {
    - FlightOrder order
    + FlightOrderBuilder()
    + buildFlights(List<ScheduledFlight> flights): void
    + buildCustomer(Customer customer): void
    + buildPrice(double price): void
    + buildPassengers(List<String> passengers): void
    + getResult(): FlightOrder
}

class Director {
    - Builder builder
    + Director(Builder builder)
    + constructOrder(List<String> passengers, List<ScheduledFlight> flights, double price, Customer customer): void
}

Order <|-- FlightOrder
Customer o-- Order : contains >
Customer o-- FlightOrder : creates >
FlightOrder o-- ScheduledFlight : contains >

Order o-- Passenger : contains >


Builder <|.. FlightOrderBuilder
Director ..> FlightOrderBuilder : Constructs >

Customer ..> FlightOrderBuilder : creates >
Customer ..> Director : creates >


@enduml
```

```Java
public FlightOrder createOrder(List<String> passengerNames, List<ScheduledFlight> flights, double price) {
        if (!isOrderValid(passengerNames, flights)) {
            throw new IllegalStateException("Order is not valid");
        }

        Builder builder = new FlightOrderBuilder();
        Director director = new Director(builder);
        director.constructOrder(passengerNames, flights, price, this);

        FlightOrder order = ((FlightOrderBuilder) builder).getResult();
  
        orders.add(order);
        return order;
    }
```

Here in 'Customer' class, the creation of flight order obeject is totally handled by 'FlightOrderBuilder' and 'Director' classes. The 'FlightOrderBuilder' class builds the 'FlightOrder' object step by step and 'Director' class constructs the 'FlightOrderBuilder' object and sets the attributes of 'FlightOrder' object using the 'FlightOrderBuilder' object.