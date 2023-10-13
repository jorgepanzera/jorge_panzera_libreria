@Library('threepoints_sharedlib') _

pipeline {
    agent any

    environment {
        CUSTOM_BRANCH = 'hotfix_rapido'
    }

    stages {
        
        stage('Checkout') {
            steps {
                echo 'Obtener codigo desde Github'
                git credentialsId: 'github_user_threepoints', url: 'https://github.com/jorgepanzera/threepoints_devops_webserver'
            }
        }
        
        stage('Pruebas de SAST') {
            steps {
                script { 
                    def gitBranch = CUSTOM_BRANCH
                    bat "echo La rama actual del Jenkinsfile es: ${gitBranch}"
                    bat 'echo Jenkinsfile: %CUSTOM_BRANCH%'
                    def result = sonarAnalysis('threepoints_devops_webserver', gitBranch, true)
                }
            }
        }
        
        stage('Build') {
            steps {
                echo 'Construcción de la imagen Docker'
                bat 'docker build --tag devops_ws .'
                
            }
        }

    }
}