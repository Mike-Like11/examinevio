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
        selected_student_group:'',
        selected_teachers_subjects:[],
        new_group_name:'',
        answer: '',
        question_desc: ' ',
        options:[],
        groups:[],
        subjects:[],
        selected_groups:[],
        subjects_names:[],
        number:'',
        teachers: '',
        teachers_names:[],
        subject_groups:[],
        selected_teachers:[],
        subject_name:'',
        group_names:[],
        students_check:[],
        teachers_check:[]
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
                .get("/admin/subjects")
                .then(response => {
                    this.subjects = response.data;
                    console.log(this.subjects)
                    for(let subject in this.subjects){
                        this.subjects_names.push(this.subjects[subject].name);
                    }
                });
            axios
                .get("/admin/teachers")
                .then(response => {
                    this.teachers = response.data;
                    console.log(this.subjects)
                    for(let teacher in this.teachers){
                        this.teachers_names.push(this.teachers[teacher].fio);
                    }
                });
            axios
                .get("/admin/students/on_check")
                .then(response => {
                    this.students_check = response.data;
                    console.log(this.students_check)
                    // for(let i = 0; i < this.test.question_number; i++){
                    //     this.options.push(i+1);
                    // }
                });
            axios
                .get("/admin/teachers/on_check")
                .then(response => {
                    this.teachers_check = response.data;
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
        enableStudentAccount: function (student,selected,number) {
            console.log("/admin/"+selected+"/student");
            console.log(student.email+selected+number+student.username);
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
        enableTeacherAccount: function (teacher,selected_teachers_subjects,number) {
            console.log("/admin/activate/teacher");
            axios
                .post("/admin/activate/teacher",{
                    "number": number,
                    "fio":teacher.username,
                    "subjects":selected_teachers_subjects,
                    "email":teacher.email
                }).then(test=>{
                    this.getInfo();
                }
            );
        },
        addSubject: function (event) {
            console.log(this.selected_teachers)
            axios
                .post("/admin/subject",{
                    "name": this.subject_name,
                    "teachers": this.selected_teachers,
                    "groups":this.subject_groups
                }).then(test=>{
                    this.getInfo();
                }
            );
        }
    }
})