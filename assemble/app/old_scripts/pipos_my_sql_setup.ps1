$INSTALL_HOME=(Split-Path $MyInvocation.MyCommand.Path -Parent)

. .\common_functions.ps1

Function initializeMySQLInstance([string]$mysqlHome) {
    $mysqldProcess = $mysqlHome + "/bin/mysqld"
    $mysqldInitializeCmd = $mysqldProcess + " --initialize-insecure"
    Invoke-Expression $mysqldInitializeCmd
    if ($LASTEXITCODE -eq 0) {
        Write-Host "MySQL instance initialization was a success"
    }
    else {
        Write-Host "MySQL instance initialization has failed, check mysql logs for more details"
        exit 1
    }

    $mysqldServiceName = "PI-POS-DB"
    $mysqldServiceCmd = $mysqldProcess + " --install " + $mysqldServiceName
    Invoke-Expression $mysqldServiceCmd
    if ($LASTEXITCODE -eq 0) {
        Write-Host "PI-POS MYSQL setup as windows service a success"
    }
    else {
        Write-Host "PI-POS MYSQL setup as windows service has failed"
        exit 1
    }

    startPIPOSService $mysqldServiceName
}

Function executeMySQLScripts([string]$mysqlHome) {
    $mysqlProcess = $mysqlHome + "/bin/mysql"

    $sqlSetupFile = $INSTALL_HOME+"/sqls/initial_setup.sql"
    if (Test-Path $sqlSetupFile -PathType Leaf) {

        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $sqlSetupFile $Key.name $Key.Value
        }

        get-content $sqlSetupFile | &$mysqlProcess -u root --skip-password

        if ($LASTEXITCODE -eq 0) {
            Write-Host "PI-POS MYSQL initial DB setup (Schema Creation) is a success"
        }
        else {
            Write-Host "PI-POS MYSQL initial DB setup (Schema Creation) has failed"
            exit 1
        }

    }
    else {
        Write-Host "The initial setup SQL file to create DB schema is missing"
        exit 1
    }

}

Function installPIPOSDB() {
    $installConf = $INSTALL_HOME+"/confs/pi_pos.conf"
    $mysqlConfTemplate = $INSTALL_HOME+"/confs/my_sql.ini"


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

    # Replace tokens in mysql configuration file
    $mysqlHome = $PIPOSHome + "/utils/mysql/"
    $mysqlConf = $PIPOSHome + "/utils/mysql/my.ini"

    if (Test-Path $mysqlConfTemplate -PathType Leaf) {
        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $mysqlConfTemplate $Key.name $Key.Value
        }

        Copy-Item -Path $mysqlConfTemplate -Destination $mysqlConf

        Write-Host "My SQL configuration file tokens has been replace and is ready for setup"
    }
    else {
        Write-Host "My SQL configuration file template is missing"
        exit 1
    }


    initializeMySQLInstance $mysqlHome
    executeMySQLScripts $mysqlHome

}

installPIPOSDB
Set-Location $INSTALL_HOME