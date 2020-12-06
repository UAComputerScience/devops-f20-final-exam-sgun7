pipeline {

    agent {
        docker {
            image 'srg97/markturn_ubuntu'
            args '--user root'
        }
    }
    stages {
        stage('Setup'){
            steps{
                sh 'cat /etc/os-release'
                sh 'curl -L https://github.com/UAComputerScience/devops-f20-final-exam-sgun7/archive/v1.0.0.tar.gz | tar xz'
                sh 'mv devops-f20-final-exam-sgun7-1.0.0 /Source'
            }      
        }
        stage('CMake'){
            steps{
                //cmakeBuild buildDir: 'build', cmakeArgs: '-DLINK_STATIC=0FF', generator: 'Ninja', installation: 'cmake'
                sh 'mkdir /Build'
                sh 'cd /Build; cmake /Source -DLINK_STATIC=0FF -G Ninja'
            }
        }
        stage('Build Generation'){
            steps{
                //sh 'cd /build; ninja clean; ninja' 
                sh 'cd /Build; ninja'
            }
        }
        stage('Test'){
            steps{
                //ctest arguments: '-R json2xml_t', installation: 'cmake', workingDir: 'build'
                //ctest installation: 'cmake', workingDir: 'build'
                sh 'cd /Build; ctest'
                }
        }
        stage('Package'){
            steps{
                //TODO is this correct
                // cpack arguments: '-G DEB', installation: 'cmake', workingDir: 'build'
                sh 'cd /Build ; cpack -G DEB'
            }
        }
        stage('Archive'){
            steps{
                sh 'cd /Build; ls'
                archiveArtifacts artifacts: 'build/markturn-0.0.0-Linux.deb', onlyIfSuccessful: true
            }
        }

}

}

