#!/bin/sh

name1="Isabelle-GUILLOU"
name2="Morgane-CAM"
release="FINAL"-$name1-$name2

# cleaning the release files
rm -r $release 2> /dev/null
# compiling and testing the back-end
cd ../diaballik-model
mvn clean package
# building the frontend
cd ../diaballik-frontend
ng build --prod --build-optimizer
# creating release files
cd ../diaballik-bundle
mkdir $release
cd ..
zip -r diaballik-bundle/$release/diaballik-$name1-$name2-sources.zip diaballik-doc diaballik-model diaballik-frontend/ -x diaballik-model/target/\* lib-algo/target/\* diaballik-frontend/dist/\* diaballik-frontend/node_modules/\* \*.idea/\* \*.iml
cd diaballik-bundle/
