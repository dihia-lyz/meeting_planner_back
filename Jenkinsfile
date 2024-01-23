node("dihia-ci"){
    stage("checkout"){
    echo "start checkout"
     echo "start checkout 2"
    checkout scmGit(branches: [[name: '*/main']], extensions: [],
     userRemoteConfigs: [[url: 'https://github.com/dihia-lyz/meeting_planner_back.git']])
    }
}