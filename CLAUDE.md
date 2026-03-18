# Bonita SAP Connector

## Project Overview

- **Name**: Bonita SAP Connector
- **Artifact**: `org.bonitasoft.connectors:bonita-connector-sap-parent` (multi-module)
- **Version**: 2.0.4-SNAPSHOT
- **Description**: Bonita connector that calls SAP BAPI/RFC functions via SAP Java Connector (JCo 3). Supports Application Server and Message Server connection types, structured input/output table parameters, and optional BAPI transaction commit.
- **License**: GPL-2.0
- **Tech stack**: Java 11, Maven (multi-module), Bonita Engine 7.14.0, SAP JCo 3 (`sapjco3.jar` — proprietary, not in Maven Central), JUnit 5, Mockito, AssertJ, Logback

### Modules

| Module | Description |
|--------|-------------|
| `sapjco-api` | Thin compile-time API stub for SAP JCo 3 interfaces (`JCoDestination`, `JCoFunction`, etc.) — avoids dependency on the proprietary `sapjco3.jar` during compilation |
| `bonita-connector-sap` | The actual connector implementation (`SAPCallFunction`) |

## Build Commands

```bash
# Full build (from repo root, builds both modules)
./mvnw clean verify

# Skip tests
./mvnw clean verify -DskipTests

# Run tests only
./mvnw test

# Check license headers (validate phase)
./mvnw validate

# Apply/format license headers
./mvnw license:format

# Package connector ZIP (connector module)
./mvnw clean package

# Deploy to Maven Central (requires GPG key)
./mvnw clean deploy -Pdeploy
```

**Important**: The real `sapjco3.jar` (proprietary SAP library) must be provided at runtime inside the Bonita server's classpath. It is NOT bundled in the connector ZIP and is not available in any public Maven repository.

The build produces (in `bonita-connector-sap/target/`):
- `bonita-connector-sap-<version>.jar`
- `bonita-connector-sap-<version>-*.zip` — Bonita connector assembly

## Architecture

### Class hierarchy

```
AbstractConnector (bonita-common)
  └── SAPCallFunction                     # Single connector class

SAPMonoDestinationDataProvider            # Singleton JCo DestinationDataProvider;
                                          # registers/updates connection properties at connect()

# sapjco-api stubs (compile-time only):
JCoDestination, JCoFunction, JCoRepository, JCoParameterList,
JCoStructure, JCoTable, JCoRecord, JCoField, JCoContext,
JCoDestinationManager, JCoException,
DestinationDataProvider, Environment
```

### Key patterns

- **Lifecycle**: `connect()` registers the `SAPMonoDestinationDataProvider` singleton, obtains a `JCoDestination`, retrieves the `JCoRepository`, looks up the function template, and begins a `JCoContext`. `disconnect()` ends the `JCoContext`.
- **Single destination limitation**: Only one SAP destination name is active at a time (the `SAPMonoDestinationDataProvider` is a singleton). Concurrent executions may interfere; documented in source as a known limitation.
- **Input parameter encoding**: `INPUT_PARAMETERS` is a `List<List<Object>>` with 4 columns: `parameterType`, `tableName`, `parameterName`, `parameterValue`. Types: `input_single`, `input_structure`, `input_table`, `table_input`.
- **Output parameter encoding**: `OUTPUT_PARAMETERS` is a `List<List<String>>` with 3 columns: `parameterType`, `tableName`, `xpath`. Types: `output_single`, `output_structure`, `output_table`, `table_output`.
- **Commit on success**: Optional `BAPI_TRANSACTION_COMMIT` call after function execution when `commitOnSuccess=true`.
- **sapjco-api module**: Provides interface stubs so the connector compiles without the proprietary JAR; the real JCo classes are loaded at runtime via reflection by the JCo framework.

## Testing Strategy

- Framework: JUnit 5 + AssertJ + Mockito
- `SAPCallFunctionTest`: mocks `JCoDestination`, `JCoFunction`, `JCoParameterList`, `JCoRepository`, and `SAPMonoDestinationDataProvider` to test input/output parameter mapping without a real SAP system.
- `SAPUtil`: test helper for building mock JCo objects.
- No integration tests against a real SAP system are included.
- Coverage enforced via JaCoCo.
- SonarCloud project key: `bonitasoft_bonita-connector-sap`.

## Commit Message Format

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <short description>

[optional body]

[optional footer(s)]
```

Common types: `feat`, `fix`, `chore`, `refactor`, `test`, `docs`, `ci`.

Examples:
```
feat: support Message Server connection type
fix: handle null tableParameterList for input_table
chore: bump Bonita engine to 7.15.0
```

## Release Process

1. Remove `-SNAPSHOT` from `version` in the root `pom.xml` (applies to both modules via parent).
2. Update `sap-jco3.def.version` if the connector definition changed.
3. Commit: `chore: release X.Y.Z`.
4. Tag: `git tag X.Y.Z`.
5. Deploy to Maven Central: `./mvnw clean deploy -Pdeploy` (requires GPG key and Central credentials in `~/.m2/settings.xml`).
6. Push tag: `git push origin X.Y.Z`.
7. Bump to next `-SNAPSHOT` and commit: `chore: prepare next development iteration`.

> Note: The SAP JCo 3 proprietary library (`sapjco3.jar`) must be separately distributed and installed on target Bonita servers. It cannot be published to Maven Central.
