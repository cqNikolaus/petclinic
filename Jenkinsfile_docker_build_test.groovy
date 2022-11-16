node() {
    stage('checkout') {
        echo currentBuild.result
        checkout scm
    }
    
    stage('build') {
        docker.image("maven:3.6.3-jdk-8-slim").inside("-v maven-repo:/root/.m2") {
            sh 'mvn help:effective-settings'
            sh 'mvn package -Dmaven.test.skip=true'
        }
    }

    def parallelStages = [:]

    parallelStages["test"] = {
        stage('test') {
            docker.image("maven:3.6.3-jdk-8-slim").inside("-v maven-repo:/root/.m2") {
                sh returnStatus: true, script: 'mvn test'
            }
            junit 'target/**/*Tests.xml'
        }
    }

    parallelStages["analyse"] = {
        stage('analyse') {
            docker.image("maven:3.6.3-jdk-8-slim").inside() {
                sh 'mvn pmd:pmd'
                sh 'mvn findbugs:findbugs'
                sh 'mvn checkstyle:checkstyle'
            }
            recordIssues(tools: [checkStyle()])
            recordIssues(tools: [findBugs(useRankAsPriority: true)])
            recordIssues(tools: [pmdParser()])
        }
    }

    parallel parallelStages

    stage('build image') {
        docker.withRegistry('https://gitlab.comquent.academy:5050', 'cq-academy-gitlab-access') {
            def myImage = docker.build('tn00/petclinic')
            myImage.push('latest')
        }
    }

    stage('run container') {
        node('demo') {
            docker.withRegistry('https://gitlab.comquent.academy:5050', 'cq-academy-gitlab-access') {
                def runImages = docker.image("gitlab.comquent.academy:5050/tn00/petclinic:latest")
                runImages.withRun("-p 8081:8080") {
                    input "Just test"
                }
            }
        }
    }
}
