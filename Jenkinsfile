pipeline {
  agent any

  tools {
    jdk 'temurin-17'
    maven 'Maven 3.9.11'
  }

  stage('Checkout Master') {
    steps {
      git branch: 'master'
    }
  }

    stage('Run Tests') {
      steps {
        bat 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml -Dheadless=true -Dbrowser=chrome'
      }
    }
  }

}

