pipeline {
  agent any
  options { timestamps() }

  // Configure these in: Manage Jenkins → Tools
  tools {
    jdk   'temurin-17'      // must match your JDK name in Tools
    maven 'Maven 3.9.x'     // must match your Maven name in Tools
  }

  parameters {
    // Leave blank to use testng.xml as-is (e.g., both Chrome & Firefox from your suite)
    choice(name: 'browser', choices: ['', 'chrome', 'firefox'], description: 'Override browser (blank = use testng.xml)')
    booleanParam(name: 'headless', defaultValue: true, description: 'Run headless in CI')
    string(name: 'suiteFile', defaultValue: 'testng.xml', description: 'Path to TestNG suite file')
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test') {
      steps {
        script {
          // Build property line for Surefire
          def props = [
            "-Dsurefire.suiteXmlFiles=${params.suiteFile}",
            "-Dheadless=${params.headless}"
          ]
          if (params.browser?.trim()) props << "-Dbrowser=${params.browser.trim()}"
          def propsLine = props.join(' ')

          // Prefer Maven Wrapper; fallback to Jenkins Maven tool
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
      // Always publish JUnit XML for Jenkins test trend
      junit 'target/surefire-reports/*.xml'

      // Publish your Extent report from test-output
      publishHTML(target: [
        reportDir: 'test-output',
        reportFiles: 'ExtentReport.html',
        reportName: 'Extent HTML Report',
        keepAll: true,
        alwaysLinkToLastBuild: true,
        allowMissing: true
      ])

      // Keep artifacts (reports + screenshots)
      archiveArtifacts artifacts: 'target/surefire-reports/**/*, test-output/**/*, screenshots/**/*',
                        allowEmptyArchive: true
    }
  }
}