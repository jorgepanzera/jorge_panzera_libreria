@Library('threepoints_sharedlib') _

pipeline {
    agent any

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
                    def gitBranch = env.BRANCH_NAME
                    bat "echo La rama actual del Jenkinsfile es: ${gitBranch}"
                    bat 'echo Jenkinsfile: %BRANCH_NAME%'
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