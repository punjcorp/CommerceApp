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

Write-Host "========================================================================================="
Write-Host "======================== Starting Uninstallation for PI-POS-APP ========================="
Write-Host "========================================================================================="
$PIPOS_APP_SERVICE_NAME="PI-POS-APP"
$PIPOS_DB_SERVICE_NAME="PI-POS-DB"

stopPIPOSService $PIPOS_APP_SERVICE_NAME
# if ($LASTEXITCODE -ne 0) {
#     Write-Host "The PI-POS APP Service Stop Process has failed!!"
#     exit 1
# }
$JAVA_ACTUAL_PATH=$PIPOSHome+"utils/jre/bin/java.exe"
$JAVA_ACTUAL_PATH = $JAVA_ACTUAL_PATH -replace '/','\'
Write-Host "Java process $JAVA_ACTUAL_PATH"

$JAVA_P_ID=Get-Process | where-object  { $_.Path -eq  $JAVA_ACTUAL_PATH} | Select-Object Id
Write-Host "Java process ID $JAVA_P_ID"

if([string]::IsNullOrEmpty($JAVA_P_ID)){
    Write-Host "The PI-POS App JAVA Process is not running."
}else{
    Stop-Process -Id $JAVA_P_ID.Id -Force
    if ($LASTEXITCODE -ne 0) {
        Write-Host "The PI-POS App JAVA Process Stopping has failed, please contact administrator!!"
        exit 1
    }
}

stopPIPOSService $PIPOS_DB_SERVICE_NAME
# if ($LASTEXITCODE -ne 0) {
#     Write-Host "The PI-POS DB Service Stop Process has failed!!"
#     exit 1
# }

$SC_EXE="SC.EXE"
$SC_APP_EXE_ARGS="DELETE $PIPOS_APP_SERVICE_NAME"
$SC_DB_EXE_ARGS="DELETE $PIPOS_DB_SERVICE_NAME"

Start-Process $SC_EXE -ArgumentList $SC_APP_EXE_ARGS -Wait
if($LASTEXITCODE -ne 0){
    Write-Host "Pi-POS-APP Service deleteion has failed!!"
    exit 1
}

Start-Process $SC_EXE -ArgumentList $SC_DB_EXE_ARGS -Wait
if($LASTEXITCODE -ne 0){
    Write-Host "Pi-POS-DB Service deleteion has failed!!"
    exit 1
}

Remove-Item $PIPOSHome  -Recurse -Force
if ($LASTEXITCODE -eq 0) {
    Write-Host "========================================================================================="
    Write-Host "The PI-POS installed files has been deleted successfully!!"
    Write-Host "========================================================================================="
}
else {
    Write-Host "The PI-POS installation deletion has failed!!"
    exit 1
}

Write-Host "========================================================================================="
Write-Host "============================ PI-POS Uninstall has completed!! ==========================="
Write-Host "========================================================================================="