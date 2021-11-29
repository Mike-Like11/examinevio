window.app = new Vue({
    el: '#subject_estimation',
    data: {
        students:[],
        groups:'',
        id : window.location.href.split("/").slice(-2)[0]
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("/subjects/" + this.id + "/groups")
                .then(response => {
                    console.log(response.data)
                    this.groups = response.data;
                    for(group in this.groups){
                        for(student in this.groups[group].users){
                            this.students.push(this.groups[group].users[student])
                        }
                    }
                    console.log(this.students)
                })
                // .catch(err =>
                //     window.location.href="/login"
                // )
        },
        addMark:function (fio,mark){
                axios
                    .post("/subjects/" + this.id + "/add_mark",{
                        "fio": fio,
                        "mark":mark
                    })
        }
    }
})