pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot-lifeis"
        JAVA_HOME = "C:\\Program Files\\Java\\jdk-21"
        PATH = "${env.JAVA_HOME}\\bin;${env.PATH}"
    }

    stages {
        stage('Setup Environment') {
            steps {
                bat '''
                echo JAVA_HOME=%JAVA_HOME%
                java -version
                mvn -version
                '''
            }
        }

        stage('Clean Workspace') {
            steps {
                bat 'mvn clean -q'
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
                bat "docker build -t ${env.DOCKER_IMAGE}:${env.BUILD_ID} ."
            }
        }

        stage('Deploy to Docker') {
            steps {
                bat '''
                docker stop springboot-lifeis || echo Container not running
                docker rm springboot-lifeis || echo Container not found
                docker run -d --name springboot-lifeis -p 9000:9000 ${DOCKER_IMAGE}:${BUILD_ID}
                '''
            }
        }
    }

    post {
        success {
            echo '🎉 Pipeline SUCCESS! Application deployed.'
            bat 'docker ps'
            bat '''
            timeout /T 10
            curl http://localhost:9000/products
            '''
        }
        failure {
            echo '❌ Pipeline FAILED!'
        }
        always {
            echo 'Pipeline execution completed.'
        }
    }
}
