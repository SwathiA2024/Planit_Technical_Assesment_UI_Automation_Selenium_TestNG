pipeline {
  agent any
  options { timestamps() }

  tools {
    jdk   'temurin-17'
    maven 'Maven 3.9.x'
  }

  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Run Tests') {
      steps {
        bat """
          java -version
          if exist mvnw.cmd (
            call .\\mvnw.cmd -B -U -V clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=chrome -Dheadless=true
          ) else (
            mvn -B -U -V clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=chrome -Dheadless=true
          )
        """
      }
    }
  }

  post {
    always {
      junit testResults: '**/target/surefire-reports/*.xml'
    }
  }
}

