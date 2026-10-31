pipeline {
    agent any
    tools { maven 'Maven3' }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Semgrep') {
                    steps {
                        sh '''
                        /var/jenkins_home/semgrep-venv/bin/semgrep scan --config p/java --config p/owasp-top-ten --config p/security-audit --metrics=off --json-output=semgrep-report.json src/ || true
                        /var/jenkins_home/semgrep-venv/bin/semgrep scan --config p/java --config p/owasp-top-ten --config p/security-audit --metrics=off src/ || true
                        '''
                    }
        }
        stage('Build') {
            steps { sh 'mvn -B clean package -DskipTests' }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=workshop-sast -Dsonar.projectName=workshop-sast'
                }
            }
        }
        stage('Dependency-Check') {
                    steps {
                        withCredentials([string(credentialsId: 'nvd-api-key', variable: 'NVD_API_KEY')]) {
                            sh '''
                            mvn -B org.owasp:dependency-check-maven:check \
                              -Dformat=ALL \
                              -DnvdApiKey=$NVD_API_KEY \
                              -DdataDirectory=/var/jenkins_home/dependency-check-data \
                              -DfailBuildOnCVSS=11
                            '''
                        }
                    }
        }

    }
    post {
        always { archiveArtifacts artifacts: 'semgrep-report.json, target/dependency-check-report.*', allowEmptyArchive: true }
    }
}