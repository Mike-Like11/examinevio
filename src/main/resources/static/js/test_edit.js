$('.new_answer').hide();
function createAnswer() {
    $('.new_answer').toggle();
}
window.app = new Vue({
    el: '#app',
    data: {
        transProps: {
            // Transition name
            name: 'flip-list'
        },
        fields: [
            {
                key: 'desc',
                sortable: true
            },
            {
                key: 'type',
                sortable: true
            },
            {
                key: 'answer',
                sortable: true,
            }
        ],
        test:'',
        selected:'',
        answer: '',
        question_desc: ' ',
        options:[],
        id : window.location.href.split("/").slice(-1)[0]
    },
    mounted(){
        this.getInfo();
    },
    methods: {
        getInfo: function (event) {
            axios
                .get("/tests/"+this.id)
                .then(response => {
                    console.log(response.data.id.str)
                    this.test = response.data;
                    for(let i = 0; i < this.test.question_number; i++){
                        this.options.push(i+1);
                    }
                })
        },
        postQuestion: function (event) {
            axios
                .post("/subject/"+window.location.href.split("/")[4]+"/add_question/"+this.id, {
                    "desc": this.question_desc,
                    "type":this.selected,
                    "answer":this.answer,
                }).then(test=>{
                    this.getInfo();
                }
            );
            this.selected='';
            this.answer='';
            this.question_desc= ' ';
            createAnswer();
        }
    }
})