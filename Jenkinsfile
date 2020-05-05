pipeline {
   agent any

   tools {
       maven 'M3'
     }

   stages {

      stage('Initialize'){
        steps {
            script {
                 def dockerHome = tool 'myDocker'
                 env.PATH = "${dockerHome}/bin:${env.PATH}"
            }
         }
      }

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
//             sh 'sudo service docker stop'
//             sh 'sudo nohup docker daemon -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock &'
//             sh 'docker stop emp-service'
//             sh 'docker rm emp-service'
//             sh 'docker build -f DockerFile -t emp-service .'
//             sh 'docker run --name emp-service -it -d -p 8888:8888 -v /var/run/mysqld/mysqld.sock:/tmp/mysql.sock --network=host emp-service'
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
