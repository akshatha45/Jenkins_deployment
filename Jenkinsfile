pipeline {
    agent any
    tools {
        maven "MavenTest"
        jdk 'OpenJDK-11'
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

        stage ('Build Test and compile') {
            steps {
                        sh 'mvn clean package'
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
        
        stage('verify') { 
            steps {
                sh 'mvn clean verify' 
            }
        }
        
         stage('Publish HTML Report') { 
            steps {
            publishHTML target: [
            allowMissing: true,
            alwaysLinkToLastBuild: false,
            keepAll: true,
            reportDir: 'target/site/jacoco/',
            reportFiles: 'index.html',
            reportName: 'Code Coverage',
            reportTitles: ''
          ]
            }
        }
                
        stage('upload to nexus') {
            steps { 
                mvn deploy:deploy-file \
               -Dfile=target/Jenkins_deployment-0.0.1-SNAPSHOT.war \
               -Dpackaging=jar -DgroupId=Sample -DartifactId=Jenkins_deployment -Dversion=0.0.1-SNAPSHOT \
               -DrepositoryId=Jenkins-app-deployable \
               -Durl=http://localhost:8081/repository/Jenkins-app-deployable/
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
