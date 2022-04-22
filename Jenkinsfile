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
                
        stage('upload to nexus') {
            steps { 
                nexusArtifactUploader artifacts: [[artifactId: 'Jenkins_deployment', classifier: '', file: 'target/Jenkins_deployment-0.0.1-SNAPSHOT.war', type: 'war']], credentialsId: 'Nexus3', groupId: 'Sample', nexusUrl: '10.0.0.4:8081', nexusVersion: 'nexus2', protocol: 'http', repository: '/Jenkins-app-deployable/', version: '0.0.1-SNAPSHOT'
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
