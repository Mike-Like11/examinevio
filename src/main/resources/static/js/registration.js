window.app = new Vue({
    el: '#app',
    data: {
        username:'',
        password:'',
        email:''
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            // axios
            //     .get("/tests/"+this.id)
            //     .then(response => {
            //         console.log(response.data.id.str)
            //         this.test = response.data;
            //         for(let i = 0; i < this.test.question_number; i++){
            //             this.options.push(i+1);
            //         }
            //     })
        },
        addUser: function (event) {
            axios
                .post("/add_user", {
                    "email": this.email,
                    "username":this.username,
                    "password":this.password,
                    "UserRole":'STUDENT'
                });
        }
    }
})