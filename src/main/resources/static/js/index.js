window.app = new Vue({
    el: '#app',
    data: {
        subjects:[],
        cookie: document.cookie
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("/subjects")
                .then(response => {
                    this.subjects = response.data;
                })
        }
    }
})