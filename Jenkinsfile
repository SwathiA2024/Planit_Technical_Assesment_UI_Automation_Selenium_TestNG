pipeline {
  agent any

  tools {
    jdk 'temurin-17'
    maven 'Maven 3.9.x'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run Tests') {
      steps {
        bat 'mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dheadless=true'
      }
    }
  }
}

