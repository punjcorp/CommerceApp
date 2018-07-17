$global:confs = $null
<#
This function should read the installation configuration file
for PI-POS application which will be used for application
configuration files
#>
Function loadProperties ([string]$path) {
    $PropertyFilePath = $path
    if (Test-Path $path -PathType Leaf) {
        $RawProperties = Get-Content $PropertyFilePath;
        $PropertiesToConvert = ($RawProperties -replace '\\', '\\') -join [Environment]::NewLine;
        $Properties = ConvertFrom-StringData $PropertiesToConvert;
        Write-Host "The PI-POS application configuration file was read successfully"
        return $Properties
    }
    else {
        Write-Host "The PI-POS application configuration file is missing!!"
        exit 1
    }

}

Function replaceTokens([string]$fileName, [string]$tokenName, [string]$tokenValue) {
    (Get-Content $fileName).Replace($tokenName, $tokenValue) | Set-Content $fileName
}

Function startPIPOSService([string]$ServiceName) {
    $arrService = Get-Service -Name $ServiceName
    $arrServiceStatus = (Get-Service -Name $ServiceName).Status
    $attempts=1

    Start-Service $ServiceName
    write-host "$ServiceName Service Current Status $arrServiceStatus"
    Start-Sleep -seconds 30
    while ($arrService.Status -ne 'Running' -or $attempts -lt 4) {
        write-host "$ServiceName Service starting.."
        $arrService.Refresh()
        if ($arrService.Status -eq 'Running') {
            Write-Host "$ServiceName Service is now Running."
            break
        }
        Start-Sleep -seconds 30
        $attempts++
    }
    if($arrService.Status -ne 'Running'){
        write-host "$ServiceName could not be start after waiting for 2 minutes, please contact administrator!!"
    }
}


Function stopPIPOSService([string]$ServiceName) {
    $arrService = Get-Service -Name $ServiceName
    $arrServiceStatus = (Get-Service -Name $ServiceName).Status
    $attempts=1

    Stop-Service $ServiceName
    write-host "$ServiceName Service Current Status $arrServiceStatus"
    Start-Sleep -seconds 30
    while ($arrService.Status -ne 'Stopped' -or $attempts -lt 4) {
        write-host "$ServiceName Service stopping.."
        $arrService.Refresh()
        if ($arrService.Status -eq 'Stopped') {
            Write-Host "$ServiceName Service has now Stopped."
            break
        }
        Start-Sleep -seconds 30
        $attempts++
    }
    if($arrService.Status -ne 'Stopped'){
        write-host "$ServiceName could not be stopped after waiting for 2 minutes, please contact administrator!!"
    }
}