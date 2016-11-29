def mvnImgDocker = 'maven:3.3-jdk-8-alpine'
//def gitUrl = 'https://github.com/wildfly/quickstart.git'
def gitUrl = '/git/wildfly-quickstarts/.git'

properties([
  buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')),
])