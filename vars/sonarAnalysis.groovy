def call(projectKey, abortPipeline = false) {
    def scannerResult = 1

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
