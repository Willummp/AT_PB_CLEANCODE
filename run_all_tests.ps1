$ErrorActionPreference = "Stop"
$Timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$LogFile = "$PWD\auditoria_testes_$Timestamp.log"

Start-Transcript -Path $LogFile

Write-Host "========================================"
Write-Host "🚀 Starting Full Project Health Check (Windows)"
Write-Host "📅 Date: $(Get-Date)"
Write-Host "📄 Log File: $LogFile"
Write-Host "========================================"

function Run-Test {
    param (
        [string]$ProjectName,
        [string]$Directory,
        [string]$Command
    )

    Write-Host "`n----------------------------------------"
    Write-Host "Testing $ProjectName..."
    Write-Host "Directory: $Directory"
    Write-Host "Command: $Command"
    Write-Host "----------------------------------------"

    Push-Location $Directory
    try {
        # Execute the command. 
        # Using cmd /c to ensure maven (mvn.cmd) is found correctly in path if it's a batch file.
        # But direct invocation often works if mvn is in path. 
        # Safer: Invoke-Expression
        Invoke-Expression $Command
        
        if ($LASTEXITCODE -ne 0) {
            throw "Command failed with exit code $LASTEXITCODE"
        }
        Write-Host "`n✅ $ProjectName: SUCCESS"
    }
    catch {
        Write-Host "`n❌ $ProjectName: FAILED"
        Write-Host "Error: $_"
        Write-Host "Check the log file for details: $LogFile"
        Pop-Location
        Stop-Transcript
        exit 1
    }
    Pop-Location
}

$BaseDir = Get-Location

# TP1
Run-Test -ProjectName "TP1 (Unit Tests)" -Directory "$BaseDir\TP1\crud-java-tp1" -Command "mvn clean test"

# TP2
Run-Test -ProjectName "TP2 (E2E Tests)" -Directory "$BaseDir\TP2\tp2pb" -Command "mvn clean test"

# TP3
Run-Test -ProjectName "TP3 (API/Fuzz Tests)" -Directory "$BaseDir\TP3\TP3_CODIGO\com-cliente-projeto" -Command "mvn clean test"

# TP4
Run-Test -ProjectName "TP4 (Integration Tests)" -Directory "$BaseDir\TP4\TP4\com-cliente-projeto" -Command "mvn clean test"

# TP5
# Note: Quotes around the argument to prevent PowerShell parsing issues with '!'
Run-Test -ProjectName "TP5 (Coverage Verification)" -Directory "$BaseDir\TP5\com-cliente-projeto" -Command "mvn clean verify '-Dtest=!CadastroEventoE2ETest'"

# AT
Run-Test -ProjectName "AT (Assessment)" -Directory "$BaseDir\AT\com-cliente-projeto" -Command "mvn clean test"

Write-Host "`n========================================"
Write-Host "🎉 ALL PROJECTS PASSED LOCAL CHECKS!"
Write-Host "📄 Full Report Saved: $LogFile"
Write-Host "========================================"

Stop-Transcript
