pipeline {
    agent any
    tools {
        maven "MavenTest"
        jdk 'jdk8'
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        
        stage('Test') { 
            steps {
                sh 'mvn test' 
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' 
                }
                failure {
                    echo "Test cases failed"
                }
            }
        }
    }
    post {
           failure {
               mail to: 'Akshatha.Hv@mindtree.com',
               subject: "Build failed in Jenkins: ${currentBuild.fullDisplayName}",
               body: "Something went wrong during Jenkins pipeline build with ${env.BUILD_URL}"
           }
         }
}
