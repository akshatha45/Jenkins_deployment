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
                nexusArtifactUploader artifacts: [[artifactId: 'Jenkins_deployment', classifier: '', file: 'target/Jenkins_deployment-0.0.1-SNAPSHOT.war', type: 'war']], credentialsId: 'Nexus3', groupId: 'Sample', nexusUrl: '10.0.0.4:8081', nexusVersion: 'NEXUS3', protocol: 'http', repository: 'Jenkins-app-deployable/', version: '0.0.1-SNAPSHOT'
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
