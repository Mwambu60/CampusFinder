#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac -d out java/src/campusfinder/*.java
exec java -cp out campusfinder.CampusFinderServer
