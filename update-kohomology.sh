#! /bin/bash

set -eu

if [ -z "${1-}" ]; then
    echo "Usage: ./update-kohomology.sh 99.9"
    exit 1
fi

version=$1

for d in kohomology-browser kohomology-cli kohomology-react; do
    kts=$d/build.gradle.kts
    echo sed -i 's/implementation("com.github.shwaka.kohomology:kohomology:.*")$/implementation("com.github.shwaka.kohomology:kohomology:'$version'")/' $kts
    sed -i 's/implementation("com.github.shwaka.kohomology:kohomology:.*")$/implementation("com.github.shwaka.kohomology:kohomology:'$version'")/' $kts
done
