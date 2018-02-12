//def branches = [:]
//
//for (int i = 0; i < 4; i++) {
//    def index = i //if we tried to use i below, it would equal 4 in each job execution.
//    branches["branch${i}"] = {
//        build job: 'test_jobs', parameters: [[$class: 'StringParameterValue', name: 'param1', value:
//                'test_param'], [$class: 'StringParameterValue', name:'dummy', value: "${index}"]]
//    }
//}
//parallel branches

//def e = "UAT1 UAT2 UAT3"

node() {
    def envs = e.split() as List
    def branches = [:]
    int cnt = 0
    for (int i = 0; i < envs.size(); i++) {
        def index = i
        def elem = envs.get(i)
        cnt += 1
        branches["branches_${index}"] = {
            node() {
                stage("deploy_db_${elem}") {
                    if (index == 2) {
                        error "TestError!"
                    } else {
                        echo "echo : Деплой на " + elem + " завершен."
                    }
                }
            }
        }
    }
    parallel branches
    echo "Завершена установка на ${cnt} сред"
}