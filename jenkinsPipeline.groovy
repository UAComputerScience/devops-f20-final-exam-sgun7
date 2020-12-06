pipeline{

agent 
{
    docker {
        image 'srg97/markturn_ubuntu'
        args '--user root'
    }
}
stages {
    //TODO will he be able to access this because this is my branch
    stage('Setup'){
        steps{
        sh 'cat /etc/os-release'
        // sh 'rm -rf devops-f20-final-exam-sgun7'
        //sh 'git clone https://github.com/UAComputerScience/devops-f20-final-exam-sgun7.git'
        //git credentialsId: 'd68d2692-a1ef-4f38-aa74-c96014acbf00', url: 'https://github.com/UAComputerScience/markturn-sgun7.git'
       // git branch: 'main', credentialsId: 'GitHub', url: 'https://github.com/UAComputerScience/markturn-sgun7.git'
       // git branch: 'main',  url: 'https://github.com/UAComputerScience/devops-f20-final-exam-sgun7.git'
      // git 'https://github.com/UAComputerScience/devops-f20-final-exam-sgun7.git'
        //sh 'git clone https://sgun7@github.com/UAComputerScience/markturn-sgun7.git'
        //echo 'Hello world'
       // sh 'rm -rf markturn build'
       // sh 'mkdir build'
       // sh 'git clone https://github.com/UAComputerScience/devops-f20-final-exam-sgun7.git'
       //sh 'git clone https://github.com/UAComputerScience/devops-f20-final-exam-sgun7.git'
      // sh 'mv markturn-1.0.0 /source'
      sh 'curl -L https://github.com/UAComputerScience/devops-f20-final-exam-sgun7/archive/v1.0.0.tar.gz | tar xz'
      sh 'mv devops-f20-final-exam-sgun7-1.0.0 /Source'
        }      
    }
    stage('CMake')
    {
        steps{
           //cmakeBuild buildDir: 'build', cmakeArgs: '-DLINK_STATIC=0FF', generator: 'Ninja', installation: 'cmake'
           sh 'mkdir /Build'
           sh 'cd /Build; cmake /Source -DLINK_STATIC=0FF -G Ninja'
        }
    }
    stage('Build Generation')
    {
        steps{
           //sh 'cd /build; ninja clean; ninja' 
           sh 'cd /Build; ninja'

        }
    }

    stage('Test')
    {
        steps{
        //ctest arguments: '-R json2xml_t', installation: 'cmake', workingDir: 'build'
        //ctest installation: 'cmake', workingDir: 'build'
        sh 'cd /Build; ctest'

        }
    }
    stage('Package')
    {
        steps{
            //TODO is this correct
          // cpack arguments: '-G DEB', installation: 'cmake', workingDir: 'build'
          sh 'cd /Build; cpack -G DEB'
        }
    }

}
}