#!/bin/bash
echo "========================================"
echo "QuickDoctor Appointment System"
echo "========================================"
echo ""

echo "Compiling Java files..."
javac -d bin -encoding UTF-8 src/com/quickdoctor/model/*.java src/com/quickdoctor/util/*.java src/com/quickdoctor/dao/*.java src/com/quickdoctor/service/*.java src/com/quickdoctor/ui/*.java src/com/quickdoctor/Main.java

if [ $? -eq 0 ]; then
    echo ""
    echo "Compilation successful!"
    echo ""
    echo "Starting QuickDoctor..."
    echo ""
    java -cp bin com.quickdoctor.Main
else
    echo ""
    echo "Compilation failed! Please check for errors."
fi
