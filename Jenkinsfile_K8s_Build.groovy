pipeline {
  agent {
    kubernetes {
      label 'promo-app'  // all your pods will be named with this prefix, followed by a unique id
      idleMinutes 5  // how long the pod will live after no jobs have run on it
      yamlFile 'build-pod.yaml'  // path to the pod definition relative to the root of our project 
      defaultContainer 'maven'  // define a default container if more than a few stages use it, will default to jnlp container
    }
  }
  stages {
    stage('Build') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn package -Dmaven.test.skip=true"   
      }
    }
    stage('Build Docker Image') {
      steps {
        container('docker') {
          script{
            docker.build('tn00/petclinic')
          }
//          sh "docker build -t vividlukeloresch/petclinic:latest ."  // when we run docker in this step, we're running it via a shell on the docker build-pod container, 
//          sh "docker push vividlukeloresch/petclinic:dev"        // which is just connecting to the host docker deaemon
        }
      }
    }
  }
}
