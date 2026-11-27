# SAP Connector
![](https://github.com/bonitasoft/bonita-connector-sap/workflows/Build/badge.svg)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=bonitasoft_bonita-connector-sap&metric=alert_status)](https://sonarcloud.io/dashboard?id=bonitasoft_bonita-connector-sap)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-connector-sap?color=blue&label=Release)](https://github.com/bonitasoft/bonita-connector-sap/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.connectors/bonita-connector-sap.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.connectors%22%20AND%20a:%22bonita-connector-sap%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

## Compatibility

| Connector           | Bonita         | Java          |
|---------------------|----------------|---------------|
| 3.x (`master`)      | 12.0 and later | 17 and later  |
| 2.x (up to 2.0.3)   | 7.11 to 11.x   | 8/11 and later|

The connector definition (`sap-jco3-callfunction` 1.0.0) and its inputs/outputs are unchanged: on Bonita 12, processes
using a 2.x implementation must switch to a 3.x implementation.

## SAP Java Connector (JCo) installation

The connector calls SAP through the proprietary SAP Java Connector 3 (`sapjco3.jar` and its native library,
`libsapjco3.so` on Linux, `sapjco3.dll` on Windows). SAP does not allow redistributing it: it is **not** embedded in
the connector archive and must be downloaded from the SAP Support Portal and installed on every Bonita server.
The `sapjco-api` module of this repository is only a compile-time stub of the JCo API, it is never deployed.

`sapjco3.jar` must be in the Bonita server classpath, not in the process: the `SAPMonoDestinationDataProvider` used by
the connector is part of the Bonita runtime (`bonita-common`) and implements a JCo interface, and the JCo native library
can only be loaded once per JVM. On a Bonita 12 Tomcat bundle or Docker image:
- copy `sapjco3.jar` to `server/lib/bonita/` (`/opt/bonita/server/lib/bonita/` in the Docker image);
- copy the native library to a directory of the JVM `java.library.path` (for instance `server/lib/bonita/` added with
  `-Djava.library.path=...` in `setenv`), and use the JCo build matching the server OS and architecture;
- restart the server.

Without it, the connector fails with `NoClassDefFoundError: com/sap/conn/jco/JCoException`.

## Build

__Clone__ or __fork__ this repository, then at the root of the project run:

`./mvnw`

## Release

In order to create a new release:
- On the release branch, make sure to update the pom version (remove the -SNAPSHOT)
- Run the [Create release](https://github.com/bonitasoft/bonita-connector-sap/actions/workflows/release.yml) action, set the version to release as parameter
- Update the `master` with the next SNAPSHOT version.

Once this is done, update the [Bonita marketplace repository](https://github.com/bonitasoft/bonita-marketplace) with the new version of the connector.

## Contributing

We would love you to contribute, pull requests are welcome! Please see the [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## License

The sources and documentation in this project are released under the [GPLv2 License](LICENSE)
