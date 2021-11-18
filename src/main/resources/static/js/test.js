
window.app = new Vue({
    el: '#test',
    data: {
        test_info:'',
        time:0,
        answer:'',
        current_question:'',
        current_index:'1',
        test_id: window.location.href.split("/").slice(-1)[0],
        id : window.location.href.split("/")[4]
    },
    async mounted() {
        await this.getInfo();
        await this.getCurrent();
    },
    computed:{
       time_after : function() {
           setInterval(() => {
               if((pad(parseInt(this.time/60,10))==="00")){
                   window.location.href = this.test_id+'/test_result'
               }
               $("#seconds").html(pad(--this.time%60));
               $("#minutes").html(pad(parseInt(this.time/60,10)));
           }, 1000)
       }
    },
    methods: {
        getCurrent: function (event) {
                    this.current_question = this.test_info.questions_answers[0]
                    this.time = parseInt((new Date(new Date(this.test_info.time_start).getTime()+this.test_info.test_time*60000) - new Date())/(1000))
        },
         getInfo: async function (event) {
             await axios
                 .get("/subjects/" + this.id + "/test/" + this.test_id + "/getview")
                 .then(response => {
                     this.test_info = response.data
                 })
         },
        answerQuestion: function (event) {
            console.log(this.current_question.id)
            axios
                .post("/subjects/"+this.id+"/test/"+this.test_id+"/getview"+"/add_answer",{
                    "your_answer" : this.current_question.your_answer,
                    "id":this.current_question.id,
                    "question":this.current_question.question,
                }).then(test=>{
                    this.getInfo();
                }
            );
        }
    }
})
function pad(val) {
    return val < 0 || val>10 ? val : "0" + val;
}