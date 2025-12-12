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
        [string]$CommandArgs
    )

    Write-Host "`n----------------------------------------"
    Write-Host "Testing $ProjectName..."
    Write-Host "Directory: $Directory"
    Write-Host "----------------------------------------"

    Push-Location $Directory
    try {
        # Check for Maven Wrapper (mvnw.cmd for Windows)
        if (Test-Path ".\mvnw.cmd") {
            Write-Host "ℹ️  Using Maven Wrapper (.\mvnw.cmd)"
            $MavenCmd = ".\mvnw.cmd"
        }
        else {
            Write-Host "ℹ️  Using Global Maven (mvn)"
            $MavenCmd = "mvn"
        }

        # Build full command string
        $FullCommand = "$MavenCmd $CommandArgs"
        Write-Host "Command: $FullCommand"

        # Execute
        Invoke-Expression $FullCommand
        
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
Run-Test -ProjectName "TP1 (Unit Tests)" -Directory "$BaseDir\TP1\crud-java-tp1" -CommandArgs "clean test"

# TP2
Run-Test -ProjectName "TP2 (E2E Tests)" -Directory "$BaseDir\TP2\tp2pb" -CommandArgs "clean test"

# TP3
Run-Test -ProjectName "TP3 (API/Fuzz Tests)" -Directory "$BaseDir\TP3\TP3_CODIGO\com-cliente-projeto" -CommandArgs "clean test"

# TP4
Run-Test -ProjectName "TP4 (Integration Tests)" -Directory "$BaseDir\TP4\TP4\com-cliente-projeto" -CommandArgs "clean test"

# TP5
Run-Test -ProjectName "TP5 (Coverage Verification)" -Directory "$BaseDir\TP5\com-cliente-projeto" -CommandArgs "clean verify '-Dtest=!CadastroEventoE2ETest'"

# AT
Run-Test -ProjectName "AT (Assessment)" -Directory "$BaseDir\AT\com-cliente-projeto" -CommandArgs "clean test"

Write-Host "`n========================================"
Write-Host "🎉 ALL PROJECTS PASSED LOCAL CHECKS!"
Write-Host "📄 Full Report Saved: $LogFile"
Write-Host "========================================"

Stop-Transcript
