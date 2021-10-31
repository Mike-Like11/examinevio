window.app = new Vue({
    el: '#app',
    data: {
        //     transProps: {
        //         // Transition name
        //         name: 'flip-list'
        //     },
        //     fields: [
        //         {
        //             key: 'desc',
        //             sortable: true
        //         },
        //         {
        //             key: 'type',
        //             sortable: true
        //         },
        //         {
        //             key: 'answer',
        //             sortable: true,
        //         }
        //     ],
        test:'',
        selected:'',
        new_group_name:'',
        answer: '',
        question_desc: ' ',
        options:[],
        groups:[],
        number:'',
        subject_groups:[],
        subject_name:'',
        group_names:[],
        students_check:[],
        // id : window.location.href.split("/").slice(-1)[0]
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("/admin/groups")
                .then(response => {
                    this.groups = response.data;
                    console.log(this.groups)
                    for(let group in this.groups){
                        this.group_names.push(this.groups[group].name);
                    }
                });
            axios
                .get("/admin/students")
                .then(response => {
                    this.students_check = response.data;
                    console.log(this.students_check)
                    // for(let i = 0; i < this.test.question_number; i++){
                    //     this.options.push(i+1);
                    // }
                });
        },
        addGroup: function (event) {
            console.log(this.new_group_name);
            axios
                .post("/admin/group",{
                    "name": this.new_group_name
                }).then(test=>{
                    this.getInfo();
                }
            );
        },
        enableAccount: function (student,selected,number) {
            console.log("/admin/"+selected+"/student");
            console.log("dasdasdasdasdasdasd");
            // var thisgroup;
            // for(let group in this.groups){
            //     if(this.groups[group].name === selected){
            //         thisgroup = this.groups[group].id
            //         console.log(thisgroup)
            //     }
            // }
            axios
                .post("/admin/"+selected+"/student",{
                    "number": number,
                    "fio":student.username,
                    "email":student.email
                }).then(test=>{
                    this.getInfo();
                }
            );
        },
        addSubject: function (event) {
            axios
                .post("/admin/subject",{
                    "name": this.subject_name,
                    "groups":this.subject_groups
                }).then(test=>{
                    this.getInfo();
                }
            );
        }
    }
})