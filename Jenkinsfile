pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh './gradlew compileDebugSources'
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
    stage('Static analysis') {
      steps {
        sh './gradlew lintDebug'
        androidLint(pattern: '**/lint-results-*.xml')
      }
    }
  }
  post {
    failure {
      mail(to: 'surajit.rcc8@gmail.com', subject: 'Oops! Your back is on fire', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}")

    }

  }
  options {
    skipStagesAfterUnstable()
  }
}