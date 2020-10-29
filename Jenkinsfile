#!groovy

node() {
    stage('Init') {
        deleteDir()
        echo "Where we are?"
        echo "PWD = " + pwd
        echo "WORKSPACE = " + workspace
    }
    stage('checkout') {
		checkout scm
    }
    stage('build') {
        withMaven(maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
            def goal = "install -Dmaven.test.skip=true"
            sh "mvn ${goal}"
        }
    }
    
    stage('test') {
        withMaven(maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
            def goal = "test -Dmaven.test.failure.ignore=true"
            sh "mvn ${goal}"
        }
    }
    
    stage('analyse') {
        withMaven(maven: 'MVN354', publisherStrategy: 'EXPLICIT') {
            ["pmd:pmd", "checkstyle:checkstyle", "findbugs:findbugs"].each() {
                sh "mvn ${it}"
            }
        }
    }
    
    stage('reporting') {
        junit 'target/surefire-reports/*Tests.xml'
        // findbugs pattern: 'target/findbugsXml.xml'
        scanForIssues tool: findBugs(pattern: 'target/findbugsXml.xml', useRankAsPriority: true)
        pmd pattern: 'target/pmd.xml'
        checkstyle pattern: 'target/checkstyle-result.xml'
    }
}
