# Rose & Key Concierge — Backend Platform & Engineering Guide

A Spring Boot backend built for **Rose & Key**, a bespoke luxury travel concierge agency. The platform provides trip intake workflows, client and companion profiling, dynamic supplier gateways (Disney, Universal, Marriott), predictive marketing engines, and self-employment tax and commission accounting.

---

## 1. Architectural Design & Philosophy

The system adheres to Clean/Domain-Driven Architecture with distinct unidirectional boundaries:

```
                  ┌────────────────────────────────────────┐
                  │       REST Controllers / API Layer     │
                  │ (HTTP routing, validation, error maps) │
                  └───────────────────┬────────────────────┘
                                      │
                                      ▼
                  ┌────────────────────────────────────────┐
                  │         Application Services           │
                  │  (Transactions, orchestration, rules)  │
                  └─────────┬────────────────────┬─────────┘
                            │                    │
                            ▼                    ▼
     ┌────────────────────────────┐        ┌────────────────────────────┐
     │   Supplier Gateways &      │        │    Domain Entities &       │
     │   External Adapters        │        │    Persistence Repositories│
     │ (Disney, Universal, etc.)  │        │ (PostgreSQL / Spring Data) │
     └────────────────────────────┘        └────────────────────────────┘
```

### Core Design Rules
* **Strict Layer Isolation:** Controllers never speak directly to JPA Repositories; requests pass through dedicated Translators and Validators before hitting transactional Services.
* **PCI-DSS Compliance:** Raw credit card primary account numbers (PAN) never touch the persistence layer. The system exclusively accepts vaulted gateway tokens (`pm_*`, `cus_*`).
* **Clean Exception Handling:** Handled centrally by `GlobalExceptionHandler`, translating internal domain assertions and Jakarta validation failures into standardized RFC-compliant error payloads.

---

## 2. Core Modules & Domain Systems

```
com.goldgell.roseandkey
├── api
│   ├── advice        # Global exception interceptors
│   ├── controller    # REST endpoints (Intake, Clients, Suppliers, Bookings, Accounting, Marketing)
│   ├── request       # Immutable inbound DTO records with Jakarta Validation
│   └── response      # Outbound JSON projections
├── client
│   └── payment       # Vaulted payment gateway abstractions (StripePaymentGatewayClient)
├── config            # Infrastructure & bean factories (RestClientConfig)
├── domain
│   ├── accounting    # Commission ledger entries, Schedule C deductions, tax estimates
│   ├── booking       # Locked reservations and payment authorization audits
│   ├── intake        # Client travel requests, dates, passenger counts, resort tiers
│   ├── marketing     # Predictive crowd calendars and automated client outreach triggers
│   └── party         # Primary travelers and companions (age calculation, minor flags)
├── repository        # Spring Data JPA repositories
├── service           # Domain business engines and transaction orchestration
├── supplier
│   ├── adapter       # Dedicated vendor integrations (Disney, Universal, Marriott)
│   ├── gateway       # Strategy router managing rate queries and booking bridges
│   └── model         # Normalized rate options and reservation command envelopes
├── translation       # Bi-directional mapping between DTOs and persistent entities
└── validation        # Pure business invariant and date/arithmetic assertion engines
```

### Domain Highlights

1. **Trip Intake & Client Profiling (`domain.party`, `domain.intake`):**
   * Manages guest-to-registered account promotions.
   * Tracks companions and dynamically computes chronological age on departure date to establish minor status and ticket tier eligibility.

2. **Supplier Strategy Gateway (`supplier`):**
   * Normalizes vendor idiosyncrasies via `SupplierGateway`.
   * **Disney Destinations:** Auto-generates prefilled agent handoff bridges and cart payloads for travel agent portals.
   * **Marriott / Direct:** Executes direct reservation locks and hold confirmation codes.
   * **Universal Parks:** Builds dynamic packages with Halloween Horror Nights (HHN) seasonal support.

3. **Predictive Vacation Marketing (`service.marketing`):**
   * Evaluates crowd scoring, pricing tiers (`VALUE`, `MODERATE`, `PEAK`), and promotional probability matrices across seasonal travel windows (e.g., Disney post-marathon January lulls vs. September EPCOT shoulder drops).

