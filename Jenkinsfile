// Musterloeseung der PetClinic Pipeline fuer das CQ Jenkins-Pipeline-Training
// Version 2.0 
// * next generation analysis - anstatt findbugs, pmd und checkstyle reporting
// Version 3.0 
// * Maven 3.6 und Java 8 im Jenkins von Java 11

timestamps() {
    node {
        stage('Checkout the Source Code') {
            deleteDir()
            checkout scm
            withMaven(jdk: 'JDK8', maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
                sh "mvn -version"
            }
        }
        
        stage('Build the Project') {
            withMaven(jdk: 'JDK8', maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
                sh "mvn install -Dmaven.test.skip=true"
            }
            archiveArtifacts artifacts: 'target/**/*.jar'
        }
        
        stage('Run the Unit Tests') {
            withMaven(jdk: 'JDK8', maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
                def retSt = sh returnStatus: true, script: 'mvn test'
                echo "Beendet mit " + retSt 
            }
            junit 'target/surefire-reports/*Tests.xml'
        }
        stage('Static Code Analysis') {
            withMaven(jdk: 'JDK8', maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
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
