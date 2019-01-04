pipeline {
  agent any
  options {
    // Stop the build early in case of compile or test failures
    skipStagesAfterUnstable()
  }
  stages {
  when {
      // Only execute this stage when building from the `beta` branch
      branch 'master'
   }
    stage('Compile') {
      steps {
        sh './gradlew compileDebugSources'
      }
    }
    stage('Static analysis') {
          steps {
            sh './gradlew lintDebug'
            androidLint(pattern: '**/lint-results-*.xml')
          }
    }
    stage('Unit test') {
      steps {
        sh './gradlew testDebugUnitTest testDebugUnitTest'
        junit '**/TEST-*.xml'
      }
    }
    stage('Build APK') {
      steps {
        sh './gradlew assembleDebug'
        archiveArtifacts '**/*.apk'
      }
    }
  }
  post {
    failure {
      mail(to: 'surajit.rcc8@gmail.com', subject: 'Oops! Your back is on fire', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}")

    }
  }
}