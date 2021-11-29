window.app = new Vue({
    el: '#new_test',
    data: {
        value: '',
        date: '',
        name_chapter: '',
        name: '',
        time:'',
        id : window.location.href.split("/")[4]
    },
    methods: {
        postTest: async function (event) {
            let new_test_id=''
            await axios
                .post("/subject/" + this.id + "/add_test", {
                    "name_test": this.name,
                    "name_chapter": this.name_chapter,
                    "date": this.date,
                    "question_number": this.value,
                    "time": this.time
                }).then(function (response) {
                    new_test_id = response.data
                })
            window.location.href = "/subject/" + this.id +"/test/" + new_test_id + "/edit"
        }
    }
})