#!groovy

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
        withMaven(maven: 'MVN.3.5.2') {
            sh "mvn clean install -Dmaven.test.skip=true"
        }
    }   
    stage('Test & Analyse') {
        withMaven(maven: 'MVN.3.5.2') {
            parallel (
                test: { sh "mvn test" },
                analyse: { sh "mvn findbugs:findbugs" }
            )
        }
    }   
    stage('report') {
        junit '**/target/surefire-reports/TEST-*.xml'
    }
}
