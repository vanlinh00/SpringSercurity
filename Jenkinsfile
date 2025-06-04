pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17'
        }
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Docker Build') {
            steps {
                echo 'Docker build step (bạn tự thêm nếu cần)'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy step (bạn tự thêm nếu cần)'
            }
        }
    }
}
