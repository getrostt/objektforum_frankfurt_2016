def mvnImgDocker = 'maven:3.3-jdk-8-alpine'
//def gitUrl = 'https://github.com/wildfly/quickstart.git'
def gitUrl = '/git/wildfly-quickstarts/.git'

properties([
  buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')),
])

stage('Stage') {
    def bc = docker.image(mvnImgDocker)
    bc.inside {
        git url: gitUrl, branch: '10.X'
        sh 'cd kitchensink && mvn clean install'
    }
}