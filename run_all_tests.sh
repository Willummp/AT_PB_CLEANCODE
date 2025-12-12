#!/bin/bash
set -e

# Configuração do Log
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
LOG_FILE="auditoria_testes_${TIMESTAMP}.log"

# Redireciona stdout e stderr para o console E para o arquivo de log
exec > >(tee -a "${LOG_FILE}") 2>&1

echo "========================================"
echo "🚀 Starting Full Project Health Check"
echo "📅 Date: $(date)"
echo "📄 Log File: ${LOG_FILE}"
echo "========================================"

run_test() {
    PROJECT=$1
    DIR=$2
    CMD=$3
    
    echo -e "\n----------------------------------------"
    echo "Testing $PROJECT..."
    echo "Directory: $DIR"
    echo "Command: $CMD"
    echo "----------------------------------------"
    
    cd "$DIR"
    if eval "$CMD"; then
        echo -e "\n✅ $PROJECT: SUCCESS"
    else
        echo -e "\n❌ $PROJECT: FAILED"
        echo "Check the log file for details: ${LOG_FILE}"
        exit 1
    fi
    cd - > /dev/null
}

BASE_DIR="/home/willump/Downloads/lucas_ferreira_pb/lucas_ferreira_pb/AT_Entrega"

# TP1
run_test "TP1 (Unit Tests)" "$BASE_DIR/TP1/crud-java-tp1" "mvn clean test"

# TP2 - Running default tests (including E2E if drivers are present, checking recent fix)
# Since we fixed the race condition, we can try running it normally.
run_test "TP2 (E2E Tests)" "$BASE_DIR/TP2/tp2pb" "mvn clean test"

# TP3
run_test "TP3 (API/Fuzz Tests)" "$BASE_DIR/TP3/TP3_CODIGO/com-cliente-projeto" "mvn clean test"

# TP4
run_test "TP4 (Integration Tests)" "$BASE_DIR/TP4/TP4/com-cliente-projeto" "mvn clean test"

# TP5 - full verification including coverage check (avoiding E2E headless issues if local chrome differs, but trying verifies coverage)
run_test "TP5 (Coverage Verification)" "$BASE_DIR/TP5/com-cliente-projeto" "mvn clean verify -Dtest=!CadastroEventoE2ETest"

# AT
run_test "AT (Assessment)" "$BASE_DIR/AT/com-cliente-projeto" "mvn clean test"

echo -e "\n========================================"
echo "🎉 ALL PROJECTS PASSED LOCAL CHECKS!"
echo "📄 Full Report Saved: ${LOG_FILE}"
echo "========================================"
