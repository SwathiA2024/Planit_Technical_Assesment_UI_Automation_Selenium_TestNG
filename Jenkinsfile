pipeline {
  agent any
  options { timestamps() }

  tools {
    jdk   'temurin-17'
    maven 'Maven 3.9.x'
  }

  parameters {
    string(name: 'suiteFile', defaultValue: 'src/test/resources/testng.xml')
    choice(name: 'browser', choices: ['', 'chrome', 'firefox'])
    booleanParam(name: 'headless', defaultValue: true)
  }

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Verify Suite File') {
      steps {
        bat """
          if exist "%suiteFile%" (
            echo FOUND: %suiteFile%
          ) else (
            echo MISSING: %suiteFile%
            exit /b 2
          )
        """
      }
    }

    stage('Run Tests') {
      steps {
        script {
          def props = ["-Dsurefire.suiteXmlFiles=${params.suiteFile}", "-Dheadless=${params.headless}"]
          if (params.browser?.trim()) props << "-Dbrowser=${params.browser.trim()}"
          def propsLine = props.join(' ')
          bat """
            java -version
            if exist mvnw.cmd (
              call .\\mvnw.cmd -B -U -V clean test ${propsLine}
            ) else (
              mvn -B -U -V clean test ${propsLine}
            )
          """
        }
      }
    }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'
      publishHTML(target: [
        reportDir: 'target/surefire-reports',
        reportFiles: 'emailable-report.html,index.html',
        reportName: 'TestNG HTML',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: true
      ])
      archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: false
    }
  }
}
