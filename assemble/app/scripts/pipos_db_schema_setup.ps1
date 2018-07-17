$INSTALL_HOME=(Split-Path $MyInvocation.MyCommand.Path -Parent)

. .\common_functions.ps1

Function preSchemaSetup(){
    $installConf = $INSTALL_HOME+"/confs/pi_pos.conf"
    $clientSpecificSQL = $INSTALL_HOME+"/sqls/V51__Insert_Seed_Data.sql"
    $mavenPOM = $INSTALL_HOME+"/confs/db_pom.xml"
    $mavenDBPOM = $INSTALL_HOME+"/db_schema/pom.xml"

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

    if (Test-Path $mavenPOM -PathType Leaf) {
        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $mavenPOM $Key.name $Key.Value
        }

        Copy-Item -Path $mavenPOM -Destination $mavenDBPOM
        Write-Host "FLYWAY MAVEN POM template file is ready for use"
    }
    else {
        Write-Host "FLYWAY MAVEN POM template file is missing"
        exit 1
    }

    $clientSpecificSQLDest=$INSTALL_HOME+"/db_schema/src/main/resources/db/migration/"
    if (Test-Path $clientSpecificSQL -PathType Leaf) {
        Foreach ($Key in ($global:confs.GetEnumerator())) {
            replaceTokens $clientSpecificSQL $Key.name $Key.Value
        }

        Copy-Item -Path $clientSpecificSQL -Destination $clientSpecificSQLDest
        Write-Host "The Customer specific SEED_DATA has been created successfully"
    }
    else {
        Write-Host "The Customer specific SEED_DATA creation has failed"
        exit 1
    }

    $mavenDB_MIGRATE_HOME = $INSTALL_HOME+"/db_schema/"
    Set-Location $mavenDB_MIGRATE_HOME

    $env:JAVA_HOME=$PIPOSHome+"/utils/jre/"
    $env:MVN_HOME=$INSTALL_HOME+"/tools/maven/"
    $FlywayMigrateCmd=$env:MVN_HOME+"/bin/mvn"
    $FlywayMigrateCmdArgs="flyway:migrate"

    Start-Process $FlywayMigrateCmd -ArgumentList $FlywayMigrateCmdArgs -Wait
    if($LASTEXITCODE -eq 0){
        Write-Host "FLYWAY MIGRATION for PI POS DB is a success!!"
    }else{
        Write-Host "FLYWAY MIGRATION for PI POS DB has failed, please check the logs!!"
        exit 1
    }
}


preSchemaSetup
Set-Location $INSTALL_HOME