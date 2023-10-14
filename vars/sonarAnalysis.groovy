def call(projectKey, gitBranch, abortPipeline = false) {
    def scannerResult = null
    def haveToExitPipeline = false

    timeout(time: 10, unit: 'SECONDS') {
        scannerResult = 100 // inicializar con algo != 0
        withSonarQubeEnv(installationName: 'SonarLocal', credentialsId: 'SonarQube_Token')  {
            scannerResult = bat(script: "sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.sources=.", returnStatus: true)
        }
    }

    bat "echo scannerResult ${scannerResult}"
    bat "echo abortPipeline ${abortPipeline}"
    bat "echo gitBranch ${gitBranch}"
    if (abortPipeline && scannerResult != 0) {
        haveToExitPipeline = true
    } else if (!abortPipeline) {
        // Verificar si abortar el pipeline segun nombre de la rama gitBranch
        if (gitBranch == 'master' || gitBranch.startsWith('hotfix')) {
            haveToExitPipeline = true
        }
    }

    bat "echo haveToExitPipeline ${haveToExitPipeline}"

    if (haveToExitPipeline) {
        error("SonarQube scan failed with result code: ${scannerResult}")
    }

    return scannerResult
}
