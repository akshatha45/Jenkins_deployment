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
                
stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: 'NEXUS3',
                            protocol: 'http',
                            nexusUrl: '10.0.0.4:8081',
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: 'Jenkins-app-deployable',
                            credentialsId: 'nexus3',
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
    }
    post {
           failure {
               mail to: 'akshatha45@gmail.com',
               subject: "Build failed in Jenkins: ${currentBuild.fullDisplayName}",
               body: "Something went wrong during Jenkins pipeline build with ${env.BUILD_URL}"
           }
         }
}
