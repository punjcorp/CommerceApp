$INSTALL_HOME=(Split-Path $MyInvocation.MyCommand.Path -Parent)

. .\common_functions.ps1

Function piposAppSetup(){
    $installConf = $INSTALL_HOME+"/confs/pi_pos.conf"
    $piposAppConf = $INSTALL_HOME+"/confs/application.properties"
    $piposAppBatchFile = $INSTALL_HOME+"/confs/app_run.bat"
    $piposAppServiceConf = $INSTALL_HOME+"/confs/pi_pos_app.xml"

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

    $piposAppConfDest =$PIPOSHome +"/pi_pos_app/config/"
    if (Test-Path $piposAppConf -PathType Leaf) {
        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $piposAppConf $Key.name $Key.Value
        }

        Copy-Item -Path $piposAppConf -Destination $piposAppConfDest

        Write-Host "PI-POS application configuration's tokens has been replaced and the file is ready for usage"
    }
    else {
        Write-Host "PI-POS application configuration file template file is missing"
        exit 1
    }

    $piposAppBatchDest =$PIPOSHome +"/pi_pos_app/bin/run.bat"
    if (Test-Path $piposAppBatchFile -PathType Leaf) {
        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $piposAppBatchFile $Key.name $Key.Value
        }

        Copy-Item -Path $piposAppBatchFile -Destination $piposAppBatchDest

        Write-Host "PI-POS application batch file's tokens has been replaced and the file is ready for usage"
    }
    else {
        Write-Host "PI-POS application configuration file template file is missing"
        exit 1
    }

    $piposServiceConfDest =$PIPOSHome +"/pi_pos_app/bin/pi_pos_app.xml"
    if (Test-Path $piposAppServiceConf -PathType Leaf) {
        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $piposAppServiceConf $Key.name $Key.Value
        }

        Copy-Item -Path $piposAppServiceConf -Destination $piposServiceConfDest

        Write-Host "PI-POS Service utility configuration file has been created successfully"
    }
    else {
        Write-Host "PI-POS Service utility configuration template file is missing"
        exit 1
    }

    # Setup the application as a windows service now
    $piposAppServiceName="PI-POS-APP"
    $piposAppServiceEXE =$PIPOSHome +"/pi_pos_app/bin/pi_pos_app.exe install"

    Invoke-Expression $piposAppServiceEXE
    if($LASTEXITCODE -eq 0){
        Write-Host "PI-POS-APP application service has been setup successfully"
    }else{
        Write-Host "PI-POS-APP was not setup as windows service"
        exit 1
    }

    startPIPOSService $piposAppServiceName

}


piposAppSetup
Set-Location $INSTALL_HOME