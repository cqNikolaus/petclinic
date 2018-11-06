#!groovy

def mvnVersion = 'MVN354'

node {
    stage('Init') {
        deleteDir()
        echo "Where we are?"
        echo "PWD = " + pwd
        echo "WORKSPACE = " + workspace
    }
    stage('Checkout') {
        git "https://github.com/comquent/spring-petclinic.git"
    }
    stage('Build') {
        withMaven(maven: mvnVersion) {
            sh "mvn install -Dmaven.test.skip=true"
        }
    }   
    stage('Test') {
        withMaven(maven: mvnVersion) {
            sh "mvn test" 
        }
    }   
    stage('Analyse') {
        withMaven(maven: mvnVersion) {
            sh "mvn findbugs:findbugs" 
            sh "mvn checkstyle:checkstyle"
            sh "mvn pmd:pmd"
        }
    }
    stage('Report') {
        junit 'target/surefire-reports/TEST-*.xml'
//        findbugs pattern: 'target/findbugsXml.xml'
        checkstyle pattern: 'target/checkstyle-result.xml'
        pmd pattern: 'target/pmd.xml'    
    }
}
