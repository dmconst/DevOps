def e = "${envs}"

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