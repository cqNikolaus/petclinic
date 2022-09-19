pipeline {
  agent {
    kubernetes {
      label 'PetClinic-App-Build'  // all your pods will be named with this prefix, followed by a unique id
      idleMinutes 5  // how long the pod will live after no jobs have run on it
      yamlFile 'build-pod.yaml'  // path to the pod definition relative to the root of our project 
      defaultContainer 'maven'  // define a default container if more than a few stages use it, will default to jnlp container
    }
  }
  stages {
    stage('Build Project') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn package -Dmaven.test.skip=true"
        archiveArtifacts artifacts: 'target/**/*.jar'   
      }
    }
    stage('Running Tests') {
      steps {  
        sh returnStatus: true, script: 'mvn test'
        junit 'target/surefire-reports/*Tests.xml' 
      }
    }
    stage('Static Analysis') {
      steps {
        sh 'mvn pmd:pmd'
        sh 'mvn findbugs:findbugs'
        sh 'mvn checkstyle:checkstyle'
        recordIssues(tools: [checkStyle()])
        recordIssues(tools: [findBugs(useRankAsPriority: true)])
        recordIssues(tools: [pmdParser()])
      }
    }
    stage('Build Docker Image') {
      steps {
        container('docker') {
          script{
            docker.withRegistry('https://gitlab.comquent.academy:5050', 'cq-gitlab-deploy-token') {
              def myImage = docker.build('tn00/petclinic')
              myImage.push('latest')
            }
          }
        }
      }
    }
  }
}
