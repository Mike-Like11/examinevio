window.app = new Vue({
    el: '#subject',
    data: {
        tests:[],
        groups:[],
        teachers:[],
        subject:[],
        disabled:[],
        my_results:'',
        test_status:'',
        id : window.location.href.split("/").slice(-1)[0]
    },
      mounted() {
          this.getInfo();
     },
    methods: {
        getInfo:   async function (event) {
            axios
                .get("/subjects/" + this.id + "/groups")
                .then(response => {
                    this.groups = response.data
                })
             axios
                .get("/subjects/" + this.id + "/teachers")
                .then(response => {
                    this.teachers = response.data
                })
            await axios
                .get("/subjects/" + this.id + "/my_results")
                .then(response => {
                    this.my_results = response.data
                })
            await axios
                .get("/subjects/" + this.id)
                .then( response => {
                    this.subject = response.data;
                    for (test in this.subject.tests) {
                            this.disabled[test] = !(new Date().getMonth() === new Date(this.subject.tests[test].date).getMonth() && new Date().getDate() === new Date(this.subject.tests[test].date).getDate());
                            if(this.disabled[test]) {
                                this.test_status = "Тест не доступен"
                            }
                            else {
                                this.test_status = "Начать тестирование"
                            }
                    }
                    for (test in this.subject.tests) {
                        for (t_result in this.my_results.tests_results) {
                            if (this.my_results.tests_results[t_result].test_name === this.subject.tests[test].name_test) {
                                this.disabled[test] = true
                                this.test_status = "У вас больше нет попыток"
                            }
                        }
                    }
                })
        },
        goToTest:function (test){
            window.location.href=this.id+"/test/"+test.indentity
        }
    }
})