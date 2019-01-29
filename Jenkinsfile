timestamps {
    ansiColor('xterm') {
        node {
            stage('Setup') {
                checkout scm
            }

            stage('Build') {
                try {
                    if(env.BRANCH_NAME.equals('master')){
                      sh "./mvnw -B clean deploy -DaltDeploymentRepository=${env.ALT_DEPLOYMENT_REPOSITORY_SNAPSHOTS} -Djvm=${env.JAVA_HOME_11}/bin/java"  
                    }else{
                       sh "./mvnw -B clean verify -Djvm=${env.JAVA_HOME_11}/bin/java"
                    }
                    archiveArtifacts 'target/bonita-connector-sap-*.zip'
                } finally {
                    junit '**/target/*-reports/*.xml'
                }
            }
        }
    }
}