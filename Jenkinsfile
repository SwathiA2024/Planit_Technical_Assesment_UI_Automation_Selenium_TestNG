pipeline {
  agent any
  options { timestamps() }

  tools {
    jdk   'temurin-17'      // must exist in Manage Jenkins → Tools
    maven 'Maven 3.9.x'     // must exist in Manage Jenkins → Tools
  }

  parameters {
    string(name: 'suiteFile', defaultValue: 'testng.xml', description: 'Path to TestNG suite file')
    choice(name: 'browser', choices: ['', 'chrome', 'firefox'], description: 'Override browser (blank = use testng.xml)')
    booleanParam(name: 'headless', defaultValue: true, description: 'Run headless in CI')
  }

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Preflight (verify suite path)') {
      steps {
        bat """
          echo ===== Check suite path =====
          if exist "%suiteFile%" (
            echo FOUND suite: %suiteFile%
          ) else (
            echo ERROR: suite not found at %suiteFile%
            echo Try: testng.xml  OR  src\\test\\resources\\testng.xml
            exit /b 2
          )
        """
      }
    }

    stage('Run TestNG Suite') {
      steps {
        script {
          def props = [
            "-Dsurefire.suiteXmlFiles=${params.suiteFile}",
            "-Dheadless=${params.headless}"
          ]
          if (params.browser?.trim()) props << "-Dbrowser=${params.browser.trim()}"
          def propsLine = props.join(' ')

          bat """
            echo ===== Java =====
            java -version

            if exist mvnw.cmd (
              .\\mvnw.cmd -B -U -V clean test ${propsLine}
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
      // Publish ONLY Extent HTML
      publishHTML(target: [
        reportDir: 'test-output',
        reportFiles: 'ExtentReport.html',
        reportName: 'Extent HTML Report',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: true
      ])

      // Keep raw Extent assets & screenshots
      archiveArtifacts artifacts: 'test-output/**/*, screenshots/**/*',
                        allowEmptyArchive: true
    }
  }
}