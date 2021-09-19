pipeline() {
    agent any
    tools {
        jdk 'JDK8'
        maven 'MVN360'
    }
    stages {
        stage('prepare') {
            steps {
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
        stage('analyse') {
            steps {
                sh 'mvn pmd:pmd'
                sh 'mvn findbugs:findbugs'
                sh 'mvn checkstyle:checkstyle'
                recordIssues(tools: [checkStyle()])
                recordIssues(tools: [findBugs(useRankAsPriority: true)])
                recordIssues(tools: [pmdParser()])
            }
        }

    }
}
