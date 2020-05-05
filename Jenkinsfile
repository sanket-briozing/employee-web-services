pipeline {
   agent any

   tools {
       maven 'M3'
     }

   stages {
      stage('Employee Pipeline') {
         steps {
            echo 'Employee Pipeline started'
         }
      }

      stage('Checkout') {
          steps {

            git branch:'master', credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/sanket-briozing/employee-web-services.git'


        echo 'Checkout Done'
        }
      }

      stage('Compile') {
         steps {
           sh 'mvn clean package'
            echo 'Compilation done'

         }
      }
     stage('Build') {
         steps {
            sh 'docker-compose up'
            echo 'Build Done'

         }
      }




     stage('Checkout Test') {
          steps {

              git branch:'master', credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/sanket-briozing/restful-booker-tests.git'

        echo 'Checkout Test Done'
        }
      }

      stage('Compile Test') {
        steps {
             sh "mvn clean package"
             echo 'Compilation of Test is done'
         }
      }

     stage('Test') {
         steps {
            sh 'mvn clean test -Dgroups=addEmployee'
            echo 'Test case passed successfully'

         }
      }
   }
}
