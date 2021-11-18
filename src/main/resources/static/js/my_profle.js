window.app = new Vue({
    el: '#my_profile',
    data: {
        results:'',
        id : window.location.href.split("/").slice(-1)[0]
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("window.location.href")
                .then(response => {
                    this.results = response.data;
                })
        },
        goToTest:function (test){
            window.location.href=this.id+"/test/"+test.indentity
        }
    }
})