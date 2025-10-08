# Maven Publishing Guide

This document describes how to publish the Bonita SAP Connector to Maven Central.

## Overview

The Bonita SAP Connector is published to Maven Central using the [Maven Central Portal](https://central.sonatype.com/). This replaced the legacy OSSRH service which reached end-of-life on June 30th, 2025.

**Published Artifact:**
- Group ID: `org.bonitasoft.connectors`
- Artifact ID: `bonita-connector-sap`
- Maven Central: [![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.connectors/bonita-connector-sap.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.connectors%22%20AND%20a:%22bonita-connector-sap%22)

## Prerequisites

To publish a new version of the connector, you need:

1. **Maven Central Account**: Access to the Bonitasoft organization on Maven Central Portal
2. **GPG Keys**: A GPG key pair for signing artifacts
3. **GitHub Repository Access**: Write access to the repository
4. **GitHub Secrets**: The following secrets must be configured in the repository:
   - `KSM_CONFIG`: Keeper Security Manager configuration for accessing secrets
   - Maven Central credentials (managed via Keeper Security Manager)
   - GPG signing credentials (managed via Keeper Security Manager)

## Publishing Process

### 1. Prepare the Release

Before publishing, ensure the version is ready:

1. **Update the version** in `bonita-connector-sap/pom.xml` and `pom.xml` (root):
   - Remove the `-SNAPSHOT` suffix
   - Ensure the version follows semantic versioning (e.g., `2.0.4`)

2. **Commit the version change** to a release branch:
   ```bash
   git checkout -b release/2.0.4
   # Update pom.xml files
   git add pom.xml bonita-connector-sap/pom.xml
   git commit -m "chore: prepare release 2.0.4"
   git push origin release/2.0.4
   ```

### 2. Trigger the Release Workflow

The release is automated through GitHub Actions:

1. Navigate to the [Release workflow](https://github.com/bonitasoft/bonita-connector-sap/actions/workflows/release.yml)
2. Click "Run workflow"
3. Select the release branch (e.g., `release/2.0.4`)
4. Enter the version to release (e.g., `2.0.4`)
5. Click "Run workflow"

The workflow will:
- Build the project
- Run tests
- Sign artifacts with GPG
- Deploy to Maven Central
- Create a GitHub release with attached artifacts

### 3. Verify the Release

After the workflow completes:

1. **Check Maven Central**: Verify the artifact appears at https://central.sonatype.com/artifact/org.bonitasoft.connectors/bonita-connector-sap
   - Note: It may take a few minutes for the artifact to appear on search.maven.org

2. **Check GitHub Release**: Verify the release appears at https://github.com/bonitasoft/bonita-connector-sap/releases

### 4. Post-Release Steps

1. **Update master branch** with the next SNAPSHOT version:
   ```bash
   git checkout master
   # Update pom.xml files to next version (e.g., 2.0.5-SNAPSHOT)
   git add pom.xml bonita-connector-sap/pom.xml
   git commit -m "chore: prepare next development iteration"
   git push origin master
   ```

2. **Update Bonita Marketplace**: If applicable, update the [Bonita marketplace repository](https://github.com/bonitasoft/bonita-marketplace) with the new version.

## Maven Configuration

### POM Configuration

The project uses the following Maven plugins for publishing:

1. **central-publishing-maven-plugin** (v0.8.0): Handles deployment to Maven Central Portal
   - Configuration: `autoPublish: true` (artifacts are automatically published after deployment)

2. **maven-gpg-plugin** (v3.1.0): Signs artifacts with GPG
   - Activated via the `deploy` profile
   - Uses `--pinentry-mode loopback` for non-interactive signing

3. **maven-source-plugin** (v3.3.0): Generates source JAR
4. **maven-javadoc-plugin** (v3.6.3): Generates Javadoc JAR

### Deploy Profile

The `deploy` profile is activated during the release process and includes:
- GPG artifact signing
- Deployment to Maven Central

To test deployment locally (requires proper credentials):
```bash
./mvnw clean deploy -Pdeploy
```

## Troubleshooting

### Common Issues

1. **GPG Signing Fails**
   - Ensure GPG keys are properly configured in the CI/CD environment
   - Verify the GPG passphrase is correct in the secrets

2. **Maven Central Deployment Fails**
   - Check that Maven Central credentials are valid
   - Verify the artifact meets Maven Central requirements (POM completeness, signatures, etc.)
   - Check the [Maven Central Portal status page](https://status.central.sonatype.com/)

3. **Artifact Not Appearing on Maven Central**
   - Wait a few minutes for synchronization
   - Check the deployment status on the Maven Central Portal
   - Verify `autoPublish: true` is set in the POM

### Manual Deployment

In case of issues with the automated workflow, you can deploy manually:

1. Configure Maven settings with credentials
2. Run the deploy command:
   ```bash
   ./mvnw clean deploy -Pdeploy
   ```

## References

- [Maven Central Portal Documentation](https://central.sonatype.org/publish/)
- [Maven Central Portal](https://central.sonatype.com/)
- [Reusable Release Workflow](.github/workflows/release.yml)
- [Maven Publishing Plugin Documentation](https://central.sonatype.org/publish/publish-portal-maven/)

## Migration from OSSRH

The project was migrated from OSSRH to Maven Central Portal in commit [b7bea5d](https://github.com/bonitasoft/bonita-connector-sap/commit/b7bea5dd8a945cb6d008b06e60b1e7e108f8b95d). The key changes were:

- Replaced `nexus-staging-maven-plugin` with `central-publishing-maven-plugin`
- Updated deployment URLs and authentication
- Simplified the deployment process with auto-publishing
