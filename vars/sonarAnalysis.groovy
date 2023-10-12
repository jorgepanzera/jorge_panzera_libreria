def call(projectKey, gitBranch, abortPipeline = false) {
    def scannerResult = 1

    echo "1 Mi rama en la libreria es ${gitBranch}"
    echo "2 Mi rama en la libreria es ${env.BRANCH_NAME}"

    timeout(time: 5, unit: 'MINUTES') {
        withSonarQubeEnv(installationName: 'SonarLocal', credentialsId: 'SonarQube_Token')  {
            scannerResult = bat(script: "sonar-scanner -Dsonar.projectKey=${projectKey} -Dsonar.sources=.", returnStatus: true)
        }
    }

    if (abortPipeline && scannerResult != 0) {
        error("SonarQube scan failed with result code: ${scannerResult}")
    }

    return scannerResult

}
