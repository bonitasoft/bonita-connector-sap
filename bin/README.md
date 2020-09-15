# SAP Connector

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=bonitasoft_bonita-connector-sap&metric=alert_status)](https://sonarcloud.io/dashboard?id=bonitasoft_bonita-connector-sap)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-connector-sap?color=blue&label=Release)](https://github.com/bonitasoft/bonita-connector-sap/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.connectors/bonita-connector-sap.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.connectors%22%20AND%20a:%22bonita-connector-sap%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

## Build

__Clone__ or __fork__ this repository

You must be a SAP client and add the [spajco3 jar dependency](https://help.sap.com/saphelp_nwpi711/helpdata/en/48/707c54872c1b5ae10000000a42189c/frameset.htm) in your local maven repository like this:

`> ./mvnw install:install-file -Dfile=path/to/sapjco3.jar -DgroupId=com.sap -DartifactId=sapjco -Dversion=3.0.3 -Dpackaging=jar`

Then run:

`> ./mvnw`

## Contributing

We would love you to contribute, pull requests are welcome! Please see the [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## License

The sources and documentation in this project are released under the [GPLv2 License](LICENSE)
