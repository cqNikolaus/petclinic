podTemplate(
  cloud: 'kubernetes',
  containers: [
    containerTemplate(name: 'maven', image: 'maven:3.6.3-jdk-8', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'python', image: 'python:latest', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'docker', image: 'docker:22.06-rc', ttyEnabled: true, command: 'cat', envVars: [containerEnvVar(key: 'DOCKER_HOST', value: 'tcp://localhost:2375')])
  ],
  volumes: [
    persistentVolumeClaim(mountPath: '/root/.m2/repository', claimName: 'csi-pvc-my-csi-app-set-0', readOnly: false)
  ]

) {
    node(POD_LABEL) {
        stage('checkout') {
            checkout scm
        }
        stage('build') {
            container('maven') {
                sh "mvn install -Dmaven.test.skip=true"
            }
            archiveArtifacts artifacts: 'target/**/*.jar'
        }
        
        stage('test') {
            container('maven') {
                def retSt = sh returnStatus: true, script: 'mvn test'
                echo "Beendet mit " + retSt 
            }
            junit 'target/surefire-reports/*Tests.xml'
        }
        stage('analyse') {
            container('maven') {
                sh 'mvn pmd:pmd'
                sh 'mvn findbugs:findbugs'
                sh 'mvn checkstyle:checkstyle'
            }
            recordIssues(tools: [checkStyle()])
            recordIssues(tools: [findBugs(useRankAsPriority: true)])
            recordIssues(tools: [pmdParser()])
        }

        stage('docker') {
            container('docker') {
                sh "docker version"
            }
        }
        stage('python') {
            container('python') {
                sh "python --version"
            }
        }
    }
}
