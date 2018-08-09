#!groovy

def mvnVersion = 'MVN_354'

node {
    stage('Init') {
        deleteDir()
        echo "Where we are?"
        echo "PWD = " + pwd
        echo "WORKSPACE = " + workspace
    }
    stage('Checkout') {
        checkout scm
    }
    stage('Build') {
        withMaven(maven: mvnVersion) {
            sh "mvn clean install -Dmaven.test.skip=true"
        }
    }   
    stage('Test & Analyse') {
        withMaven(maven: mvnVersion) {
            parallel (
                test: { sh "mvn test" },
                analyse: { sh "mvn findbugs:findbugs" 
                           sh "mvn checkstyle:checkstyle"
                           sh "mvn pmd:pmd"},
                docu: { sh "mvn javadoc:javadoc -Dmaven.javadoc.failOnError=false" }
            )
        }
    }   
    stage('report') {
        junit 'target/surefire-reports/TEST-*.xml'
        findbugs pattern: 'target/findbugsXml.xml'
        checkstyle pattern: 'target/checkstyle-result.xml'
        pmd pattern: 'target/pmd.xml'    
    }
}
