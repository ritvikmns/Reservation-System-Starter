# Refactoring Payment Processing with the Strategy Design Pattern


- ASSUMPTION - We haven't touched the tests and since tests call some methods directly which become useless after introducing strategy, we have kept those methods and called the centralised **(processOrder)** method in those primitive functions. Further implementations of payment strategy will be added without such methods.
## 1. Introduction
The original implementation of `FlightOrder` tightly coupled payment processing logic with the class itself. It contained separate methods for **credit card** and **PayPal** payments, making the system less flexible and harder to extend.

To improve **maintainability**, **scalability**, and **code reusability**, we introduced the **Strategy Pattern**, which allows different payment methods to be used interchangeably without modifying the `FlightOrder` class.

## 2. Key Changes

### A. Extracted Payment Logic into a Strategy Interface
We introduced a `PaymentStrategy` interface, which acts as a contract for different payment methods. Each payment method now implements this interface, ensuring all payments follow a common structure.

### B. Refactored Payment Methods
- Instead of separate `processOrderWithCreditCard()` and `processOrderWithPayPal()` methods, we now have a single `processOrder(PaymentStrategy paymentStrategy)`.
- The `FlightOrder` class no longer handles payment validation and transaction logic. It delegates these tasks to the respective **payment strategy**.
- This removes redundant logic and makes the class **more focused on order management** rather than payment processing.
### C. Encapsulated Payment Methods
- `CreditCard` and `PayPal` now implement `PaymentStrategy`.
- Each class handles its own validation and payment logic, making them more **self-contained**.
- If payment fails, it is now handled in a more centralized way.

---

## 3. Adding New Payment Methods
With the Strategy Pattern in place, adding a new payment method is **straightforward**.

### Steps to Add a New Payment Method (e.g., Google Pay)
1. **Create a New Class**
    - Implement the `PaymentStrategy` interface.
    - Add relevant fields like account details and validation logic.

2. **Implement the `pay(double amount)` Method**
    - Define the payment logic inside the method.
    - Ensure it returns `true` if the payment is successful.

3. **Use in FlightOrder**
    - Instantiate the new payment method in `processOrder(PaymentStrategy paymentStrategy)`.
    - Now `FlightOrder` can accept Google Pay without any modifications to its core logic.
    - This makes the system **extensible** and **maintainable**.
    - The new payment method can be easily integrated into the system.
   