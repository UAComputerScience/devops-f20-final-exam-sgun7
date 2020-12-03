pipeline{

agent 
{
    docker {
        image 'ub2-new'
        args '--user root'
    }
}
stages {
    stage('Setup'){
        steps{
        //sh 'cat /etc/os-release'
        //git credentialsId: 'd68d2692-a1ef-4f38-aa74-c96014acbf00', url: 'https://github.com/UAComputerScience/markturn-sgun7.git'
        git branch: 'main', credentialsId: 'GitHub', url: 'https://github.com/UAComputerScience/markturn-sgun7.git'
        //sh 'git clone https://sgun7@github.com/UAComputerScience/markturn-sgun7.git'
        //echo 'Hello world'
        }      
    }
    stage('CMake')
    {
        steps{
           cmakeBuild buildDir: 'build', cmakeArgs: '-DLINK_STATIC=0FF', generator: 'Ninja', installation: 'cmake'
        }
    }



}
}