
/*
1. Mục tiêu đơn giản:
Jenkins sẽ clone code từ GitHub.

Build project Spring Boot bằng Maven.

Chạy test (hoặc bỏ qua test nếu bạn muốn).

(Nếu muốn) build Docker image và push lên Docker Hub.

(Có thể) chạy app trong Docker container.


*/
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/vanlinh00/SpringSercurity.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'   // Build project, bỏ qua test cho nhanh
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'                        // Chạy test nếu muốn
            }
        }
    }
}
