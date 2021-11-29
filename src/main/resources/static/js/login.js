window.app = new Vue({
    el: '#login',
    data: {
        email: '',
        password: '',
        error: '',
        config: {
            withCredentials: true
        }
    },
    watch: {
        email(value) {
            this.email = value;
            this.validateEmail(value);
        }
    },
    methods: {
        validateEmail(value) {
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
                this.error = '';
            } else {
                this.error = 'Неправильный формат почты';
            }
        },
    authUser: function (event) {
        if (this.email !== '' && this.password !== '') {
            axios
                .post("/auth", {
                    "email": this.email,
                    "password": this.password,
                })
                .then((response) => {
                    if(response.data === "Ошибка ввода данных"){
                        this.error = response.data
                    }
                    else {
                        if(response.data === "Ваш аккаунт еще не подтвержден, ожидайте") {
                            this.error = response.data
                        }
                        else {
                            window.location.href = '/'
                        }
                    }
                })
        } else {
            this.error = 'Пожалуйста, заполните все поля'
        }
    }
    }
})