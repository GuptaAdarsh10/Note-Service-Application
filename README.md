1. What responsibilities belong in Controller, Service, and Repository layers?
   The controller handles HTTP requests and responses and does basic input mapping.
   The service contains business logic and validations.
   The repository is responsible only for database operations like save, update, and fetch.

2. What is a database transaction? When should @Transactional be used or avoided?
   A database transaction ensures multiple operations either all succeed or all fail together.
   @Transactional should be used when data consistency is important, like create or update flows.
   It should be avoided in read-only or external API–only operations.

3. Why are DTOs used instead of returning JPA entities directly from APIs?
   DTOs prevent exposing internal database structure to clients.
   They help control what data is sent over the API and avoid lazy loading issues.
   DTOs also make APIs more stable when entity structures change.

4. What problem does Liquibase solve in a multi-developer / multi-environment setup?
   Liquibase keeps database schema changes versioned and consistent across environments.
   It ensures every developer and server runs the same database structure.
   It also helps track who changed what and when.