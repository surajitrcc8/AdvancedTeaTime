pipeline {
  agent any
  environment {
       ANDROID_HOME='/Users/Shared/Jenkins/Library/Android/sdk/'
       ADB="$ANDROID_HOME/platform-tools/adb"
   }

  options {
    // Stop the build early in case of compile or test failures
    skipStagesAfterUnstable()
  }
  stages {
    stage('Compile') {
    when {
       // Only execute this stage when building from the `beta` branch
       branch 'master'
    }
      steps {
        sh './gradlew compileDebugSources'
      }
    }
    stage('Static analysis') {
    when {
           // Only execute this stage when building from the `beta` branch
           branch 'master'
        }
          steps {
            sh './gradlew lintDebug'
            androidLint(pattern: '**/lint-results-*.xml')
          }
    }
    stage('Unit test') {
    when {
               // Only execute this stage when building from the `beta` branch
               branch 'master'
            }
      steps {
        sh './gradlew testDebugUnitTest testDebugUnitTest'
        junit '**/TEST-*.xml'
      }
    }
    stage('Launch emulator') {
        when {
                   // Only execute this stage when building from the `beta` branch
                   branch 'master'
                }
          steps {
            sh "$ANDROID_HOME/tools/./emulator -avd Nexus_5_API_26 -netdelay none -netspeed full"
          }
     }
    stage('Instrument test') {
        when {
                   // Only execute this stage when building from the `beta` branch
                   branch 'master'
                }
          steps {
                      sh "./gradlew connectedDebugAndroidTest"
                    }
     }
    stage('Build APK') {
    when {
               // Only execute this stage when building from the `beta` branch
               branch 'master'
            }
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