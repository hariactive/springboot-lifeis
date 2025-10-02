pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot-lifeis"
    }

    stages {
        stage('Debug Info') {
            steps {
                bat 'echo "Java Version:" && java -version'
                bat 'echo "Maven Version:" && mvn --version'
            }
        }

        stage('Clean Workspace') {
            steps {
                bat 'mvn clean -q'
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Validate Project') {
            steps {
                bat 'mvn validate -q'
            }
        }

        stage('Build & Compile') {
            steps {
                bat 'mvn compile -Dmaven.compiler.source=21 -Dmaven.compiler.target=21'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -Dmaven.compiler.source=21 -Dmaven.compiler.target=21'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                bat 'mvn package -DskipTests -Dmaven.compiler.source=21 -Dmaven.compiler.target=21'
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
            bat 'timeout 10 && curl -f http://localhost:9000/products || echo "Application might be starting..."'
        }
        failure {
            echo '❌ Pipeline FAILED!'
        }
        always {
            echo 'Pipeline execution completed.'
        }
    }
}