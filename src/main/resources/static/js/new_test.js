window.app = new Vue({
    el: '#app',
    data: {
        value: '',
        date: '',
        name_chapter: '',
        name: '',
        time:'',
        id : window.location.href.split("/")[4]
    },
    methods: {
        postTest: function (event) {
            axios
                .post("/subject/"+this.id+"/add_test", {
                    "name_test": this.name,
                    "name_chapter":this.name_chapter,
                    "date":this.date,
                    "question_number":this.value,
                    "time":this.time
                }).
            then(function (response){
                console.log(response.data)
                window.location.href = "/subject/"+this.id+"/test_edit/"+response.data
            })
        }
    }
})