@echo off
rem This is a startup script for the EdisoftXMLProcessor project.
rem It can be used in Apache Ant task or on its own.
rem Target OS is MS Windows.
rem For other OS write an appropriate script.

rem Use of "chcp" command is for convenience: useful in case of outputting data encoded in UTF-8 to Windows console
start cmd /k "chcp 65001>nul & java -Dfile.encoding=UTF8 -jar EdisoftXMLProcessor.jar"