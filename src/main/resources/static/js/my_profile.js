window.app = new Vue({
    el: '#my_profile',
    data: {
        subjects_results:'',
        role:'',
        info:'',
        id : window.location.href.split("/").slice(-1)[0]
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("/info/role")
                .then(response => {
                   if(response.data === "ROLE_STUDENT"){
                       this.role = "student"
                       axios
                           .get("/info/student")
                           .then(response =>{
                               this.info = response.data
                           })
                   }
                   else{
                       this.role = "teacher"
                       axios
                           .get("/info/teacher")
                           .then(response =>{
                               this.info = response.data
                           })
                   }
                })
            axios
                .get("/subjects_results")
                .then(response => {
                    console.log(response.data)
                    this.subjects_results = response.data;

                })
                .catch(err =>
                    window.location.href="/login"
                )
        },
        logout:function (test){
            axios.get("/logout")
            window.location.href="/"
        }
    }
})