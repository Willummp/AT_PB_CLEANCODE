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
    CMD_ARGS=$3
    
    echo -e "\n----------------------------------------"
    echo "Testing $PROJECT..."
    echo "Directory: $DIR"
    echo "----------------------------------------"
    
    cd "$DIR"
    
    # Check for Maven Wrapper
    if [ -f "./mvnw" ]; then
        echo "ℹ️  Using Maven Wrapper (./mvnw)"
        chmod +x ./mvnw
        MAVEN_CMD="./mvnw"
    else
        echo "ℹ️  Using Global Maven (mvn)"
        MAVEN_CMD="mvn"
    fi
    
    FULL_CMD="$MAVEN_CMD $CMD_ARGS"
    echo "Command: $FULL_CMD"
    
    if eval "$FULL_CMD"; then
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
run_test "TP1 (Unit Tests)" "$BASE_DIR/TP1/crud-java-tp1" "clean test"

# TP2
run_test "TP2 (E2E Tests)" "$BASE_DIR/TP2/tp2pb" "clean test"

# TP3
run_test "TP3 (API/Fuzz Tests)" "$BASE_DIR/TP3/TP3_CODIGO/com-cliente-projeto" "clean test"

# TP4
run_test "TP4 (Integration Tests)" "$BASE_DIR/TP4/TP4/com-cliente-projeto" "clean test"

# TP5
run_test "TP5 (Coverage Verification)" "$BASE_DIR/TP5/com-cliente-projeto" "clean verify -Dtest=!CadastroEventoE2ETest"

# AT
run_test "AT (Assessment)" "$BASE_DIR/AT/com-cliente-projeto" "clean test"

echo -e "\n========================================"
echo "🎉 ALL PROJECTS PASSED LOCAL CHECKS!"
echo "📄 Full Report Saved: ${LOG_FILE}"
echo "========================================"
