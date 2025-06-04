
/*
1. Mục tiêu đơn giản:
Jenkins sẽ clone code từ GitHub.

Build project Spring Boot bằng Maven.

Chạy test (hoặc bỏ qua test nếu bạn muốn).

(Nếu muốn) build Docker image và push lên Docker Hub.

(Có thể) chạy app trong Docker container.





Sau khi ấn Build Now:

Jenkins sẽ tải code từ Git về.

Thực thi các bước trong Jenkinsfile (ví dụ build, test).

Bạn có thể xem log trực tiếp để theo dõi quá trình chạy.
*/

/*
pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17'
            args '-v /root/.m2:/root/.m2' // Cache Maven để lần sau build nhanh hơn
        }
    }

    stages {
stage('Checkout') {
    steps {
        git url: 'https://github.com/vanlinh00/SpringSercurity.git', branch: 'master'
    }
}


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
    }

    post {
        success {
            echo '✅ Build OK'
        }
        failure {
            echo '❌ Build FAILED'
        }
    }
}
*/

pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/vanlinh00/SpringSercurity.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
                echo 'Build stage - just testing'
            }
        }
    }
}
