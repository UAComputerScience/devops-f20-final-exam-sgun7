pipeline {

        agent any

    stages{
        stage("Build Package"){
        agent {
            docker {
                image 'srg97/markturn_ubuntu'
                args '--user root'
            }
        }
        

        steps {
            sh 'pwd'
            sh 'cat /etc/os-release'
            sh 'curl -L https://github.com/UAComputerScience/devops-f20-final-exam-sgun7/archive/v1.0.0.tar.gz | tar xz'
            sh 'mv devops-f20-final-exam-sgun7-1.0.0 /Source'
            sh 'mkdir /Build;'
            sh 'cd /Build; cmake /Source -DLINK_STATIC=0FF -G Ninja'
            sh 'cd /Build; ninja'
            sh 'cd /Build; ctest'
            sh 'cd /Build; cpack -G DEB'
           // sh 'echo "artifact file" > Build/markturn-0.0.0-Linux.deb'

            //sh 'cp /Build/*.deb dist/.'
           // sh 'pwd; ls -lh'
           //archiveArtifacts artifacts: 'dist/*.deb', followSymlinks: false
           archiveArtifacts artifacts: 'build/*.deb', onlyIfSuccessful: true
        }
        }
    }
 /*       post {
        always {
            archiveArtifacts artifacts: 'build/*.deb', onlyIfSuccessful: true
        }
    } */
}