4. **Accounting & Tax Ledger (`domain.accounting`):**
   * **Commission Accounting:** Tracks gross vendor commissions (10%–16%), host agency splits (e.g., 80/20 retainage), and disbursements pegged to travel completion dates.
   * **Tax Escrow Estimator:** Computes quarterly Schedule C taxable net after eligible deductible expenses (FAM trips, memberships, supplies) and projects federal self-employment tax (15.3% FICA/Medicare base) and income tax reserves.

---

## 3. Testing Architecture & Standards

The project follows a **1:1 class-to-test parity model** using an isolated hierarchical pattern:

```
src/test/java/...
├── Test_ClassName.java                 # Abstract test fixture (mocks, setup, test context)
└── Test_ClassName_methodUnderTest.java # Concrete isolated runner for a single method/behavior
```

### Testing Rules
* **No Kitchen-Sink Tests:** Endpoint methods and complex service operations are isolated into dedicated classes rather than clustered under single large test files.
* **Repository Tests (`@DataJpaTest`):** Execute against an in-memory, PostgreSQL-compatible H2 database with strict schema validation.
* **Controller Tests (`MockMvc`):** Configured via standalone setups with the explicit `GlobalExceptionHandler` registered to verify HTTP status codes, validation constraints, and JSON payload contracts without server boot overhead.
* **Deterministic Unit Tests:** Pure unit tests use Mockito with zero Spring context overhead to ensure fast test cycles.

---

## 4. Setup, Configuration & Running Tests

### Prerequisites
* **Java:** OpenJDK 21
* **Build Tool:** Gradle 8.14+ (via included wrapper `gradlew.bat`)
* **OS:** Windows 10/11 or POSIX compliant shell

### Test Configuration (`src/test/resources/application.properties`)
```properties
spring.datasource.url=jdbc:h2:mem:roseandkey_test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```

### Executing Tests in Windows PowerShell

Run the entire test suite:
```powershell
.\gradlew.bat test
```

Run an isolated test package (e.g., Accounting Service):
```powershell
.\gradlew.bat test --tests "com.goldgell.roseandkey.service.accounting.*"
```

Run a single method runner:
```powershell
.\gradlew.bat test --tests "com.goldgell.roseandkey.service.accounting.Test_TravelAgencyAccountingService_recordBookingCommission"
```

### Windows File Lock Prevention
`build.gradle` is configured with:
* `forkEvery = 1` to cleanly terminate test workers immediately upon completion.
* `-Dsun.zip.disableMemoryMapping=true` and `-Dsun.io.useCanonCaches=false` to prevent the JVM and NTFS from locking binary report files in `build\test-results`.
* `org.gradle.vfs.watch=false` in `gradle.properties` to disable Windows file system watcher collisions.

---

## 5. API Reference Summary

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/intakes` | Submit client vacation intake and traveler companions |
| `PATCH`| `/api/v1/intakes/{id}/promote` | Promote guest profile to registered client |
| `GET`  | `/api/v1/clients/{clientId}` | Retrieve profile details and companion history |
| `POST` | `/api/v1/clients/{clientId}/companions` | Add companion traveler with age calculations |
| `PUT`  | `/api/v1/clients/{clientId}/payment-vault` | Attach PCI vaulted payment token |
| `POST` | `/api/v1/suppliers/rates` | Multi-vendor rate query (Disney, Universal, Marriott) |
| `POST` | `/api/v1/suppliers/handoff/{intakeId}` | Generate deep-link auto-fill package for vendor agent portal |
| `POST` | `/api/v1/bookings/lock` | Authorize hold/lock on resort package and persist booking record |
| `POST` | `/api/v1/marketing/predictive/windows` | Forecast optimal travel windows, crowd scores, and discounts |
| `POST` | `/api/v1/accounting/expenses` | Log Schedule C deductible expense with receipt reference |
| `GET`  | `/api/v1/accounting/taxes/quarterly` | Generate quarterly 1040-ES estimated tax report |
