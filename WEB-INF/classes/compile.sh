#!/bin/bash

javac -cp ".:../../../../lib/servlet-api.jar:../../../../lib" ../src/utils/*.java -d .
javac -cp ".:../../../../lib/servlet-api.jar:../../../../lib" ../src/modele/dto/*.java -d .
javac -cp ".:../../../../lib/servlet-api.jar:../../../../lib" ../src/modele/dao/*.java -d .
javac -cp ".:../../../../lib/servlet-api.jar:../../../../lib" ../src/controleur/*.java -d .