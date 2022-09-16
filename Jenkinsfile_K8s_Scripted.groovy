podTemplate(
  cloud: 'kubernetes',
  containers: [
    containerTemplate(name: 'maven', image: 'maven:3.6.3-jdk-8', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'python', image: 'python:latest', ttyEnabled: true, command: 'cat')
  ]
) {
    node(POD_LABEL) {
        stage('prepare') {
            container('maven') {
                echo pwd()
                sh 'ls -la'
                checkout scm
                withMaven(jdk: 'JDK8', maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
                    sh "mvn -version"
                }
            }
            archiveArtifacts artifacts: 'target/**/*.jar'
        }
        
        stage('test') {
            container('maven') {
                def retSt = sh returnStatus: true, script: 'mvn test'
                echo "Beendet mit " + retSt 
            }
            junit 'target/surefire-reports/*Tests.xml'
        }

        stage('python') {
            container('python') {
                sh "python --version"
                sleep 100
            }
        }
        stage('analyse') {
            container('maven') {
                sh 'mvn pmd:pmd'
                sh 'mvn findbugs:findbugs'
                sh 'mvn checkstyle:checkstyle'
            }
            recordIssues(tools: [checkStyle()])
            recordIssues(tools: [findBugs(useRankAsPriority: true)])
            recordIssues(tools: [pmdParser()])
        }
    }
}
