// Musterloeseung der PetClinic Pipeline fuer das CQ Jenkins-Pipeline-Training
// Version 2.0 
// * next generation analysis - anstatt findbugs, pmd und checkstyle reporting

node {
    stage('checkout') {
        deleteDir()
        git credentialsId: 'cq-jenkins-gitlab-access', url: 'https://gitlab.comquent.de/training/petclinic.git'    
    }
    
    stage('build') {
        withMaven(maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
            sh "mvn install -Dmaven.test.skip=true"
        }
        archiveArtifacts artifacts: 'target/**/*.jar'
    }
    
    stage('test') {
        withMaven(maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
            def retSt = sh returnStatus: true, script: 'mvn test'
            echo "Beendet mit " + retSt 
        }
        junit 'target/surefire-reports/*Tests.xml'
    }
    stage('analyse') {
        withMaven(maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
            def retSt = sh returnStatus: true, script: 'mvn pmd:pmd'
            echo "Beendet mit " + retSt 
            retSt = sh returnStatus: true, script: 'mvn findbugs:findbugs'
            echo "Beendet mit " + retSt 
            retSt = sh returnStatus: true, script: 'mvn checkstyle:checkstyle'
            echo "Beendet mit " + retSt 
        }
        recordIssues(tools: [checkStyle()])
        recordIssues(tools: [findBugs(useRankAsPriority: true)])
        recordIssues(tools: [pmdParser()])
    }
}
