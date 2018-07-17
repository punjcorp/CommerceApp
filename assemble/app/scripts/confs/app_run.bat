@ECHO OFF

set PI_POS_HOME="PI_POS_T_HOME"

set JAVA_HOME="PI_POS_T_HOME/utils/jre/"
set PATH=%PATH%;%JAVA_HOME%/bin/;.;

set PI_POS_APP_HOME=%PI_POS_T_HOME%/pi_pos_app/
cd %PI_POS_APP_HOME%

ECHO start PI-POS-APP
start "PI-POS-APP" java -jar lib\pi_pos_app.jar
