window.app = new Vue({
    el: '#subject',
    data: {
        tests:[],
        groups:[],
        teachers:[],
        subject:[],
        disabled:[],
        id : window.location.href.split("/").slice(-1)[0]
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("/subjects/"+this.id)
                .then(response => {
                    var date_current = new Date();
                    this.subject = response.data;
                    for(test in this.subject.tests){
                        this.disabled[test] = !(date_current.getMonth() === new Date(this.subject.tests[test].date).getMonth() && date_current.getDay() === new Date(this.subject.tests[test].date).getDay());
                    }
                })
        },
        goToTest:function (test){
            window.location.href=this.id+"/test/"+test.indentity
        }
    }
})