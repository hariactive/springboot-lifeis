pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "springboot-lifeis"
        JAVA_HOME = "C:\\Program Files\\Java\\jdk-21"
    }

    stages {
        stage('Setup Environment') {
            steps {
                bat """
                set PATH=${env.JAVA_HOME}\\bin;%PATH%
                echo JAVA_HOME: %JAVA_HOME%
                java -version
                echo.
                mvn --version
                """
            }
        }

        stage('Clean Workspace') {
            steps {
                bat 'mvn clean -q'
            }
        }

        stage('Build & Compile') {
            steps {
                bat """
                set PATH=${env.JAVA_HOME}\\bin;%PATH%
                mvn compile -Dmaven.compiler.source=21 -Dmaven.compiler.target=21
                """
            }
        }

        stage('Run Tests') {
            steps {
                bat """
                set PATH=${env.JAVA_HOME}\\bin;%PATH%
                mvn test -Dmaven.compiler.source=21 -Dmaven.compiler.target=21
                """
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                bat """
                set PATH=${env.JAVA_HOME}\\bin;%PATH%
                mvn package -DskipTests -Dmaven.compiler.source=21 -Dmaven.compiler.target=21
                """
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
                    bat """
                    docker stop springboot-lifeis || echo "Container not running"
                    docker rm springboot-lifeis || echo "Container not found"
                    docker run -d --name springboot-lifeis -p 9000:9000 ${env.DOCKER_IMAGE}:${env.BUILD_ID}
                    """
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Pipeline SUCCESS! Application deployed and running.'
            bat 'docker ps'
            bat """
            echo "Application container started successfully!"
            echo "Spring Boot may take 20-30 seconds to fully start..."
            echo "You can manually test with: curl http://localhost:9000/products"
            """
        }
        failure {
            echo '❌ Pipeline FAILED!'
        }
    }
}