pipeline {
  agent any

  environment {
    IMAGE_NAME = "spring-security-app"
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: 'develop', url: 'https://github.com/vanlinh00/SpringSercurity.git'
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

    stage('Docker Build') {
      steps {
        sh 'docker build -t $IMAGE_NAME .'
      }
    }

    stage('Deploy') {
      steps {
        sh 'scp target/*.jar user@your-server:/app/'
        // Thay user@your-server bằng user@ip-server của bạn
      }
    }
  }
}
