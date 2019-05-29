pipeline {
    agent any
    tools {
        maven 'maven 3.6.1'
        jdk 'JDK 8'
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
        /*
        stage ('Build') {
            steps {
                sh 'mvn clean test -U' 
            }
            post {
                success {
                    junit 'target/surefire-reports/**//*.xml' 
                }
            }
        }
        */

        /*
        stage ('Analysis') {
            steps {
                sh 'echo "Start analysis"'
                sh 'mvn --batch-mode -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd findbugs:findbugs'
            }
        }*/
    }

    post {
        always {
            // junit testResults: '**/target/surefire-reports/*.xml'

            recordIssues enabledForFailure: true, tools: [mavenConsole(), java(), javaDoc()]            
        }
    }
}