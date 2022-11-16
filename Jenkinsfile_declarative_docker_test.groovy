env.HOME = env.JENKINS_HOME
env.JAVA_TOOL_OPTIONS = "-Duser.home=${env.HOME}/maven"

pipeline {
    agent {
        docker { image 'maven:3.6.3-jdk-8-slim' }
    }
    
    stages {
      stage('build') {
          steps {
            sh 'mvn help:effective-settings'
            sh 'mvn package -Dmaven.test.skip=true'
          }
      }
      
      stage('test & analyse') {
          parallel {
              stage('test') {
                steps {
                    sh returnStatus: true, script: 'mvn test'
                    junit 'target/**/*Tests.xml'
                }
              }
              stage('analyse') {
                steps {
                    sh 'mvn pmd:pmd'
                    sh 'mvn findbugs:findbugs'
                    sh 'mvn checkstyle:checkstyle'
                    
                    recordIssues(tools: [checkStyle()])
                    recordIssues(tools: [findBugs(useRankAsPriority: true)])
                    recordIssues(tools: [pmdParser()])
                }
              }
          }
      }
        stage('build image') {
            steps {
                script {
                    docker.withRegistry('https://gitlab.comquent.academy:5050', 'cq-academy-gitlab-access') {
                        def myImage = docker.build('tn00/petclinic')
                        myImage.push('latest')
                    }
                }
            }
        }
    
        stage('run & test container') {
            agent {
                label 'demo'
            }
            options {
                skipDefaultCheckout true
            }
            steps {
                script {
                    docker.withRegistry('https://gitlab.comquent.academy:5050', 'cq-academy-gitlab-access') {
                        def runImages = docker.image("gitlab.comquent.academy:5050/tn00/petclinic:latest")
                        runImages.withRun("-p 8081:8080") {
                            input "Just test"
                        }
                    }
                }
            }
        }
    }
}
