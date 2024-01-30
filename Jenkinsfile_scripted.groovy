node {
    stage('checkout') {
        deleteDir()
        checkout scm
    }
    stage('build') {
        withMaven(jdk: 'JDK8', maven: 'MVN363', publisherStrategy: 'EXPLICIT') {
            sh "mvn package -Dmaven.test.skip=true"
        }
        archiveArtifacts artifacts: 'target/**/*.jar'
    }
    stage('test') {
        def tests = ["system", "model", "owner", "vet", "service"] 
        tests.each {
            stage('test ' + it) {
                withMaven(jdk: 'JDK8', maven: 'MVN363', publisherStrategy: 'EXPLICIT') {
                    sh "mvn -Dmaven.test.failure.ignore=true -Dtest=\"org.springframework.samples.petclinic.${it}.*\" test"
                }
                junit 'target/**/*.xml'
                dir("target/surefire-reports") {
                    deleteDir()
                }
            }
        }
    }
    
    stage('analyse') {
        def analysis = ["pmd":"pmdParser()", "checkstyle":"checkStyle()", "findbugs":"findBugs()"]
        analysis.each {
            stage('analysis ' + it.key) {
                withMaven(jdk: 'JDK8', maven: 'MVN363', publisherStrategy: 'EXPLICIT') {
                    sh "mvn ${it.key}:${it.key}"
                }
            }
        }
        stage('report') {
            recordIssues(tools: [checkStyle()])
            recordIssues(tools: [findBugs(useRankAsPriority: true)])
            recordIssues(tools: [pmdParser()])
        }
    }
}
