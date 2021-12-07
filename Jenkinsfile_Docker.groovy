env.HOME = env.JENKINS_HOME
env.JAVA_TOOL_OPTIONS = "-Duser.home=${env.HOME}/maven"
 
node {
    stage('checkout') {
        deleteDir()
        checkout scm
    }
    
    stage('build') {
        sh "docker volume rm maven-cache && docker volume create maven-cache"
        docker.image('maven:3.6.3-jdk-8').inside() {
            sh "mvn package -Dmaven.test.skip=true"
        }
        archiveArtifacts artifacts: 'target/**/*.jar'
    }
    
    stage('test') {
        docker.image('maven:3.6.3-jdk-8').inside() {
            def retSt = sh returnStatus: true, script: 'mvn test'
            echo "Beendet mit " + retSt
        } 
        junit 'target/surefire-reports/*Tests.xml'
    }
    stage('analyse') {
        docker.image('maven:3.6.3-jdk-8').inside() {
            sh 'mvn pmd:pmd'
            sh 'mvn findbugs:findbugs'
            sh 'mvn checkstyle:checkstyle'
        }

        recordIssues(tools: [checkStyle()])
        recordIssues(tools: [findBugs(useRankAsPriority: true)])
        recordIssues(tools: [pmdParser()])
    }

    stage('build image') {
        docker.withRegistry('https://gitlab.comquent.academy:5050', 'cq-academy-gitlab-access') {
            def myImage = docker.build('tn00/petclinic')
            myImage.push('latest')
        }    
    }
}

