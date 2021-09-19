pipeline() {
    environment {
        mvnVersion = "MVN36"
        javaVersion = "JAVA8"
    }
    stages {
        stage('prepare') {
            steps {
            // One or more steps need to be included within the steps block.
            }
        }

        stage('build') {
            steps {
            // One or more steps need to be included within the steps block.
            }
        }

        stage('test') {
            steps {
            // One or more steps need to be included within the steps block.
            }
        }
    }
}
