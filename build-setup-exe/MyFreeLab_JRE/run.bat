@echo off
:: Path de ejecucion
:: .\lib\jre\bin\javaw -Xrs -jar ./MyFreeLab.jar

:: Run MyFreeLab.jar
:: Estos funcionan bien sin utilizar variables de entorno
:: START "" .\lib\jre\bin\javaw -Xrs -jar ./MyFreeLab.jar

:: Esto necesita agregar el \lib\jre\bin\ al PATH
 START "" javaw -Xrs -jar ./MyFreeLab.jar

:: Esto necesita crear MFL_JRE_PATH y MFL_INSTALL_PATH
::cd %MFL_JRE_PATH%\
:: START "" javaw -jar "%MFL_INSTALL_PATH%\MyFreeLab.jar"

EXIT
