call .\runcrud
if "%ERRORLEVEL%" == "0" goto nextstep
echo.
echo .\runcrud has errors – breaking work
goto fail

:nextstep
start chrome http://localhost:8080/crud/v1/tasks
if "%ERRORLEVEL%" == "0" goto theend
echo Cannot open website
goto fail

:theend
echo.
echo My work is done here.

:fail
echo.
echo There were errors