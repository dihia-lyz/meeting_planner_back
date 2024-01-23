node("dihia-ci"){
    stage("checkout"){
    echo "start checkout"
    checkout scmGit(branches: [[name: '*/main']], extensions: [],
     userRemoteConfigs: [[url: 'https://github.com/dihia-lyz/meeting_planner_back.git']])
    }
}