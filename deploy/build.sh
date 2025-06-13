#!/bin/sh

mv ../appBrowser/.npmrc .npmrc
dos2unix ./build.bat
. ./build.bat
unix2dos ./build.bat
mv .npmrc ../appBrowser/.npmrc

gzip -f *.tar
