window.app = new Vue({
    el: '#registration',
    data: {
        username:'',
        password:'',
        role:'ROLE_STUDENT',
        msg: [],
        password_check:'',
        email:'',
        empty_fields:'',
        config : {
            withCredentials: true
        }
    },
    watch: {
        email(value){
            this.email = value;
            this.validateEmail(value);
        },
        password(value){
            this.password = value;
            this.validatePassword(value);
        },
        password_check(value){
            this.password_check = value;
            this.validatePasswordCheck(value);
        },
        username(value){
            this.username = value;
            this.validateUsername(value);
        }
    },
    methods: {
        validateEmail(value){
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value))
            {
                this.msg['email'] = '';
            } else{
                this.msg['email'] = 'Неправильный формат почты';
            }
        },
        validatePassword(value){
            let difference = 6 - value.length;
            if (value.length<6) {
                this.msg['password'] = 'Должно быть как минимум 6 символов '+ difference + ' осталось' ;
            } else {
                this.msg['password'] = '';
            }
            if (value===this.password_check) {
                this.msg['password_check'] = '' ;
            } else {
                if(this.password_check==='') {
                    this.msg['password_check'] = '';
                }
                else{
                    this.msg['password_check'] = 'Неправильный пароль подтверждения';
                }
            }
        },
        validateUsername(value){
            if (value==='') {
                this.msg['username'] = 'Не забудьте указать ваше ФИО' ;
            } else {
                this.msg['username'] = '';
            }
        },
        validatePasswordCheck(value){
            if (value===this.password) {
                this.msg['password_check'] = '' ;
            } else {
                this.msg['password_check'] = 'Неправильный пароль подтверждения';
            }
        },
        addUser: function (event) {
            if(this.email!=='' && this.username!=='' && this.password!=='' && this.password_check!=='') {
                axios
                    .post("/add_user", {
                        "email": this.email,
                        "username": this.username,
                        "password": this.password,
                        "UserRole": this.role
                    },this.config)
                    .then((response) => {
                        window.location.href='login'
                    })
                    .catch((error) => {
                    this.empty_fields = error
                    });
            }
            else{
                this.empty_fields = 'Пожалуйста, заполните все поля'
            }
        }
    }
})