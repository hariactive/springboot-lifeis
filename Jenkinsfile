pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot-lifeis"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
                sh 'git branch'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${env.DOCKER_IMAGE}:${env.BUILD_ID} ."
                }
            }
        }

        stage('Deploy to Docker') {
            steps {
                script {
                    sh """
                    docker stop springboot-lifeis || true
                    docker rm springboot-lifeis || true
                    docker run -d --name springboot-lifeis -p 9000:9000 ${env.DOCKER_IMAGE}:${env.BUILD_ID}
                    """
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Pipeline SUCCESS! Application deployed.'
            sh 'docker ps'
            sh 'curl -s http://localhost:9000/products || echo "App might be starting..."'
        }
        failure {
            echo '❌ Pipeline FAILED!'
        }
        always {
            echo 'Pipeline completed.'
        }
    }
}