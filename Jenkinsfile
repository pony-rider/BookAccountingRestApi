pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
        jdk 'jdk8'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn --batch-mode --debug --errors --update-snapshots -DskipTests clean package"
            }
        }
        stage('Test') {
            steps {
                withMaven(maven:'Maven 3.3.9', jdk:'jdk8') {
                    sh "mvn --batch-mode --debug --errors --update-snapshots clean verify sonar:sonar"
                }
            }
        }
    }

}
