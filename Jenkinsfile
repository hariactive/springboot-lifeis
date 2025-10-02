pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot-lifeis"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                bat 'mvn clean || echo "Clean completed"'
                bat 'if exist target rmdir /s /q target'
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                bat 'mvn clean package -DskipTests'
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${env.DOCKER_IMAGE}:${env.BUILD_ID} ."
                }
            }
        }

        stage('Deploy to Docker') {
            steps {
                script {
                    bat "docker stop springboot-lifeis || echo \"Container not running\""
                    bat "docker rm springboot-lifeis || echo \"Container not found\""
                    bat "docker run -d --name springboot-lifeis -p 9000:9000 ${env.DOCKER_IMAGE}:${env.BUILD_ID}"
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Pipeline SUCCESS! Application deployed.'
            bat 'docker ps'
            bat 'curl http://localhost:9000/products || echo "Testing application..."'
        }
        failure {
            echo '❌ Pipeline FAILED!'
        }
        always {
            echo 'Pipeline completed.'
        }
    }
}