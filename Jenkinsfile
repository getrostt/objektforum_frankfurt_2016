/**
 * Objektforum Frankfurt
 */

// global pipeline settings to avoid typos during live coding
def jdkName = 'JDK_1.8'
def mvnToolName = 'maven-3.3'
def localMavenRepoPath = '/var/jenkins_home/.m2/repository'
// withMaven(jdk: 'JDK_1.8', maven: 'maven-3.3', mavenLocalRepo: '/var/jenkins_home/.m2/repository') {
// def gitUrl = 'https://github.com/wildfly/quickstart.git'
def gitUrl = '/git/wildfly-quickstarts/.git'
def gitBranch = '10.x'
def junitTestReports = '**/target/surefire-t/TEST-*.xml'
def deployScriptPath = '../Demo1@script/deploy.groovy'

properties([
  buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '3')),
])

stage('Commit') {
    node {
        git url: gitUrl, branch: gitBranch

        dir('kitchensink') {
            withMaven(jdk: jdkName, maven: mvnToolName, mavenLocalRepo: localMavenRepoPath) {
                sh 'mvn  clean install'
            }

            junit allowEmptyResults: true, testResults: junitTestReports
            archive(includes: '**/*.war')
        }
    }
}

