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
            withMaven(jdk: 'JDK_1.8', maven: 'maven-3.3', mavenLocalRepo: '/var/jenkins_home/.m2/repository') {
                sh 'mvn clean install'

                junit allowEmptyResults: true, testResults: junitTestReports

                archive(includes: '**/*.war')

                stash(name: 'off', includes: '**/*.war')
            }
        }
    }
}

stage('deploy') {
    node{
        unstash(name: 'off')

        def filesToDeploy = findFiles(glob: '**/*kitchensink*.war')
        def deploy = load(deployScriptPath)
        deploy.deploy(filesToDeploy[0].name)
    }
}

def branches = [:]
branches['UX Tests'] {
    input('UX Tests passed?')
}
branches['Pentests'] {
    input('Pentests OK?')
}
stage('Manual Tests') {
    parallel branches
}

