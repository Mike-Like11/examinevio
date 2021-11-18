window.app = new Vue({
    el: '#test_result',
    data: {
        results:'',
        id : window.location.href.split("/").slice(3,-1),
        subject_href : window.location.href.split("/").slice(3,5).join('/')
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get('/'+this.id.join('/')+'/end_test')
                .then(response => {
                    this.results = response.data;
                })
            console.log(this.results)
        },
        goToTest:function (test){
            window.location.href=this.id+"/test/"+test.indentity
        }
    }
})