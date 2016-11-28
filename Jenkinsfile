/**
 * Objektforum Frankfurt
 */

// global pipeline settings to avoid typos during live coding
def mvnToolName = 'maven-3.2'
def gitUrl = 'https://github.com/wildfly/quickstart.git'
def junitTestReports = '**/target/surefire-t/TEST-*.xml'
def deployScriptPath = '../OFS@script/deploy.groovy'

stage('Commit') {
    node {
        // checkout sources
        git url: gitUrl, branch: '10.x'

        // change directory to module kitchensink
        dir('kitchensink') {
          // run maven build
          // -Dmaven.test.failure.ignore=true -> do not fail the maven build due to test errors
          //                                  -> this will be done by the junit step (causing the build to become yellow)
          withMaven(jdk: 'JDK_1.8', maven: 'maven-3.3') {
              sh "ls -al /var/jenkins_home/tools/hudson.model.JDK/JDK_1.8/bin"
              sh "ls -al /var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/maven-3.3/bin"
              sh "mvn --version"
              sh "mvn clean install -Dmaven.test.failure.ignore=true"
          }

          // publish JUnit test results
          // allowEmptyResults -> do not fail the build if we have no tests
          //                   -> this flag is required as the demo project does not have simple unit tests
          junit(allowEmptyResults: true, testResults: junitTestReports)

          // archive the WAR file
          archive(includes: '**/*.war')

          // store the war file for later use in the pipeline
          stash name: 'ofs', includes: '**/*.war'
        }
    }
}

stage('autoTest') {
    node {
        // restore the war file for deployment
        unstash(name: 'ofs')

        // find the files to deploy and deploy them using the provided script
        def deployables = findFiles(glob: '**/*.war')
        def deployScript = load(deployScriptPath)
        deployScript.deploy(deployables[0].path)
    }
}

// create parallel executions
def branches = [:]
branches['manTest']  = {
    node{
        input(message: 'Sind die Tests OK?')
    }
}
branches['UX TEsts'] = {
    node {
        input message: 'UX Tests OK?'
    }
}
stage('man and UX Tests') {
    parallel(branches)
}
