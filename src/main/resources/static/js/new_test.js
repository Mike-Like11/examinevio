window.app = new Vue({
    el: '#app',
    data: {
        value: '',
        date: '',
        name_chapter: '',
        name: '',
        time:''
    },
    methods: {
        postTest: function (event) {
            axios
                .post("/add_test", {
                    "name_test": this.name,
                    "name_chapter":this.name_chapter,
                    "date":this.date,
                    "question_number":this.value,
                    "time":this.time
                }).
            then(function (response){
                window.location.href = "/test_edit/"+response.data
            })
        }
    }
})