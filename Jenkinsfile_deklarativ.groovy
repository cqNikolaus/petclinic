pipeline() {
    agent any
    tools {
        jdk 'JDK8'
        maven 'MVN360'
    }
    stages {
        stage('prepare') {
            steps {
                deleteDir()
                sh "mvn -version"
            }
        }

        stage('build') {
            steps {
                sh "mvn install -Dmaven.test.skip=true"
                archiveArtifacts artifacts: 'target/**/*.jar'
            }
        }

        stage('test') {
            steps {
                script {
                    def retSt = sh returnStatus: true, script: 'mvn test'
                    echo "Beendet mit " + retSt 
                    junit 'target/surefire-reports/*Tests.xml'
                }
            }
        }
    }
}
