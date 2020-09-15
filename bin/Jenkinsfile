timestamps {
    ansiColor('xterm') {
        node {
            stage('Setup') {
                checkout scm
            }

            stage('Build') {
             withCredentials([string(credentialsId: 'sonarcloud-token', variable: 'SONAR_TOKEN')]) {
               try {
                    if(env.BRANCH_NAME.equals('master')){
                       sh "./mvnw -B clean deploy sonar:sonar -Dsonar.login=${env.SONAR_TOKEN} -DaltDeploymentRepository=${env.ALT_DEPLOYMENT_REPOSITORY_SNAPSHOTS} -Djvm=${env.JAVA_HOME_11}/bin/java"  
                    }else{
                       sh "./mvnw -B clean verify sonar:sonar -Dsonar.login=${env.SONAR_TOKEN} -Djvm=${env.JAVA_HOME_11}/bin/java"
                    }
                    archiveArtifacts 'target/bonita-connector-sap-*.zip'
                } finally {
                    junit '**/target/*-reports/*.xml'
                }
             }
            }
        }
    }
}