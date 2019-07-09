#!/bin/sh

name1="Isabelle-GUILLOU"
name2="Morgane-CAM"
release="D1"-$name1-$name2

rm -r $release 2> /dev/null
mkdir $release
cd ..
zip -r diaballik-bundle/$release/diaballik-D1-$name1-$name2.zip diaballik-doc
