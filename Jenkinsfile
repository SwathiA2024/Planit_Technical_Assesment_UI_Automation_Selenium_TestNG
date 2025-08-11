pipeline {
  agent any

  tools {
    jdk 'temurin-17'           // Ensure this matches your Jenkins tool config
    maven 'Maven 3.9.11'       // Same here
  }

  stages {
    stage('Checkout Master') {
      steps {
        git branch: 'master', url: 'https://github.com/SwathiA2024/Planit_Technical_Assesment_UI_Automation_Selenium_TestNG'
      }
    }

    stage('Run TestNG Suite') {
      steps {
        bat 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml -Dheadless=true -Dbrowser=chrome'
      }
    }
  }

  post {
    always {
      junit '/target/surefire-reports/*.xml' // Collect test results
    }
    failure {
      echo '❌ Tests failed. Please check the report.'
    }
    success {
      echo '✅ Tests passed successfully!'
}
}
}