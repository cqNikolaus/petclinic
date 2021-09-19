pipeline() {
    agent any
    environment {
        mvnVersion = "MVN36"
        javaVersion = "JAVA8"
    }
    stages {
        stage('prepare') {
            steps {
                deleteDir()
                withMaven(jdk: 'JDK8', maven: 'MVN360', publisherStrategy: 'EXPLICIT') {
                    sh "mvn -version"
                }
            }
        }

        stage('build') {
            steps {
                withMaven(jdk: 'JDK8', maven: 'MVN360', publisherStrategy: 'EXPLICIT') {
                    sh "mvn install -Dmaven.test.skip=true"
                }
                archiveArtifacts artifacts: 'target/**/*.jar'
            }
        }

        stage('test') {
            steps {
                withMaven(jdk: 'JDK8', maven: 'MVN360', publisherStrategy: 'EXPLICIT') {
                    def retSt = sh returnStatus: true, script: 'mvn test'
                    echo "Beendet mit " + retSt 
                }
                junit 'target/surefire-reports/*Tests.xml'
            }
        }
    }
}
