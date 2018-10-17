<#
    This file will install and setup PI-POS-APP in any machine. It does the following steps:
    1. The MySQL database installation
    2. The PI-POS-DB schema creation and Seed data updates
    3. The PIS-POS-APP setup as windows service
    4. The installation scripts cleanup
#>

$INSTALL_HOME=(Split-Path $MyInvocation.MyCommand.Path -Parent)

. .\common_functions.ps1

$installConf = $INSTALL_HOME+"/confs/pi_pos.conf"
$PIPOS_APP_INSTALLABLE = $INSTALL_HOME+"/tools/pi_pos_app"
$PIPOS_UTIL_INSTALLABLE = $INSTALL_HOME+"/tools/utils"

$global:confs = loadProperties $installConf
if ( $null -eq $global:confs -or $global:confs.count -eq 0) {
    Write-Host "The PI-POS application configuration file is empty!!"
    exit 1
}

$PIPOSHome = $global:confs.PI_POS_T_HOME
if ([string]::IsNullOrEmpty($PIPOSHome)) {
    Write-Host "The PI-POS application home configuration is missing"
    exit 1
}

if (Test-Path $PIPOS_APP_INSTALLABLE -PathType Container) {
    Write-Host "PI-POS-APP Installables found!"}
else {
    Write-Host "PI-POS-APP Installables is missing"
    exit 1
}
if (Test-Path $PIPOS_UTIL_INSTALLABLE -PathType Container) {
    Write-Host "PI-POS-APP Installables found!"}
else {
    Write-Host "PI-POS-APP Installables is missing"
    exit 1
}

Write-Host "========================================================================================="
Write-Host "Starting Pre Tasks for PI-POS-APP"
Write-Host "========================================================================================="
New-Item -Path $PIPOSHome  -ItemType Directory
Copy-Item -Path $PIPOS_APP_INSTALLABLE -Destination $PIPOSHome -recurse -Force
Copy-Item -Path $PIPOS_UTIL_INSTALLABLE -Destination $PIPOSHome -recurse -Force


invoke-expression -Command .\pipos_my_sql_setup.ps1
if ($LASTEXITCODE -eq 0) {
    Write-Host "========================================================================================="
    Write-Host "The PI-POS MySQL database installation is successful!! The database is up and running!!"
    Write-Host "========================================================================================="
}
else {
    Write-Host "The PI-POS MySQL setup has failed!!"
    exit 1
}

Set-Location $INSTALL_HOME
invoke-expression -Command .\pipos_db_schema_setup.ps1
if ($LASTEXITCODE -eq 0) {
    Write-Host "=============================================================================================================="
    Write-Host "The PI-POS database migration (Schema + Seed Data) is successful!! The database is ready for application!!"
    Write-Host "=============================================================================================================="
}
else {
    Write-Host "The PI-POS database migration has failed!!"
    exit 1
}

Set-Location $INSTALL_HOME
invoke-expression -Command .\pipos_app_setup.ps1
if ($LASTEXITCODE -eq 0) {
    Write-Host "========================================================================================="
    Write-Host "The PI-POS-APP installation is successful!! The app is up and running!!"
    Write-Host "========================================================================================="
}
else {
    Write-Host "The PI-POS APP installation has failed!!"
    exit 1
}


Write-Host "========================================================================================="
Write-Host "=== PI-POS Setup has completed, Access application using http://localhost:8080/ now!! ==="
Write-Host "========================================================================================="