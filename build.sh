#!/bin/bash
# Build script for teda library.
# Sets DOCKER_HOST to the Podman socket so Testcontainers can find it,
# then runs mvn install. Pass any extra Maven args directly:
#   ./build.sh -DskipTests
#   ./build.sh -Pforce-jaxb

export DOCKER_HOST="unix:///run/user/$(id -u)/podman/podman.sock"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

exec mvn -f "$SCRIPT_DIR/pom.xml" install "$@"
