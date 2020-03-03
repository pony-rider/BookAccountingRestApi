pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
        jdk 'jdk8'
    }
    stages {
        stage('Build') {
            steps {
                echo 'This is a minimal pipeline.'
                sh "mvn -B -DskipTests clean verify"
            }
            /*withMaven(maven: 'maven-3') {
                // Run the maven build
                sh "mvn -B -DskipTests clean verify"
            }*/
        }
    }
}
