# CertificateService
ACME-compliant certificate service

This is an MVC Spring Boot application which exposes a REST API. Clients make a REST call to /cert/{domain} to request a
certificate. This service inturn contacts an external service to retrieve/create/renew certificates. 

The application uses an in-memory database hsqldb as a cache to store the certificates.

Design Assumptions:
1] The client sends the domain-name as a URL argument.
2] The certificates for a given domain are cached in the DB for a configurable amout of days. Currently hardcoded to 30 days.
3] If the certificate exists in DB cache, then the service checks if the cache is valid and the certificates are not expired.
4] If cache is invalid it refreshes the certifacte from external service & updates cache.
5] If certificate is expired it renews the certifacte from external service & updates cache.
6] External service returns the certifcate along with the expiration date.

This application runs as Spring Boot Application.



