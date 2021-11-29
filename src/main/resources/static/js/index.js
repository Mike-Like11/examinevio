window.app = new Vue({
    el: '#app',
    data: {
        subjects:[],
        config : {
            withCredentials: true
        }
    },
    async mounted(){
        await this.getInfo();
        window.document.getElementById("app").style.visibility = "visible"
    },
    methods: {
        getInfo: async function (event) {
            axios
                .get("/subjects",this.config)
                .then(response => {
                    console.log(response)
                        this.subjects = response.data;

                })
                .catch(err =>
                    window.location.href="/login"
                 )
        }
    }
})